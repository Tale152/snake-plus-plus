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
		check(items, fieldSize, initialPlayerStates);
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
	
	private boolean invalidPoint(Point field, Point toCheck) {
		if (toCheck.x >= field.x || toCheck.x < 0) {
			return true;
		}
		else if (toCheck.y >= field.y || toCheck.y < 0) {
			return true;
		}
		return false;
	}
	
	private void check(List<Item> items, Point fieldSize, List<InitialPlayerState> initialPlayerStates) {
		if (fieldSize == null) {
			throw new NullPointerException("Argument fieldSize into InitialGameStateImpl's contructor cannot be null");
		}
		else if (fieldSize.x < 0 || fieldSize.y < 0) {
			throw new IllegalArgumentException("Field size cannot be negative");
		}
		else if (items == null) {
			throw new NullPointerException("Argument items into InitialGameStateImpl's contructor cannot be null");
		}
		else if (items.contains(null)) {
			throw new IllegalStateException("List items contains some null entries");
		}
		else if (items.stream().anyMatch(i -> { return invalidPoint(fieldSize, i.getPoint());})) {
			throw new IllegalStateException("Some items entries have invalid attribute point compared to field size");
		}
		else if (initialPlayerStates == null) {
			throw new NullPointerException("Argument initialPlayerStates into InitialGameStateImpl's contructor cannot be null");
		}
		else if (initialPlayerStates.isEmpty() || initialPlayerStates.size() > MAX_PLAYERS) {
			throw new IllegalArgumentException("initialPlayerStates size must be greater than zero and cannot be greater than " + MAX_PLAYERS);
		}
		for (InitialPlayerState player : initialPlayerStates) {
			for (Point bodyPoint : player.getBodyPoints()) {
				if (invalidPoint(fieldSize, bodyPoint)) {
					throw new IllegalStateException("InitialPlayerState '" + player.getName() + "' has some invalid bodyPoints compared to field size");
				}
			}
		}
	}

}
