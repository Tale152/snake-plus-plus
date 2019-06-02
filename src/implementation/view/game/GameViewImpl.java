package implementation.view.game;

import design.controller.game.GameController;
import design.view.game.*;
import implementation.controller.game.GameControllerImpl;
import javafx.beans.value.*;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

public class GameViewImpl implements GameView {

	private static final double PLAYER_HEIGHT_PERCENTAGE = 0.9;
	private static final double TIME_HEIGHT_PERCENTAGE = 0.9;
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
  
	private BackgroundPane root;
	private double labelY = 0;
	private double timeLabelX = 0;
	private double playerSpacingX = 0;
	private double scoreSpacingY = 0;
	private double namesSpacingY = 0;
	
	private Font timeFont;
	private Font playerFont;
	
    public GameViewImpl(int nPlayer, ResourcesLoader loader, int nCellWidth, int nCellHeight) throws FileNotFoundException, IOException {
		this.loader = loader;
		this.nPlayer = nPlayer;
		this.hudPercentage = calculateHudPercentage(nCellWidth, nCellHeight);
	    hud = new GameHudImpl(nPlayer, loader);
		field = new GameFieldImpl(nPlayer, loader);
		root = new BackgroundPane(hudPercentage, nCellWidth, nCellHeight);
		scene = new Scene(root);
	    root.widthProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				timeLabelX = root.getWidth() / 2; 
				playerSpacingX = root.getWidth() / (nPlayer + 1);
				update();
			}
		});
	    root.heightProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				labelY = (root.getHeight() * hudPercentage) / 2;
				scoreSpacingY = root.getHeight() - (root.getHeight() * hudPercentage) / 3;
				namesSpacingY = root.getHeight() - (((root.getHeight() * hudPercentage) / 3) * 2);
				timeFont = new Font(root.getBackgroundGraphicsContext().getFont().getName(), 
						root.getHeight() * hudPercentage * TIME_HEIGHT_PERCENTAGE);
				playerFont = new Font(root.getBackgroundGraphicsContext().getFont().getName(), 
						root.getHeight() * hudPercentage * PLAYER_HEIGHT_PERCENTAGE / 2);
				update();
			}
		});
	    
	    timeFont = new Font(root.getBackgroundGraphicsContext().getFont().getName(), 
				root.getWidth() * hudPercentage * TIME_HEIGHT_PERCENTAGE);
	    playerFont = new Font(root.getBackgroundGraphicsContext().getFont().getName(), 
				root.getHeight() * hudPercentage * PLAYER_HEIGHT_PERCENTAGE / 2);
	    root.getBackgroundGraphicsContext().setTextAlign(TextAlignment.CENTER);
	    root.getBackgroundGraphicsContext().setTextBaseline(VPos.CENTER);
    	root.getBackgroundGraphicsContext().setFill(Color.BLACK);
    	
    	//TODO 
    	GameController controller = new GameControllerImpl("", new ArrayList<String>(Arrays.asList("Viroli")), this, loader);
    	Thread t  = new Thread(controller);
    	t.start();
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
		drawBg(root.getBackgroundGraphicsContext(), root.getBackgroundCanvas(), (Image) loader.getHudBackground().getBackground());
		drawBg(root.getFieldGraphicsContext(), root.getFieldCanvas(), (Image) loader.getFieldBackground().getBackground());
    	drawField(loader, field, root.getFieldGraphicsContext(), root.getSpriteSize(), nPlayer);
		root.getBackgroundGraphicsContext().setFont(timeFont);
    	root.getBackgroundGraphicsContext().fillText(hud.getTime(), timeLabelX, labelY);
    	root.getBackgroundGraphicsContext().setFont(playerFont);
    	for (int i = 0; i < nPlayer; i++) {
    		root.getBackgroundGraphicsContext().fillText(hud.getPlayerHUDs().get(i).getName(), 
    				(playerSpacingX * i) + playerSpacingX,
    				namesSpacingY);
    		root.getBackgroundGraphicsContext().fillText(hud.getPlayerHUDs().get(i).getScore(), 
    				(playerSpacingX * i) + playerSpacingX,
    				scoreSpacingY);
    	}
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
    	for (Entry<Point, Sprite> entry : field.getWallSprites().entrySet()) {
    		drawSprite(fieldGC, (Image) entry.getValue().getSprite(), entry.getKey(), spriteLen);
    	}
    	for (int i = 0; i < nPlayer; i++) {
    		for (Entry<Point, List<Sprite>> entry : field.getSnakeSprites(i).entrySet()) {
    			for (int j = entry.getValue().size() - 1; 0 <= j; j--) {
    				drawSprite(fieldGC, (Image) entry.getValue().get(j).getSprite(), entry.getKey(), spriteLen);
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
