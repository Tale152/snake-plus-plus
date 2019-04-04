package implementation.model.game;

import java.awt.Point;
import java.util.List;
import design.model.game.*;
import design.model.game.InitialGameState.InitialPlayerState;
import implementation.model.game.initializers.Utils;
import implementation.model.game.snake.SnakeImpl;

public class InitSnakes {

	private InitSnakes() {}
	
	public static void initSnakes(GameRules gameRules, InitialGameState initialGameState, List<Snake> snakes, long gameTime) {
		int nPlayer = 0;
		long delta = gameRules.getInitialSnakeDelta();
		double multiplier = gameRules.getInitialSnakeMultiplier();
		for (InitialPlayerState player : initialGameState.getInitialPlayerState()) {
			snakes.add(createSnake(gameTime, delta, multiplier, nPlayer++, player)); 
		}
	}
	
	private static Snake createSnake(long gameTime, long delta, double multiplier, int nPlayer, InitialPlayerState player) {
		PlayerNumber number = determinePlayerNumber(nPlayer);
		String name = player.getName();
		Direction direction = player.getDirection();
		List<Point> bodyPoints = player.getBodyPoints();
		throw new IllegalStateException("bodyPoints misses from snake's constructor"); //TODO remove
		//return new SnakeImpl(number, name, direction, delta, multiplier, gameTime); //TODO bodyPoints misses from snake's constructor

	}
	
	private static PlayerNumber determinePlayerNumber(int i) {
		Utils.throwIllegalState(i >= PlayerNumber.values().length, "Something went wrong while getting player number");
		return PlayerNumber.values()[i];
	}
	
}
