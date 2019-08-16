package implementation.model.game;

import design.model.game.Field;
import design.model.game.GameModel;
import design.model.game.GameRules;

/**
 * @see GameModel
 * @author Alessandro Talmi
 */
public class GameModelImpl implements GameModel {

    private final Field field;
    private final GameRules gameRules;

    /**
     * @param field were the game will play
     * @param gameRules to apply to the game
     */
    public GameModelImpl(final Field field, final GameRules gameRules) {
        this.field = field;
        this.gameRules = gameRules;
    }

    @Override
    public final Field getField() {
        return field;
    }

    @Override
    public final GameRules getGameRules() {
        return gameRules;
    }

}
