package design.controller.game;

import design.model.game.Direction;
import design.model.game.PlayerNumber;

/**
 * Describes an action for a snake to perform.
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface Action {
    /**
     * 
     * @return The PlayerNumber of the snake which should perform the action.
     */
    PlayerNumber getPlayerNumber();

    /**
     * 
     * @return The direction the snake should turn towards.
     */
    Direction getDirection();

}
