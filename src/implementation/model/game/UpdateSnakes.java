package implementation.model.game;

import java.awt.Point;
import java.util.*;
import design.model.game.*;
import implementation.model.game.items.*;

public class UpdateSnakes {

	private UpdateSnakes() {}
	
	public static void updateSnakes(List<Snake> snakes, Field field, long gameTime, List<Item> differences){
		snakes.stream()
			.filter(s ->{return s.isAlive();})
			.forEach(Snake ->{
				updateSnakeEffects(Snake, gameTime);
				if (timeToMoveSnake(Snake, gameTime)) {
					updateSnakePosition(Snake, gameTime, differences, field);
				}
			});
	}
	
	private static void updateSnakeEffects(Snake snake, long gameTime) {
		snake.getEffects().stream()
			.filter(e -> {return effectEnded(e, gameTime);})
			.forEach(Effect -> {
				snake.removeEffect(Effect);
			});
	}
	
	private static boolean effectEnded(Effect effect, long gameTime) {
		return effect.getEffectEndTime().get() <= gameTime;
	}
	
	private static boolean timeToMoveSnake(Snake snake, long gameTime) {
		return snake.getProperties().getSpeed().getNextUpdate() <= gameTime;
	}
	
	private static void updateSnakePosition(Snake snake, long gameTime, List<Item> differences, Field field){
		updateSnakeTime(snake);
		moveSnake(snake, field, gameTime, differences);
	}
	
	private static void updateSnakeTime(Snake snake) {
		snake.getProperties().getSpeed().setLastUpdate(snake.getProperties().getSpeed().getNextUpdate());
	}
	
	private static void moveSnake(Snake snake, Field field, long gameTime, List<Item> differences) {
		differences.addAll(snake.move(getNextPoint(snake, field)));
		collide(field, snake, gameTime, differences);
	}
	
	private static Point getNextPoint(Snake snake, Field field) {
		Point point = snake.getBodyParts().get(0).getPoint();
		switch (snake.getProperties().getDirection().getDirection()) {
			case UP: point.y = ((point.y - 1) < 0 ) ? (field.getHeight() - 1) : (point.y - 1); return point;
			case DOWN: point.y = ((point.y + 1) >= field.getHeight() ) ? 0 : (point.y + 1); return point;
			case LEFT: point.x = ((point.x - 1) < 0 ) ? (field.getWidth() - 1) : (point.x - 1); return point;
			case RIGHT: point.x = ((point.x + 1) >= field.getWidth() ) ? 0 : (point.x + 1); return point;
		}
		throw new IllegalStateException();
	}
	
	private static void collide(Field field, Snake snake, long gameTime, List<Item> differences) {
		Optional<List<Item>> cell = field.getCell(snake.getBodyParts().get(0).getPoint());
		if (!cell.isPresent()) {
			throw new IllegalStateException();
		}
		cell.get().remove(snake.getBodyParts().get(0));
		for (Item item : cell.get()) {
			item.onCollision(snake, gameTime);
			if ((!(item instanceof Wall) || !(item instanceof BodyPart)) && snake.getProperties().getCollision().getIntangibility() == false) {
				field.removeItem(item);
				differences.add(item);
			}
		}
	}
	
}