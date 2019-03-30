package implementation.model.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import design.model.game.*;
import design.model.game.InitialGameState.InitialPlayerState;
import implementation.model.game.initializers.Utils;
import implementation.model.game.items.BodyPart;
import implementation.model.game.items.Wall;
import implementation.model.game.snake.SnakeImpl;

public class GameSnakesStatic {

	protected static void initSnakes(GameRules gameRules, InitialGameState initialGameState, List<Snake> snakes, long gameTime) {
		long delta = gameRules.getInitialSnakeDelta();
		int playerEnum = 0;
		for (InitialPlayerState player : initialGameState.getInitialPlayerState()) {
			PlayerNumber number = determinePlayerNumber(playerEnum++);
			String name = player.getName();
			Direction direction = player.getDirection();
			snakes.add(new SnakeImpl(number, name, direction, delta, 1, gameTime)); //TODO multiplier not always 1, cannot set initial coord
		}
	}
	
	protected static List<Item> updateSnakes(List<Snake> snakes, Field field, long gameTime){
		List<Item> differences = new ArrayList<>();
		for (Snake snake : snakes) {
			for (Effect effect : snake.getEffects()) {
				if (effect.getEffectEndTime().get() <= gameTime) {
					snake.removeEffect(effect);
				}
			}
			if (timeToUpdateSnake(snake, gameTime)) {
				updateSnakeTime(snake);
				differences.addAll(snake.move(getNextPoint(snake, field)));
				differences.addAll(collide(field, snake, gameTime));
			}
		}
		return differences;
	}
	
	private static PlayerNumber determinePlayerNumber(int i) {
		Utils.throwIllegalState(i >= PlayerNumber.values().length, "Something went wrong while getting player number");
		return PlayerNumber.values()[i];
	}
	
	private static boolean timeToUpdateSnake(Snake snake, long gameTime) {
		return snake.getProperties().getSpeed().getNextUpdate() >= gameTime;
	}
	
	private static void updateSnakeTime(Snake snake) {
		snake.getProperties().getSpeed().setLastUpdate(snake.getProperties().getSpeed().getNextUpdate());
	}
	
	private static int fieldLimits(int value, int limit) {
		if (value >= 0 && value < limit) {
			return value;
		}
		else if (value < 0) {
			return limit - 1;
		}
		else {
			return 0;
		}
	}
	
	private static Point getNextPoint(Snake snake, Field field) {
		Point point = snake.getBodyParts().get(0).getPoint();
		switch (snake.getProperties().getDirection().getDirection()) {
			case UP: point.y = fieldLimits(point.y - 1, field.getHeight()); return point;
			case DOWN: point.y = fieldLimits(point.y + 1, field.getHeight()); return point;
			case LEFT: point.x = fieldLimits(point.x - 1, field.getWidth()); return point;
			case RIGHT: point.x = fieldLimits(point.x + 1, field.getWidth()); return point;
		}
		throw new IllegalStateException();
	}
	
	private static List<Item> collide(Field field, Snake snake, long gameTime) {
		List<Item> differences = new ArrayList<>();
		Optional<List<Item>> cell = field.getCell(snake.getBodyParts().get(0).getPoint());
		if (!cell.isPresent()) {
			throw new IllegalStateException();
		}
		cell.get().remove(snake.getBodyParts().get(0));
		for (Item item : cell.get()) {
			item.onCollision(snake, gameTime);
			if (!(item instanceof Wall) || !(item instanceof BodyPart)) {
				field.removeItem(item);
				differences.add(item);
			}
		}
		return differences;
	}
	
}
