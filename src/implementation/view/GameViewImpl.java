package implementation.view;

import design.view.*;
import javafx.beans.value.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

import static implementation.view.GameViewStatic.*;

public class GameViewImpl implements GameView {

	private final GameHud hud;
	private final GameField field;
	private final int nCellWidth;
	private final int nCellHeight;
	private final int nPlayer;
	private final double hudPercentage;
	private final Scene scene = new Scene(ROOT);
	private final ResourcesLoader loader;
   
	private boolean dirty;
	private double spriteLen;
	
    public GameViewImpl(int nPlayer, ResourcesLoader loader, int nCellWidth, int nCellHeight) {
		this.loader = loader;
		this.nPlayer = nPlayer;
		this.hudPercentage = calculateHudPercentage(nCellWidth, nCellHeight);
	    this.nCellHeight = nCellHeight;
	    this.nCellWidth = nCellWidth;
	    dirty = false;
	    
		hud = new GameHudImpl(nPlayer, loader);
		field = new GameFieldImpl(this, nPlayer, loader);
		
		ROOT.setCenter(GameViewStatic.FIELD_CANVAS);
    	ROOT.setTop(GameViewStatic.TOP_CANVAS);
    	ROOT.setBottom(GameViewStatic.BOTTOM_CANVAS);
    	ROOT.setLeft(GameViewStatic.LEFT_CANVAS);
    	ROOT.setRight(GameViewStatic.RIGHT_CANVAS);
	    ROOT.widthProperty().addListener(getSizeListener());
	    ROOT.heightProperty().addListener(getSizeListener());

	}
    
	@Override
	public GameHud getHUD() {
		return hud;
	}

	@Override
	public GameField getField() {
		return field;
	}

	@Override
	public void update() {
		if (dirty) {
			//TODO tutto il discorso hud
			drawBg(FIELD_GRAPHICS_CONTEXT, FIELD_CANVAS, (Image) loader.getFieldBg().getBackground());
			drawField(loader, field, FIELD_GRAPHICS_CONTEXT, spriteLen, nPlayer);
			dirty = false;
		}
	}

	@Override
	public void togglePause() {
		// TODO implementare
	}

	@Override
	public void setDirty() {
		dirty = true;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	private ChangeListener<Object> getSizeListener() {
		return new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				resize();
				drawBg(TOP_GRAPHICS_CONTEXT, TOP_CANVAS, (Image) loader.getTopHudBg().getBackground());
		    	drawBg(RIGHT_GRAPHICS_CONTEXT, RIGHT_CANVAS, (Image) loader.getRightHudBg().getBackground());
		    	drawBg(BOTTOM_GRAPHICS_CONTEXT, BOTTOM_CANVAS, (Image) loader.getBottomHudBg().getBackground());
		    	drawBg(LEFT_GRAPHICS_CONTEXT, LEFT_CANVAS, (Image) loader.getLeftHudBg().getBackground());
				drawBg(FIELD_GRAPHICS_CONTEXT, FIELD_CANVAS, (Image) loader.getFieldBg().getBackground());
		    	drawField(loader, field, FIELD_GRAPHICS_CONTEXT, spriteLen, nPlayer);
			}
		};
	}
    
	private void resize() {
    	double topDownHudHeight = ROOT.getHeight() * hudPercentage;
    	double canvasHeight = ROOT.getHeight() - (topDownHudHeight * 2);
    	spriteLen = canvasHeight / nCellHeight;
    	double canvasWidth = spriteLen * nCellWidth;
    	double leftRightHudWidth = (ROOT.getWidth() - canvasWidth) / 2;
    	
    	resizeCanvas(TOP_CANVAS, ROOT.getWidth(), topDownHudHeight);
    	resizeCanvas(LEFT_CANVAS, leftRightHudWidth, canvasHeight);
    	resizeCanvas(RIGHT_CANVAS, leftRightHudWidth, canvasHeight);
    	resizeCanvas(BOTTOM_CANVAS, ROOT.getWidth(), topDownHudHeight);
    	resizeCanvas(FIELD_CANVAS, canvasWidth, canvasHeight);
    	
    }

}
