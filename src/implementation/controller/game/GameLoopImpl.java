package implementation.controller.game;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import design.controller.application.Action;
import design.controller.game.GameInitializer;
import design.controller.game.GameLoader;
import design.controller.game.GameLoop;
import design.controller.game.SpritesLoader;
import design.model.game.Item;
import design.view.GameView;


public class GameLoopImpl implements GameLoop {

	private boolean pause;
	private long lastUpdate;
	private long currentTime;
	private long deltaT;
	private final List<Action> actions;
	private GameLoader gameLoader;
	private SpritesLoader spritesLoader;
	private final GameView gameView;
	
	public GameLoopImpl(List<File> snakes, File items, File file, List<String> playerNames, List<Integer> playerScores, GameView gameView) 
			throws FileNotFoundException, ClassNotFoundException, IOException {
		this.actions = new ArrayList<Action>();
		this.pause = false;
		this.spritesLoader = new SpritesLoaderFromFile(snakes, items);
		this.gameLoader = new GameLoaderFromFile(file, playerNames, playerScores);
		this.currentTime = System.currentTimeMillis();
		this.lastUpdate = 0;
		this.gameView = gameView;
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
		int i = 0;
		
		try {
			while(!game.getGame().lossConditionsReached() || !game.getGame().winConditionsReached()) {
				while(!this.actions.isEmpty()) {
					game.getGame().setSnakeDirection(this.actions.get(i).getPlayerNumber(), this.actions.get(i).getDirection());
					this.actions.remove(i);
				}
				
				//metti if che controlla se il gioco è in pausa, perché se lo è non devo aggiornare il tempo
				this.deltaT = this.currentTime - this.lastUpdate;
				if(!this.pause) {
					game.getGame().update(this.deltaT);
				}
				this.lastUpdate = this.currentTime;
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
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
	
}
