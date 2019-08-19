package design.model.game;

/**
 * A set of properties that determinate and can change the direction of a snake.
 */
public interface DirectionProperty {

    /**
     * @return the current snake's direction
     */
    Direction getDirection();

    /**
     * Sets the snake direction, snake will memorize up to 2 subsequent valid inputs.
     * @param direction the new snake's direction to set
     * @return if direction changed
     */
    boolean setDirection(Direction direction);

    /**
     * @param reverse sets if the snake's controls are swapped (pressing up will be interpreted as down)
     */
    void setReverseDirection(boolean reverse);

    /**
     * @return if the snake's input controls are swapped (pressing up will be interpreted as down)
     */
    boolean isDirectionReversed();

    /**
     * @param direction the new direction to force that will be set ignoring checks
     * @return if the direction changed
     */
    boolean forceDirection(Direction direction);

    /**
    * Unlocks the possibility of setting direction by setDirection.
    */
    void allowChangeDirection();

    /**
     * @return if the snake has the second input memorized
     */
    boolean hasNextValidDirection();

    /**
     * @return the second input direction memorized into the snake
     */
    Direction getNextValidDirection();

}
