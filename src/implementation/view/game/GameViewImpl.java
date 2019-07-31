package implementation.view.game;

import design.controller.game.GameController;
import design.view.game.*;
import implementation.controller.game.GameControllerImpl;
import implementation.controller.game.InputEventFX;
import javafx.animation.AnimationTimer;
import javafx.beans.value.*;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.*;
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
	private final double hudPercentage;
	private final ResourcesLoader loader;
  
	private BackgroundPane root;
	private double labelY = 0;
	private double timeLabelX = 0;
	private double playerSpacingX = 0;
	private double scoreSpacingY = 0;
	private double namesSpacingY = 0;
	private double hudSpritesSpacingY = 0;
	private double hudSpritesDimension = 0;
	
	private Font timeFont;
	private Font playerFont;
	
	private final AnimationTimer animationTimer;
	
    public GameViewImpl(Scene scene, String levelPath, String resourcesPath, List<String> playerNames, int nCellWidth, int nCellHeight) throws FileNotFoundException, IOException {
    	loader = new ResourcesLoaderFromFile(resourcesPath, nCellWidth, nCellHeight);
		hudPercentage = calculateHudPercentage(nCellWidth, nCellHeight);
	    hud = new GameHudImpl(playerNames.size(), loader);
		field = new GameFieldImpl(playerNames.size(), loader);
		root = initRoot(scene, playerNames.size(), nCellWidth, nCellHeight);
    	animationTimer = initAnimationTimer(playerNames.size());
    	initGameController(scene, levelPath, playerNames);
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
	public void startRendering() {
		animationTimer.start();
	}
	
	@Override
	public void stopRendering() {
		animationTimer.stop();
	}
	
	private AnimationTimer initAnimationTimer(int nPlayers) {
    	return new AnimationTimer()
	    {
	        public void handle(long currentNanoTime)
	        {
	        	drawBg(root.getBackgroundGraphicsContext(), root.getBackgroundCanvas(), (Image) loader.getHudBackground().getBackground());
    			drawBg(root.getFieldGraphicsContext(), root.getFieldCanvas(), (Image) loader.getFieldBackground().getBackground());
    	    	drawField(loader, field, root.getFieldGraphicsContext(), root.getSpriteSize(), nPlayers);
    			drawHud(nPlayers);
	        }
	    };
    }
	
	private BackgroundPane initRoot(Scene scene, int nPlayers, int nCellWidth, int nCellHeight) throws FileNotFoundException, IOException {
    	BackgroundPane root = new BackgroundPane(hudPercentage, nCellWidth, nCellHeight);
		scene.setRoot(root);
	    root.widthProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				timeLabelX = root.getWidth() / 2; 
				playerSpacingX = root.getWidth() / (nPlayers + 1);
			}
		});
	    root.heightProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				labelY = (root.getHeight() * hudPercentage) / 2;
				scoreSpacingY = root.getHeight() - (((root.getHeight() * hudPercentage) / 5) * 2.5);
				namesSpacingY = root.getHeight() - (((root.getHeight() * hudPercentage) / 5) * 4);
				hudSpritesSpacingY = root.getHeight() - (((root.getHeight() * hudPercentage) / 5) * 1.5);
				hudSpritesDimension = ((root.getHeight() * hudPercentage)) / 4;
				timeFont = new Font(root.getBackgroundGraphicsContext().getFont().getName(), 
						root.getHeight() * hudPercentage * TIME_HEIGHT_PERCENTAGE);
				playerFont = new Font(root.getBackgroundGraphicsContext().getFont().getName(), 
						root.getHeight() * hudPercentage * PLAYER_HEIGHT_PERCENTAGE / 3);
			}
		});
	    timeFont = new Font(root.getBackgroundGraphicsContext().getFont().getName(), 
				root.getWidth() * hudPercentage * TIME_HEIGHT_PERCENTAGE);
	    playerFont = new Font(root.getBackgroundGraphicsContext().getFont().getName(), 
				root.getHeight() * hudPercentage * PLAYER_HEIGHT_PERCENTAGE / 3);
	    root.getBackgroundGraphicsContext().setTextAlign(TextAlignment.CENTER);
	    root.getBackgroundGraphicsContext().setTextBaseline(VPos.CENTER);
    	root.getBackgroundGraphicsContext().setFill(Color.BLACK);
	    return root;
    }
    
    private void initGameController(Scene scene, String levelPath, List<String> playerNames) throws IOException {
    	GameController controller = new GameControllerImpl(levelPath, playerNames, this, loader);
    	scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
    		controller.playerInput(new InputEventFX(key));
    	});
    	Thread t  = new Thread(controller);
    	t.start();
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
    
    private void drawHud(int nPlayers) {
		root.getBackgroundGraphicsContext().setFont(timeFont);
    	root.getBackgroundGraphicsContext().fillText(hud.getTime(), timeLabelX, labelY);
    	root.getBackgroundGraphicsContext().setFont(playerFont);
    	for (int i = 0; i < nPlayers; i++) {
    		root.getBackgroundGraphicsContext().fillText(hud.getPlayerHUDs().get(i).getName(), 
    				(playerSpacingX * i) + playerSpacingX,
    				namesSpacingY);
    		root.getBackgroundGraphicsContext().fillText(hud.getPlayerHUDs().get(i).getScore(), 
    				(playerSpacingX * i) + playerSpacingX,
    				scoreSpacingY);
    		drawPlayerHeadAndActiveItems(i);
    		if (!hud.getPlayerHUDs().get(i).isAlive()) {
    			Double deadDim = root.getHeight() * hudPercentage;
    			root.getBackgroundGraphicsContext().drawImage((Image)loader.getDeadPlayerIndicator().getSprite(),
    					(playerSpacingX * i) + playerSpacingX - (deadDim / 2), root.getHeight() - deadDim, 
    					deadDim, deadDim);
    		}
    	}
	}
    
    private void drawPlayerHeadAndActiveItems(int nPlayer) {
    	List<Sprite> spriteList = hud.getPlayerHUDs().get(nPlayer).getSpriteList();
    	double totalSpaceSprites = ((spriteList.size() * hudSpritesDimension) + ((hudSpritesDimension * 0.25) * (spriteList.size() - 1)));
    	double leftCorner = (playerSpacingX * nPlayer) + playerSpacingX - (totalSpaceSprites/2);
    	int i = 0;
    	for (Sprite sprite : spriteList) {
    		double xSpacing = leftCorner + (hudSpritesDimension * 1.25 * i);
    		root.getBackgroundGraphicsContext().drawImage((Image)sprite.getSprite(), xSpacing, hudSpritesSpacingY, 
    				hudSpritesDimension, hudSpritesDimension);
    		++i;
    	}
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
