package design.view.game;

import design.controller.game.GameController;

public interface GameView {

	public GameHud getHUD();
	
	public GameField getField();
	
	public void startRendering();
	
	public void stopRendering();
	
	public GameController getGameController();
	
}
