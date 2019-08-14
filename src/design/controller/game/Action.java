package design.controller.game;

import design.model.game.Direction;
import design.model.game.PlayerNumber;

public interface Action {

    public PlayerNumber getPlayerNumber();

    public Direction getDirection();

}
