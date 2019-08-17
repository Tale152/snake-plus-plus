package implementation.controller.game;

import design.controller.game.Action;
import design.model.game.Direction;
import design.model.game.PlayerNumber;

public class ActionImpl implements Action {

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

    public ActionImpl(final PlayerNumber n, final Direction dir) {
        this.player = n;
        this.direction = dir;
    }
}
