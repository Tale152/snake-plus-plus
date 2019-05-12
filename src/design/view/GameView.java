package design.view;

public interface GameView {

	public GameHud getHUD();
	
	public GameField getField();
	
	public void update();
	
	public void togglePause();
	
	public void setDirty();
	
}
