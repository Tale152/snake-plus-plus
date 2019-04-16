package design.model.game;

import java.util.List;

public interface Game {

	public long getGameTime();
	
	public boolean setSnakeDirection(PlayerNumber player, Direction direction);
	
	public List<Snake> getSnakes();
	
	public List<Item> getItems();
	
	public List<Item> getWalls();
	
	public List<Item> update(long enlapsedTime);
	
	public boolean winConditionsReached();
	
	public boolean lossConditionsReached();
	
}
