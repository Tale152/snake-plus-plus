package implementation.model.game;

import java.awt.Point;
import java.util.*;
import design.model.game.*;
import design.model.game.InitialGameState.InitialPlayerState;
import implementation.model.game.initializers.Utils;
import implementation.model.game.items.BodyPart;
import implementation.model.game.items.Wall;
import implementation.model.game.snake.SnakeImpl;

public class GameStatic {

	private  GameStatic() {}
	
	private static boolean timeToUpdateSnake(Snake snake, long gameTime) {
		return snake.getProperties().getSpeed().getNextUpdate() >= gameTime;
	}
	
	private static void updateSnakeTime(Snake snake) {
		snake.getProperties().getSpeed().setLastUpdate(snake.getProperties().getSpeed().getNextUpdate());
	}
	
	private static Point getNextPoint(Snake snake, Field field) {
		//TODO 
		return null;
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
	
	protected static List<Item> updateSnakes(List<Snake> snakes, Field field, long gameTime){
		List<Item> differences = new ArrayList<>();
		for (Snake snake : snakes) {
			if (timeToUpdateSnake(snake, gameTime)) {
				updateSnakeTime(snake);
				differences.addAll(snake.move(getNextPoint(snake, field)));
				differences.addAll(collide(field, snake, gameTime));
			}
		}
		return differences;
	}
	
	protected static void initSnakes(GameRules gameRules, InitialGameState initialGameState, List<Snake> snakes, long gameTime) {
		long delta = gameRules.getInitialSnakeDelta();
		int playerEnum = 0;
		for (InitialPlayerState player : initialGameState.getInitialPlayerState()) {
			Utils.throwIllegalState(playerEnum >= PlayerNumber.values().length, "Something went wrong while getting player number");
			PlayerNumber number = PlayerNumber.values()[playerEnum++];
			String name = player.getName();
			Direction direction = player.getDirection();
			snakes.add(new SnakeImpl(number, name, direction, delta, 1, gameTime)); //TODO multiplier not always 1, cannot set initial coord
		}
	}
	
	protected static void initField() {
		//TODO
	}
	
	protected static List<Item> updateItems(){
		List<Item> differences = new ArrayList<>();
		//TODO
		return differences;
	}
	
	protected static void check(GameRules gameRules, InitialGameState initialGameState, long gameTime) {
		Utils.throwNullPointer(gameRules == null || initialGameState == null, "Null args");
		if (gameTime < 0) {
			throw new IllegalArgumentException("gameTime cannot be negative");
		}
	}
}
