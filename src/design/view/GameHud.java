package design.view;

import javafx.scene.image.Image;

public interface GameHud {
	
	public void setBackground(Image image);
	
	public void setTime(String time);
	
	public void setPlayerName(int playerNumber, String name);
	
	public void setPlayerScore(int playerNumber, String score);
	
	public void setPlayerDead(int playerNumber, boolean dead);
}
