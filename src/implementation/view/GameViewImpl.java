package implementation.view;

import java.awt.Point;
import java.util.*;
import java.util.Map.Entry;
import design.view.*;
import javafx.beans.value.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class GameViewImpl implements GameView{
	
	private final static double LEFT_PADDING_PERCENTAGE = 0;
	private final static double  RIGHT_PADDING_PERCENTAGE = 0;
	private final static double TOP_PADDING_PERCENTAGE = 0.1;
	private final static double  BOTTOM_PADDING_PERCENTAGE = 0.1;
	private final static double TIME_LABEL_PERCENTAGE = 0.9;
	private final static double PLAYER_INFOS_PERCENTAGE = 0.7;
	private double cellPixelHeight;
	private double cellPixelWidth;
	double left_spacing;
	double top_spacing;
	double bgWidth;
	double bgHeight;
	
	private final int nPlayer;
	private final GameHudImpl hud;
	private final GameFieldImpl field;
	private boolean dirty;
	
	private Pane root = new Pane();
	private Scene scene = new Scene(root);
	private Canvas canvas;
    private GraphicsContext gc;
    
    private int timeLabelTextLength = 0;
    private final Label timeLabel = new Label();
    private final ArrayList<Label> nameLabels = new ArrayList<>();
    private final ArrayList<Label> scoreLabels = new ArrayList<>();
	
	public GameViewImpl(int nPlayer, int cellNumberWidth, int cellNumberHeight) {
		this.nPlayer = nPlayer;
		if (nPlayer > 1) {
			for (int i = 0; i < nPlayer; ++i) {
				nameLabels.add(new Label(""));
				scoreLabels.add(new Label(""));
			}
		}
		else {
			scoreLabels.add(new Label(""));
		}
		hud = new GameHudImpl(nPlayer, this);
		field = new GameFieldImpl(this);
		dirty = false;
		init(cellNumberWidth, cellNumberHeight);
		TestLoop tl = new TestLoop(this);
		tl.start();
	}
	
	@Override
	public synchronized GameHud getHUD() {
		return hud;
	}

	@Override
	public synchronized GameField getField() {
		return field;
	}

	@Override
	public void update() {
		if (dirty) {
			dirty = false;
			printCanvas();
		}
	}

	@Override
	public void togglePause() {
		// TODO Auto-generated method stub

	}
	
	public Scene getScene() {
		return scene;
	}
	
	protected void setDirty() {
		dirty = true;
	}
	
	protected void setTimeLabelText(String time) {
		timeLabel.setText(time);
		if (time.length() != timeLabelTextLength) {
			timeLabelTextLength = time.length();
			resizeTimeLabel();
		}
	}

	private void resizeTimeLabel() {
		timeLabel.setFont(new Font(canvas.getHeight() * TOP_PADDING_PERCENTAGE * TIME_LABEL_PERCENTAGE));
		timeLabel.layoutYProperty().bind(root.heightProperty().multiply(TOP_PADDING_PERCENTAGE).subtract(timeLabel.getHeight()).divide(2));
		timeLabel.layoutXProperty().bind(root.widthProperty().subtract(timeLabel.widthProperty()).divide(2));
		//TODO scazza quando si massimizza la finestra e viceversa
	}
	
	protected void setScoreLabelText(String score, int playerNumber) {
		scoreLabels.get(playerNumber).setText(score); 
		//TODO stessi cazzi del timeLabel
	}
	
	private void resizePlayers() {
		if (nPlayer == 1) {
			nameLabels.get(0).setFont(new Font(canvas.getHeight() * BOTTOM_PADDING_PERCENTAGE * PLAYER_INFOS_PERCENTAGE));
			nameLabels.get(0).layoutXProperty().bind(root.widthProperty().subtract(nameLabels.get(0).widthProperty()).divide(2));
			nameLabels.get(0).layoutYProperty().bind(root.heightProperty()
					.multiply(1 - BOTTOM_PADDING_PERCENTAGE)
					.add(((root.getHeight() * BOTTOM_PADDING_PERCENTAGE) - nameLabels.get(0).getHeight()) / 2));
		}
		else {
			
		}
	}
	
	private void printCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		if (hud.getBackground().isPresent()) {
			gc.drawImage(hud.getBackground().get(), 0, 0, canvas.getWidth(), canvas.getHeight());
		}
		if (field.getBackground().isPresent()) {
			gc.drawImage(field.getBackground().get(), left_spacing, top_spacing, bgWidth, bgHeight);
		}
		for (Entry<Point, List<Image>> entry : field.getSpritesMap().entrySet()) {
			for (Image image : entry.getValue()) {
				gc.drawImage(image, 
						(entry.getKey().getX() * cellPixelWidth) + left_spacing,
						(entry.getKey().getY() * cellPixelHeight) + top_spacing, 
						cellPixelWidth, cellPixelHeight);
			}
		}
	}
	
	private void init(int cellNumberWidth, int cellNumberHeight){
		canvas = new Canvas(800, 600);
	    gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		widthProperty(canvas.getWidth(), cellNumberWidth);
		heightProperty(canvas.getHeight(), cellNumberHeight);
		root.getChildren().add(timeLabel);		
		for (int i = 0; i < nPlayer; ++i) {
			root.getChildren().add(nameLabels.get(i));
			root.getChildren().add(scoreLabels.get(i));
		}
		root.widthProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				widthProperty((double) newValue, cellNumberWidth);
				resizeTimeLabel();
				printCanvas();
			}
		});
		root.heightProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				heightProperty((double) newValue, cellNumberHeight);
				resizeTimeLabel();	
				printCanvas();
			}
		});
	}
	
	private void widthProperty(double newCanvasWidth, int cellNumberWidth) {
		canvas.setWidth(newCanvasWidth);
		cellPixelWidth = (canvas.getWidth() * (1 - LEFT_PADDING_PERCENTAGE - RIGHT_PADDING_PERCENTAGE)) / cellNumberWidth;
		left_spacing = canvas.getWidth() * LEFT_PADDING_PERCENTAGE;
		bgWidth = canvas.getWidth() - (canvas.getWidth() * RIGHT_PADDING_PERCENTAGE) - left_spacing;
	}
	
	private void heightProperty(double newCanvasHeight, int cellNumberHeight) {
		canvas.setHeight(newCanvasHeight);
		cellPixelHeight = (canvas.getHeight() * (1 - TOP_PADDING_PERCENTAGE - BOTTOM_PADDING_PERCENTAGE)) / cellNumberHeight;
		top_spacing = canvas.getHeight() * TOP_PADDING_PERCENTAGE;
		bgHeight = canvas.getHeight() - (canvas.getHeight() * BOTTOM_PADDING_PERCENTAGE) - top_spacing;
	}

}
