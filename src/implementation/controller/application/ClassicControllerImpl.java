package implementation.controller.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import design.controller.application.ClassicController;
import design.controller.game.GameLoader;
import design.model.game.Field;
import design.model.game.ItemRule;
import design.model.game.Wall;
import design.view.game.ResourcesLoader;
import implementation.controller.PathUtils;
import implementation.controller.game.GameControllerImpl;
import implementation.controller.game.gameLoader.GameLoaderJSON;
import implementation.view.game.ResourcesLoaderFromFile;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

public class ClassicControllerImpl implements ClassicController {

    private final List<GameLoader> levels;
    private final List<String> names;
    private int selected = 0;
    private int previous = 0;
    private int players = 1;
    private String skinPackPath;
    private ResourcesLoader resources;

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
    private Button addPlayerButton;
    @FXML
    private Button removePlayerButton;
    @FXML
    private HBox itemList;
    @FXML
    private HBox levelButtons;
    @FXML
    private Text playersText;

    private static MediaPlayer mediaPlayer;

    public ClassicControllerImpl() throws IOException {
        levels = new ArrayList<>();
        names = new ArrayList<>(Arrays.asList("Player 1", "Player 2", "Player 3", "Player 4"));
        for (final File file : new File(PathUtils.CLASSIC).listFiles()) {
            final String levelPath = file.getPath();
            levels.add(new GameLoaderJSON(levelPath, names));
        }
    }

    public final void initialize() throws FileNotFoundException, IOException {
        final ObservableList<Node> buttons = levelButtons.getChildren();
        for (int i = 0; i < levels.size(); i++) {
            final int n = i;
            final Button button = new Button(String.valueOf(i));
            buttons.add(button);
            button.setOnMouseClicked(e -> {
                selected = n;
                try {
                    refreshLevel();
                    refreshPlayers();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        }
        preview.widthProperty().bind(previewContainer.widthProperty());
        preview.heightProperty().bind(previewContainer.heightProperty());
        final ChangeListener<Number> canvasResize = (observable, oldValue, newValue) ->
            drawPreview(preview, levels.get(selected).getGameModel().getField());

        preview.widthProperty().addListener(canvasResize);
        preview.heightProperty().addListener(canvasResize);
        gc = preview.getGraphicsContext2D();
        refreshPlayers();
    }

    private void refreshLevel() throws FileNotFoundException, IOException {
        final int nLevels = levels.size();
        GameLoader level;
        final ObservableList<Node> buttons = levelButtons.getChildren();
        while (this.selected < 0) {
            this.selected = this.selected + nLevels;
        }
        while (this.selected >= nLevels) {
            this.selected = this.selected - nLevels;
        }
        buttons.get(previous).setDisable(false);
        buttons.get(selected).setDisable(true);
        level = levels.get(selected);
        resources = new ResourcesLoaderFromFile(skinPackPath, level.getGameModel().getField().getWidth(), level.getGameModel().getField().getHeight());
        refreshItemList(level);
        final String text = level.getLevelDescription();
        levelDescription.setText(text);
        new AnimationTimer() {
            private int f = 0;
            @Override
            public void handle(final long now) {
                f++;
                if (f >= 2) {
                    drawPreview(preview, level.getGameModel().getField());
                    this.stop();
                }
            }
        }.start(); // First frame is discarded; this ensures there will be two
        previous = selected;
    }

    private void refreshItemList(final GameLoader level) {
        itemList.getChildren().clear();
        final List<ItemRule> items = level.getGameModel().getGameRules().getItemRules();
        for (final ItemRule item : items) {
            final Image sprite = (Image) resources.getItem(item.getEffectClass().getSimpleName()).getSprite();
            final ImageView imv = new ImageView(sprite);
            imv.setPreserveRatio(true);
            imv.setFitHeight(32); // TODO: remove magic number
            final double freq = 1000.0 * item.getSpawnChance() / (double) item.getSpawnDelta();
            final String tooltip = String.format("Spawn chance per second: %d%%\nMaximum amount on screen: %d", (int) (freq * 100), item.getMax());
            Tooltip.install(imv, new Tooltip(tooltip));
            itemList.getChildren().add(imv);
        }
    }

    private void drawPreview(final Canvas canvas, final Field field) {
        final List<Wall> walls = field.getWalls();
        final int spriteSize = (int) Math.min(canvas.getWidth() / field.getWidth(), canvas.getHeight() / field.getHeight());
        final int x0 = (int) (canvas.getWidth() - spriteSize * field.getWidth()) / 2;
        final int y0 = (int) (canvas.getHeight() - spriteSize * field.getHeight()) / 2;
        gc.drawImage((Image) resources.getFieldBackground().getBackground(), 0, 0, canvas.getWidth(), canvas.getHeight());
        for (final Wall wall : walls) {
            final String wName = GameControllerImpl.wallSpriteName(wall, walls);
            final Image wallSprite = (Image) resources.getWall(wName).getSprite();
            final double x = wall.getPoint().getX();
            final double y = wall.getPoint().getY();
            gc.drawImage(wallSprite, x0 + x * spriteSize, y0 + y * spriteSize, spriteSize, spriteSize);
        }
    }

    private void refreshPlayers() {
        final int maxPlayers = levels.get(selected).getMaxPlayers();
        this.players = Math.max(Math.min(this.players, maxPlayers), 1);
        if (this.players == 1) {
            removePlayerButton.setDisable(true);
        } else {
            removePlayerButton.setDisable(false);
        }
        if (this.players == maxPlayers) {
            addPlayerButton.setDisable(true);
        } else {
            addPlayerButton.setDisable(false);
        }
        playersText.setText("Players: " + this.players);
    }

    @FXML
    @Override
    public final void selectPrev() throws FileNotFoundException, IOException {
        this.selected -= 1;
        refreshLevel();
        refreshPlayers();
    }

    @FXML
    @Override
    public final void selectNext() throws FileNotFoundException, IOException {
        this.selected += 1;
        refreshLevel();
        refreshPlayers();
    }

    @FXML
    @Override
    public final void removePlayer() {
        this.players -= 1;
        refreshPlayers();
    }

    @FXML
    @Override
    public final void addPlayer() {
        this.players += 1;
        refreshPlayers();
    }

    @FXML
    @Override
    public final void startSelectedLevel() throws FileNotFoundException, IOException {
        new GameIntersticeImpl(levels.get(selected), skinPackPath, players).nextLevel();
    }

    @Override
    public final void setSkinPackPath(final String path) {
        skinPackPath = path;
        try {
            refreshLevel();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Stops music.
     */
    public static final void stopMusic() {
        mediaPlayer.stop();
    }
}
