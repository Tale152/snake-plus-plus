package design.model.game;

public interface Player {

	public PlayerNumber getPlayerNumber();
	
	public String getName();
	
	public void addScore(int score);
	
	public void reduceScore(int score);
	
	public void applyScoreMultiplier(double mult);
	
	public double getScoreMultiplier();
	
	public int getScore();
	
}
