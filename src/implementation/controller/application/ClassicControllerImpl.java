package implementation.controller.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import design.controller.application.ClassicController;
import design.controller.game.GameLoader;
import design.model.game.ItemRule;
import design.view.game.ResourcesLoader;
import implementation.controller.game.gameLoader.GameLoaderJSON;
import implementation.view.application.Main;
import implementation.view.game.GameViewImpl;
import implementation.view.game.ResourcesLoaderFromFile;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;

public class ClassicControllerImpl implements ClassicController {

	public static String PATH = "res" + File.separator + "resources" + File.separator + "TestPack";
	private static String levelsPath = "res" + File.separator + "stages" + File.separator + "classic";
	private final ArrayList<Pair<String, GameLoader>> levels;
	private final ArrayList<String> names;
	private int selected = 0;
	private int previous = 0;
	private int players = 1;
	private String skinPackPath;
	
	@FXML
	private Text levelDescription;
	@FXML
	private Text levelTitle;
	@FXML
	private Canvas preview;
	private GraphicsContext gc;
	@FXML
	private Pane previewContainer;
	@FXML
	private Button prevButton;
	@FXML
	private Button nextButton;
	@FXML
	private HBox itemList;
	@FXML
	private HBox levelButtons;
	@FXML
	private Text playersText;
	
	
	public ClassicControllerImpl() throws IOException {
		levels = new ArrayList<>();
		for (File file : new File(levelsPath).listFiles()) {
			String levelPath = file.getPath();
			List<String> playerNames = new ArrayList<>(Arrays.asList("Ale")); //TODO: remove this heresy
			levels.add(new Pair<String, GameLoader>(levelPath, new GameLoaderJSON(levelPath, playerNames)));
		}
		names = new ArrayList<>(Arrays.asList("Player 1", "Player 2", "Player 3", "Player 4"));
	}
	
	public void initialize() throws FileNotFoundException, IOException {
		ObservableList<Node> buttons = levelButtons.getChildren();
		for (int i = 0; i < levels.size(); i++) {
			final int n = i;
			Button button = new Button(String.valueOf(i));
			buttons.add(button);
			button.setOnMouseClicked(e -> {
				selected = n;
				try {
					refreshLevel();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}
		preview.widthProperty().bind(previewContainer.widthProperty());
		preview.heightProperty().bind(previewContainer.heightProperty());
		gc = preview.getGraphicsContext2D();
		
		refreshPlayers();
	}
	
	private void refreshLevel() throws FileNotFoundException, IOException {
		int nLevels = levels.size();
		ResourcesLoader resources;
		GameLoader level;
		
		ObservableList<Node> buttons = levelButtons.getChildren();
		
		while (this.selected < 0) {
			this.selected = this.selected + nLevels;
		}
		
		while (this.selected >= nLevels) {
			this.selected = this.selected - nLevels;
		}
		
		buttons.get(previous).setDisable(false);
		buttons.get(selected).setDisable(true);
		
		level = levels.get(selected).getValue();
		resources = new ResourcesLoaderFromFile(skinPackPath, level.getGameModel().getField().getWidth(), level.getGameModel().getField().getHeight());
		
		String text = level.getLevelDescription();
		levelDescription.setText(text);
		//levelTitle.setText(level.getLevelName() + "\n");
		
		itemList.getChildren().clear();
		List<ItemRule> items = level.getGameModel().getGameRules().getItemRules();
		
		for (ItemRule item : items) {
			ImageView imv = new ImageView((Image) resources.getItem(item.getEffectClass().getSimpleName()).getSprite());
			double freq = 1000.0 * item.getSpawnChance() / (double) item.getSpawnDelta();
			String tooltip = String.format("Spawn chance per second: %d%%\nMaximum amount on screen: %d", (int) (freq * 100), item.getMax());
			Tooltip.install(imv, new Tooltip(tooltip));
			itemList.getChildren().add(imv);
		}
		
		Platform.runLater(new Runnable() {
			@Override public void run() {
				gc.clearRect(0, 0, preview.getWidth(), preview.getHeight());
				gc.setLineWidth(3);
				gc.setFill(Color.DARKGREEN);
				gc.setStroke(Color.AQUA);
				gc.fillText(level.getLevelName(), 100, 100);
				gc.strokeRect(9.5, 9.5, preview.getWidth() - 20, preview.getHeight() - 20);
			}
		});
		
		
		
		previous = selected;
	}
	
	private void refreshPlayers() {
		
		this.players = Math.max(Math.min(this.players, 4), 1);
		playersText.setText("Players: " + this.players);
	}
	
	@FXML
	public void selectPrev() throws FileNotFoundException, IOException {
		this.selected -= 1;
		refreshLevel();
	}
	
	@FXML
	public void selectNext() throws FileNotFoundException, IOException {
		this.selected += 1;
		refreshLevel();
	}
	
	@FXML
	public void removePlayer() {
		this.players -= 1;
		refreshPlayers();
	}
	
	@FXML
	public void addPlayer() {
		this.players += 1;
		refreshPlayers();
	}
	
	@FXML
	public void startSelectedLevel() throws FileNotFoundException, IOException {
		GameLoader gl = levels.get(selected).getValue();
		String levelPath = levels.get(selected).getKey();
		List<String> playerNames = names.subList(0, players);
		new GameViewImpl(Main.getScene(), this.skinPackPath, gl.getGameModel());
	}

	@Override
	public void setSkinPackPath(String path) {
		skinPackPath = path;
		try {
			refreshLevel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
