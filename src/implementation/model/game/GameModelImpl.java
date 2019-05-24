package implementation.model.game;

import design.model.game.Field;
import design.model.game.GameModel;
import design.model.game.GameRules;

public class GameModelImpl implements GameModel {

	private final Field field;
	private final GameRules gameRules;
	
	public GameModelImpl(Field field, GameRules gameRules) {
		this.field = field;
		this.gameRules = gameRules;
	}
	
	@Override
	public Field getField() {
		return field;
	}

	@Override
	public GameRules getGameRules() {
		return gameRules;
	}

}
