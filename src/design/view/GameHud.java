package design.view;

import java.util.List;

public interface GameHud {
	
	public HudBackgrounds getHudBackgrounds();
	
	public void setTime(Long time);
	
	public Long getTime();
	
	public List<PlayerHud> getPlayerHUDs();
	
}
