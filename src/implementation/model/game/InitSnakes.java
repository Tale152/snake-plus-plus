package implementation.model.game;

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
		return new SnakeImpl(player.getBodyPoints(), determinePlayerNumber(nPlayer), player.getName(), player.getDirection(), delta, multiplier, gameTime);
	}
	
	private static PlayerNumber determinePlayerNumber(int i) {
		Utils.throwIllegalState(i >= PlayerNumber.values().length, "Something went wrong while getting player number");
		return PlayerNumber.values()[i];
	}
	
}
