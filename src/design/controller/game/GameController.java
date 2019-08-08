package design.controller.game;

public interface GameController extends Runnable{
	
	public void playerInput(InputEvent input);
	
	public boolean isGameEnded();
	
}
