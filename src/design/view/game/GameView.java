package design.view.game;

public interface GameView {

	public GameHud getHUD();
	
	public GameField getField();
	
	public void update();
	
	public void togglePause();
	
}
