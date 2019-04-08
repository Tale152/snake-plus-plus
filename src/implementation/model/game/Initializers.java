package implementation.model.game;

import java.util.ArrayList;
import java.util.List;
import design.model.game.*;
import design.model.game.InitialGameState.InitialPlayerState;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.initializers.Utils;
import implementation.model.game.snake.SnakeImpl;

public class Initializers {

	private Initializers() {}
	
	public static Field initField(InitialGameState initialGameState, List<Snake> snakes) {
		Field field = new FieldImpl(initialGameState.getFieldSize());
		for (Snake snake : snakes) {
			for (Item bodyPart : snake.getBodyParts()) {
				field.addItem(bodyPart);
			}
		}
		for (Item item : initialGameState.getFieldItems()) {
			field.addItem(item);
		}
		return field;
	}
	
	public static List<Snake> initSnakes(GameRules gameRules, InitialGameState initialGameState, long gameTime) {
		List<Snake> snakes = new ArrayList<>();
		int nPlayer = 0;
		long delta = gameRules.getInitialSnakeDelta();
		double multiplier = gameRules.getInitialSnakeMultiplier();
		for (InitialPlayerState player : initialGameState.getInitialPlayerState()) {
			snakes.add(createSnake(gameTime, delta, multiplier, nPlayer++, player)); 
		}
		return snakes;
	}
	
	private static Snake createSnake(long gameTime, long delta, double multiplier, int nPlayer, InitialPlayerState player) {
		return new SnakeImpl(player.getBodyPoints(), determinePlayerNumber(nPlayer), player.getName(), player.getDirection(), delta, multiplier, gameTime);
	}
	
	private static PlayerNumber determinePlayerNumber(int i) {
		Utils.throwIllegalState(i >= PlayerNumber.values().length, "Something went wrong while getting player number");
		return PlayerNumber.values()[i];
	}
	
}