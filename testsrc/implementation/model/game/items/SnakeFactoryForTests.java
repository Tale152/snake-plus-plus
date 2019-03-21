package implementation.model.game.items;

import design.model.game.*;
import implementation.model.game.snake.SnakeImpl;

public class SnakeFactoryForTests {

	public static Snake baseSnake() {
		return new SnakeImpl(PlayerNumber.PLAYER1, "Player", Direction.RIGHT, 1000, 1, 0);
	}
	
	public static Snake ghostSnake() {
		Snake snake = baseSnake();
		snake.getProperties().getCollision().setIntangibility(true);
		return snake;
	}
	
	public static Snake godSnake() {
		Snake snake = baseSnake();
		snake.getProperties().getCollision().setInvincibility(true);
		return snake;
	}
	
	public static Snake springSnake() {
		Snake snake = baseSnake();
		snake.getProperties().getCollision().setSpring(true);
		return snake;
	}
	
}
