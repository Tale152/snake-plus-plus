package design.view.game;

import java.util.List;

public interface PlayerHud {

	public String getName();
	
	public void setName(String name);
	
	public String getScore();
	
	public void setScore(String score);
	
	public boolean isAlive();
	
	public void setAlive(boolean alive);
	
	public void setSnakeSprite(Sprite sprite);
	
	public void addEffectSprite(Sprite sprite);
	
	public void resetEffectSpriteList();
	
	public List<Sprite> getSpriteList();
	
}
