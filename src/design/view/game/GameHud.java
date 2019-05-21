package design.view.game;

import java.util.List;

public interface GameHud {
	
	public Background getHudBackground();
	
	public void setTime(String time);
	
	public String getTime();
	
	public List<PlayerHud> getPlayerHUDs();
	
}
