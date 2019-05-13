package design.view;

import java.util.List;

public interface GameHud {
	
	public Background getHudBackground();
	
	public void setTime(Long time);
	
	public Long getTime();
	
	public List<PlayerHud> getPlayerHUDs();
	
}
