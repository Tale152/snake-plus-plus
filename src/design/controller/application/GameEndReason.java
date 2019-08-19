package design.controller.application;

/**
 * Every possible reason that a game can end.
 */
public enum GameEndReason {
    /**
     * There was no game.
     */
    START,
    /**
     * One of the snakes reached the desired length.
     */
    WON_LENGTH,
    /**
     * The game time reached the desired value.
     */
    WON_TIME,
    /**
     * One of the snakes reached the desired score.
     */
    WON_SCORE,
    /**
     * Every snake into the field is dead.
     */
    LOST_DEATH,
    /**
     * The game time reached a certain value.
     */
    LOST_TIME,
    /**
     * Any kind of error occurred.
     */
    ERROR;
}
