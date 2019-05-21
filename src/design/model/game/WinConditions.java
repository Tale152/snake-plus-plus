package design.model.game;

import java.util.List;

public interface WinConditions {
	
	public boolean checkSnakeLength(List<Snake> snakes);
	
	public boolean checkScore(List<Snake> snakes);
	
	public boolean checkTime(Long time);
	
}
