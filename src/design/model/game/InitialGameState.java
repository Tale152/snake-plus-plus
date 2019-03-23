package design.model.game;

import java.awt.Point;
import java.util.List;
import design.model.game.Direction;
import design.model.game.Item;

public interface InitialGameState {
	
	public List<Item> getFieldItems();
	
	public Point getFieldSize();
	
	public interface InitialPlayerState {
		String getName();
		Point getPosition();
		Direction getDirection();
		int getScore();
	}
	
	public List<InitialPlayerState> getInitialPlayerState();
}
