package design.model.game;

public interface WinConditions {
	
	public boolean checkSnakeLength(Game game);
	
	public boolean checkScore(Game game);
	
	public boolean checkTime(Game game);
	
	public boolean checkFieldLimit(Game game);
	
}
