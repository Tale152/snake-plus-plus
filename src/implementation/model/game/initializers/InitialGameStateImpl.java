package implementation.model.game.initializers;

import java.awt.Point;
import java.util.*;
import design.model.game.*;

public class InitialGameStateImpl implements InitialGameState {

	private final List<Item> items;
	private final Point fieldSize;
	private final List<InitialPlayerState> initialPlayerStates;
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
	
	public String toString() {
		String res = "Field size:\t[" + fieldSize.x + "," + fieldSize.y + "]\n";
		int cells = fieldSize.x * fieldSize.y;
		res += "Total cells:\t" + cells + "\n";
		int freeCells = cells - items.size();
		for (InitialPlayerState p : initialPlayerStates) {
			freeCells -= p.getBodyPoints().size();
		}
		res += "Free cells:\t" + freeCells + "\n";
		res += "Occupied cells:\t" + (cells - freeCells) + "\n";
		res += "Max players:\t" + MAX_PLAYERS + "\n\n";
		res += "Current players: " + initialPlayerStates.size() + "\n";
		int i = 1;
		for (InitialPlayerState player : initialPlayerStates) {
			res += "---Player " + i++ + "---\n";
			res += player.toString();
		}
		res += "\nItems on field: " + items.size() + "\n";
		for (Item item : items) {
			res += "[" + item.getPoint().x + "," + item.getPoint().y + "]\t" + item.getClass().getSimpleName() + "\n";
		}
		return res;
	}
	
	private void check(List<Item> items, Point fieldSize, List<InitialPlayerState> initialPlayerStates) {
		List<Point> allPoints = new ArrayList<>();
		Utils.throwNullPointer(fieldSize == null || items == null || initialPlayerStates == null, "Null args");
		Utils.throwIllegalState(fieldSize.x < 0 || fieldSize.y < 0, "Field size cannot be negative");
		Utils.throwIllegalState(items.contains(null), "List items contains some null entries");
		boolean itemPointsValidity = items.stream().anyMatch(i -> { allPoints.add(i.getPoint()); return Utils.invalidPoint(fieldSize, i.getPoint());});
		Utils.throwIllegalState(itemPointsValidity, "Some items entries have invalid attribute point compared to field size");
		Utils.throwIllegalState(initialPlayerStates.isEmpty() || initialPlayerStates.size() > MAX_PLAYERS, "initialPlayerStates size must be greater than zero and cannot be greater than " + MAX_PLAYERS);
		for (InitialPlayerState player : initialPlayerStates) {
			allPoints.addAll(player.getBodyPoints());
			for (Point bodyPoint : player.getBodyPoints()) {
				Utils.throwIllegalState(Utils.invalidPoint(fieldSize, bodyPoint), "InitialPlayerState '" + player.getName() + "' has some invalid bodyPoints compared to field size");
			}
		}
		Utils.throwIllegalState(Utils.sameCoordinates(allPoints), "The coordinates of at least 2 points are euqual (looking into items and initialPlayerStates)");
	}

}
