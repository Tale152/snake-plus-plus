package design.controller.game;

import design.controller.application.Action;

public interface GameLoop extends Runnable {
	
	public void pushAction(Action action);
	
	public void togglePause();
	
	public boolean isPaused();

}
