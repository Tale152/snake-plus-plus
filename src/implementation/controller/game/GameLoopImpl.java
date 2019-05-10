package implementation.controller.game;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import design.controller.application.Action;
import design.controller.game.GameInitializer;
import design.controller.game.GameLoader;
import design.controller.game.GameLoop;
import design.controller.game.Sprite;
import design.controller.game.SpritesLoader;
import design.model.game.Item;
import design.view.GameView;
import javafx.scene.image.Image;


public class GameLoopImpl implements GameLoop {
	
	
	private boolean pause;
	private long lastUpdate;
	private long currentTime;
	private long deltaT;
	private final List<Action> actions;
	private GameLoader gameLoader;
	private SpritesLoader spritesLoader;
	private final GameView gameView;
	private String spriteDirectory;
	private SpritesLoader sprites;
	
	public GameLoopImpl(String spriteDirectory,File gameFile, List<String> playerNames, List<Integer> playerScores, GameView gameView, SpritesLoader sprites) 
			throws FileNotFoundException, ClassNotFoundException, IOException {
		this.actions = new ArrayList<Action>();
		this.pause = false;
		this.gameLoader = new GameLoaderFromFile(gameFile, playerNames, playerScores);
		this.currentTime = System.currentTimeMillis();
		this.lastUpdate = 0;
		this.gameView = gameView;
		this.spriteDirectory = spriteDirectory;
		this.sprites = sprites;
	}
	
	// Actual loop (while !game.isOver()? dunno)
	// three phases:
	//	1) Handle input: toggle pause, send snake actions to game
	//	2) Update: get current time, subtract last update, pass delta to
	//			game.update, update last update time to current time
	//	3) Draw: send to View things to remove and/or add to screen
	@Override
	public void run() {
		GameInitializer game = new GameInitializerImpl(this.gameLoader, this.spritesLoader, this.currentTime);
		init(game);
		while(!isGameEnded(game)) {
			if(!this.pause) {
				inGame(game);
			}
		}
		postGame(game);
	}

	private void init(GameInitializer game) {
		//TO DO: collegare i controlli 
		Map<Point, String> allWalls = GameLoopStatic.computeAllWalls(game.getGame().getWalls());
		for(Entry<Point, String> wall: allWalls.entrySet()) {		
			Optional<Sprite> sprite = sprites.getWalls().stream().filter(e -> {
				return e.getName().equals(wall.getValue());
			}).findFirst();
			spriteCheck(sprite);
			this.gameView.getField().addItemSprite(wall.getKey(), sprite.get().getSprite());
		}
		
		for(Item item : game.getGame().getItems()) {
			String itemName = GameLoopStatic.computeItem(item);
			Optional<Sprite> sprite = sprites.getItems().stream().filter(e -> {
				return e.getName().equals(itemName);
			}).findFirst();
			spriteCheck(sprite);
			this.gameView.getField().addItemSprite(item.getPoint(), sprite.get().getSprite());
		}
		
		for(int i = 0; i < game.getGame().getSnakes().size(); i++) {
			Map<Point, String> snake = GameLoopStatic.computeASnake(game.getGame().getSnakes().get(i));
			for(Entry<Point, String> s: snake.entrySet()) {
				Optional<Sprite> sprite = sprites.getSnakes().get(i).stream().filter(e -> {
					return e.getName().equals(s.getValue());
				}).findFirst();
				spriteCheck(sprite);
				this.gameView.getField().addBodyPart(i, s.getKey(), sprite.get().getSprite());
			}
		}
		this.gameView.update();
	}
	
	private void spriteCheck(Optional<Sprite> sprite) {
		if (!sprite.isPresent()) {
			throw new IllegalStateException();
		}
	}
	
	private void inGame(GameInitializer game) {
		
	}
	
	private void postGame(GameInitializer game) {
		
	}
	
	@Override
	public void pushAction(Action action) {
		this.actions.add(action);
	}

	@Override
	public void togglePause() {
		if(this.pause) {
			this.pause = false;
		} else {
			this.pause = true;
		}
	}

	@Override
	public boolean isPaused() {
		return this.pause;
	}
	
	private boolean isGameEnded(GameInitializer game) {
		try {
			return game.getGame().lossConditionsReached() || game.getGame().winConditionsReached();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return true;
	}
	
}
