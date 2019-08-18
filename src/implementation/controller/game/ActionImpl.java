package implementation.controller.game;

import design.controller.game.Action;
import design.model.game.Direction;
import design.model.game.PlayerNumber;

/**
 * @see Action
 * @author Nicola Orlando
 */
public final class ActionImpl implements Action {

    private final PlayerNumber player;
    private final Direction direction;

    @Override
    public PlayerNumber getPlayerNumber() {
        return this.player;
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Creates an action representing a snake and the direction it should turn towards.
     * @param n The player number.
     * @param dir The direction their snake should turn towards.
     */
    public ActionImpl(final PlayerNumber n, final Direction dir) {
        this.player = n;
        this.direction = dir;
    }
}
