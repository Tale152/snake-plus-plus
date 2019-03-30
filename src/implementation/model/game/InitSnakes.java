package implementation.model.game;

import java.util.List;
import design.model.game.*;
import design.model.game.InitialGameState.InitialPlayerState;
import implementation.model.game.initializers.Utils;
import implementation.model.game.snake.SnakeImpl;

public class InitSnakes {

	private InitSnakes() {}
	
	public static void initSnakes(GameRules gameRules, InitialGameState initialGameState, List<Snake> snakes, long gameTime) {
		long delta = gameRules.getInitialSnakeDelta();
		int playerEnum = 0;
		for (InitialPlayerState player : initialGameState.getInitialPlayerState()) {
			PlayerNumber number = determinePlayerNumber(playerEnum++);
			String name = player.getName();
			Direction direction = player.getDirection();
			snakes.add(new SnakeImpl(number, name, direction, delta, 1, gameTime)); //TODO multiplier not always 1, cannot set initial coord
			throw new IllegalStateException("not finished implementing");
		}
	}
	
	private static PlayerNumber determinePlayerNumber(int i) {
		Utils.throwIllegalState(i >= PlayerNumber.values().length, "Something went wrong while getting player number");
		return PlayerNumber.values()[i];
	}
	
}
