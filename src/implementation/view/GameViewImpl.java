package implementation.view;

import design.view.*;
import javafx.application.Platform;
import javafx.beans.value.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.*;
import java.util.List;
import java.util.Map.Entry;

public class GameViewImpl implements GameView {

	private static final double MIN_HUD_PERCENTAGE = 0.1;
	private static final double DELTA_HUD_PERCENTAGE = 0.005;
	private static final double HUD_ERROR_PERCENTAGE = 0.5;
	private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
	private final GameHud hud;
	private final GameField field;
	private final int nPlayer;
	private final double hudPercentage;
	private final Scene scene;
	private final ResourcesLoader loader;
   
	private boolean dirty;
	private BackgroundPane root;
	
    public GameViewImpl(int nPlayer, ResourcesLoader loader, int nCellWidth, int nCellHeight) throws FileNotFoundException, IOException {
		this.loader = loader;
		this.nPlayer = nPlayer;
		this.hudPercentage = calculateHudPercentage(nCellWidth, nCellHeight);
	    dirty = false;
	    hud = new GameHudImpl(nPlayer, loader);
		field = new GameFieldImpl(this, nPlayer, loader);
		root = new BackgroundPane(hudPercentage, nCellWidth, nCellHeight);
		scene = new Scene(root);
	    root.widthProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				refresh();
			}
		});
	    root.heightProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				refresh();
			}
		});
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
			refresh();
			dirty = false;
		}
	}
	
	private void refresh() {
		//TODO cambiare gli sfondi
		drawBg(root.getBackgroundGraphicsContext(), root.getBackgroundCanvas(), (Image) loader.getHudBackground().getBackground());
		drawBg(root.getFieldGraphicsContext(), root.getFieldCanvas(), (Image) loader.getFieldBackground().getBackground());
    	drawField(loader, field, root.getFieldGraphicsContext(), root.getSpriteSize(), nPlayer);
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
	
	private void drawBg(GraphicsContext gc, Canvas canvas, Image bg) {
    	gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.drawImage(bg, 0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
    private void drawField(ResourcesLoader loader, GameField field, GraphicsContext fieldGC, double spriteLen, int nPlayer) {
    	for (Entry<Point, Sprite> entry : field.getItemSprites().entrySet()) {
    		drawSprite(fieldGC, (Image) entry.getValue().getSprite(), entry.getKey(), spriteLen);
    	}
    	for (int i = 0; i < nPlayer; i++) {
    		for (Entry<Point, List<Sprite>> entry : field.getSnakeSprites(i).entrySet()) {
    			for (Sprite sprite : entry.getValue()) {
    				drawSprite(fieldGC, (Image) sprite.getSprite(), entry.getKey(), spriteLen);
    			}
    		}
    	}
    }
    
    private void drawSprite(GraphicsContext fieldGC, Image sprite, Point point, double spriteLen) {
    	fieldGC.drawImage(sprite, point.x * spriteLen, point.y * spriteLen, spriteLen, spriteLen);
    }
    
    private double calculateHudPercentage(int nCellWidth, int nCellHeight) {
		double percentage = MIN_HUD_PERCENTAGE;
		while(true) {
			if (percentage >= HUD_ERROR_PERCENTAGE) {
				throw new IllegalStateException("Cannot size screen with this nCellWidth and nCellHeight");
			}
			double hudHeight = SCREEN_SIZE.getHeight() * percentage;
			double fieldHeight = SCREEN_SIZE.getHeight() - (hudHeight * 2);
			double cellSize = fieldHeight / nCellHeight;
			if (cellSize * nCellWidth < SCREEN_SIZE.getWidth()) {
				break;
			}
			else {
				percentage += DELTA_HUD_PERCENTAGE;
			}
		}
		return percentage;
	}

}
