package design.view.game;

public interface PlayerHud {

	public String getName();
	
	public void setName(String name);
	
	public String getScore();
	
	public void setScore(String score);
	
	public boolean isAlive();
	
	public void setAlive(boolean alive);
	
}
