package implementation.model.game;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import design.model.game.*;
import implementation.model.game.initializers.Utils;
import implementation.model.game.items.*;
import static implementation.model.game.UpdateField.updateField;
import static implementation.model.game.Initializers.*;
import static implementation.model.game.UpdateSnakes.updateSnakes;

public class GameImpl implements Game {
	
	private long gameTime;
	private final ItemCounter itemCounter;
	private final Field field;
	private final List<Snake> snakes;
	private final GameRules gameRules;
	private final WinConditions win;
	private final LossConditions loss;
	
	public GameImpl(GameRules gameRules, InitialGameState initialGameState, WinConditions win, LossConditions loss, long gameTime) {
		check(gameRules, initialGameState, win, loss, gameTime);
		this.gameTime = gameTime;
		this.gameRules = gameRules;
		itemCounter = new ItemCounter(gameRules);
		snakes = initSnakes(gameRules, initialGameState, gameTime);
		field = initField(initialGameState, snakes, itemCounter);
		this.win = win;
		this.loss = loss;
	}
	
	@Override
	public List<Item> update(long enlapsedTime) {
		gameTime += enlapsedTime;
		List<Item> differences = new ArrayList<>();
		updateSnakes(snakes, field, gameTime, differences, itemCounter);
		updateField(field, enlapsedTime, differences, gameRules, itemCounter);
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
	
	@Override
	public boolean winConditionsReached() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Method m : win.getClass().getDeclaredMethods()) {
			if ((boolean)m.invoke(win, this)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean lossConditionsReached() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Method m : loss.getClass().getDeclaredMethods()) {
			if ((boolean)m.invoke(loss, this)) {
				return true;
			}
		}
		return false;
	}
	
	private static void check(GameRules gameRules, InitialGameState initialGameState, WinConditions win, LossConditions loss, long gameTime) {
		Utils.throwNullPointer(gameRules == null || initialGameState == null || win == null || loss == null, "Null args");
		if (gameTime < 0) {
			throw new IllegalArgumentException("gameTime cannot be negative");
		}
	}

}
