package design.model.game;

import java.util.List;

/**
 * This contains the entire set of rules that will govern a level.
 */
public interface GameRules {

    /**
     * @return the conditions needed to win a level
     */
    WinConditions getWinConditions();

    /**
     * @return the conditions needed to loose a level
     */
     LossConditions getLossConditions();

    /**
     * @return the entire list of item rules, that defines how every item will behave
     */
    List<ItemRule> getItemRules();

    /**
     * @return the initial delta time (milliseconds) between snake movements
     */
    long getInitialSnakeDelta();

    /**
     * @return the initial score multiplier snake
     */
    double getInitialSnakeMultiplier();

    /**
     * @return the time at the start of the game (milliseconds)
     */
    long getInitialTime();

    /**
     * @return whether or not time goes forward or backward
     */
    boolean isTimeGoingForward();

}
