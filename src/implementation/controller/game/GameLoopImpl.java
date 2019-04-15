package implementation.controller.game;

import java.util.ArrayList;
import java.util.List;

import design.controller.application.Action;
import design.controller.game.GameLoop;

public class GameLoopImpl implements GameLoop {

	private boolean pause;
	private long lastUpdate;
	private final List<Action> actions;
	
	@Override
	public void run() {
		// TODO Initialize the game.
		
		// Actual loop (while !game.isOver()? dunno)
		// three phases:
		//	1) Handle input: toggle pause, send snake actions to game
		//	2) Update: get current time, subtract last update, pass delta to
		//			game.update, update last update time to current time
		//	3) Draw: send to View things to remove and/or add to screen
	}

	@Override
	public void pushAction(Action action) {
		// TODO put action into internal Q

	}

	@Override
	public void togglePause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPaused() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public GameLoopImpl() {
		actions = new ArrayList<Action>();
	}

}
