package implementation.model.game.items;

import java.awt.Point;
import java.util.List;

import design.model.game.*;
import implementation.model.game.snake.SnakeImpl;

public class SnakeFactoryForTests {

	public static Snake baseSnake(List<Point> points, Field field) {
		return new SnakeImpl(PlayerNumber.PLAYER1, "Player", Direction.RIGHT, 1000L, 1.0, field,points);
	}
	
	public static Snake ghostSnake(List<Point> points, Field field) {
		Snake snake = baseSnake(points, field);
		snake.getProperties().getCollisionProperty().setIntangibility(true);
		return snake;
	}
	
	public static Snake godSnake(List<Point> points, Field field) {
		Snake snake = baseSnake(points, field);
		snake.getProperties().getCollisionProperty().setInvincibility(true);
		return snake;
	}
	
	public static Snake springSnake(List<Point> points, Field field) {
		Snake snake = baseSnake(points, field);
		snake.getProperties().getCollisionProperty().setSpring(true);
		return snake;
	}
	
}
