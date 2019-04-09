package implementation.model.game.items;

import java.awt.Point;
import java.util.List;

import design.model.game.*;
import implementation.model.game.snake.SnakeImpl;

public class SnakeFactoryForTests {

	public static Snake baseSnake(List<Point> points) {
		return new SnakeImpl(points, PlayerNumber.PLAYER1, "Player", Direction.RIGHT, 1000L, 1.0, 0L);
	}
	
	public static Snake ghostSnake(List<Point> points) {
		Snake snake = baseSnake(points);
		snake.getProperties().getCollision().setIntangibility(true);
		return snake;
	}
	
	public static Snake godSnake(List<Point> points) {
		Snake snake = baseSnake(points);
		snake.getProperties().getCollision().setInvincibility(true);
		return snake;
	}
	
	public static Snake springSnake(List<Point> points) {
		Snake snake = baseSnake(points);
		snake.getProperties().getCollision().setSpring(true);
		return snake;
	}
	
}
