package implementation.model.game;

import java.util.*;
import design.model.game.*;
import implementation.model.game.initializers.Utils;
import implementation.model.game.items.*;
import static implementation.model.game.UpdateField.updateField;
import static implementation.model.game.Initializers.*;
import static implementation.model.game.UpdateSnakes.updateSnakes;

public class GameImpl implements Game {
	
	private long gameTime;
	private final Field field;
	private final List<Snake> snakes;
	private final GameRules gameRules;
	
	public GameImpl(GameRules gameRules, InitialGameState initialGameState, long gameTime) {
		check(gameRules, initialGameState, gameTime);
		this.gameTime = gameTime;
		this.gameRules = gameRules;
		snakes = initSnakes(gameRules, initialGameState, gameTime);
		field = initField(initialGameState, snakes);
	}
	
	@Override
	public List<Item> update(long enlapsedTime) {
		gameTime += enlapsedTime;
		List<Item> differences = new ArrayList<>();
		updateSnakes(snakes, field, gameTime, differences);
		updateField(field, enlapsedTime, differences, gameRules);
		return differences;
	}
	
	@Override
	public long getGameTime() {
		return gameTime;
	}

	@Override
	public boolean setSnakeDirection(PlayerNumber player, Direction direction) {
		Optional<Snake> target = snakes.stream()
							.filter(s -> {return s.getPlayer().getPlayerNumber().equals(player);})
							.findFirst();
		if (target.isPresent() && target.get().isAlive()) {
			target.get().getProperties().getDirection().setDirection(direction);
			return true;
		}
		return false;
	}

	@Override
	public List<Snake> getSnakes() {
		return new ArrayList<Snake>(snakes);
	}

	@Override
	public List<Item> getItems() {
		List<Item> items = new ArrayList<>();
		field.getItems().stream()
					.filter(i -> { return !(i instanceof Wall) && !(i instanceof BodyPart); })
					.forEach(item -> { items.add(item); });
		return items;
	}

	@Override
	public List<Item> getWalls() {
		List<Item> walls = new ArrayList<>();
		field.getItems().stream()
					.filter(i -> { return i instanceof Wall; })
					.forEach(item -> { walls.add(item); });
		return walls;
	}
	
	private static void check(GameRules gameRules, InitialGameState initialGameState, long gameTime) {
		Utils.throwNullPointer(gameRules == null || initialGameState == null, "Null args");
		if (gameTime < 0) {
			throw new IllegalArgumentException("gameTime cannot be negative");
		}
	}

}
