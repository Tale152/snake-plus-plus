package implementation.model.game.initializers;

import java.awt.Point;
import java.util.*;
import design.model.game.*;

public class InitialGameStateImpl implements InitialGameState {

	List<Item> items;
	Point fieldSize;
	List<InitialPlayerState> initialPlayerStates;
	public static final int MAX_PLAYERS = 4;
	
	public InitialGameStateImpl(List<Item> items, Point fieldSize, List<InitialPlayerState> initialPlayerStates) {
		if (items == null || items.contains(null) 
				|| fieldSize == null || fieldSize.x <= 0 || fieldSize.y <= 0 
				|| initialPlayerStates == null || initialPlayerStates.isEmpty() 
				|| initialPlayerStates.size() > MAX_PLAYERS || initialPlayerStates.contains(null)) {
			throw new IllegalStateException();
		}
		this.items = items;
		this.fieldSize = fieldSize;
		this.initialPlayerStates = initialPlayerStates;
	}
	
	@Override
	public List<Item> getFieldItems() {
		return new ArrayList<>(items);
	}

	@Override
	public Point getFieldSize() {
		return new Point(fieldSize.x, fieldSize.y);
	}

	@Override
	public List<InitialPlayerState> getInitialPlayerState() {
		return new ArrayList<>(initialPlayerStates);
	}

}
