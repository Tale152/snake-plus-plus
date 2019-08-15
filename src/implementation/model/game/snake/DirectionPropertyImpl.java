package implementation.model.game.snake;

import java.util.Optional;

import design.model.game.Direction;
import design.model.game.DirectionProperty;
/**
 * This class is part of the snake properties, here there are the method to use
 * to set all the direction properties, to force a direction an to get the
 * current snake direction.
 * @author Elisa Tronetti
 */
public class DirectionPropertyImpl implements DirectionProperty {

    private Direction direction;
    private Optional<Direction> nextInputedDirection;
    private boolean reversed;
    private boolean canChangeDirection;

    /**
     * Set all the direction properties parameter.
     * @param direction the direction to set to snake at first
     */
    public DirectionPropertyImpl(final Direction direction) {
        checkDirection(direction);
        this.direction = direction;
        this.reversed = false;
        this.canChangeDirection = true;
        nextInputedDirection = Optional.empty();
    }

    @Override
    public final Direction getDirection() {
        return this.direction;
    }

    @Override
    public final boolean forceDirection(final Direction direction) {
        this.direction = direction;
        return true;
    }

    @Override
    public final boolean setDirection(final Direction direction) {
        checkDirection(direction);
        if (this.canChangeDirection) {
            this.canChangeDirection = false;
            this.direction = directionCase(direction);
        } else if (!this.nextInputedDirection.isPresent()) {
            Direction nextPossibleDirection = directionCase(direction);
            if (nextPossibleDirection != this.direction) {
                this.nextInputedDirection = Optional.of(nextPossibleDirection);
            }
        }
        return this.canChangeDirection;
    }

    private Direction directionCase(final Direction inputDirection) {
        if (!this.direction.equals(reversedDirection(inputDirection)) && !this.reversed) { 
            return inputDirection;
        } else if (!this.direction.equals(inputDirection) && this.reversed) {
            return reversedDirection(inputDirection);
        }
        return this.direction;
    }

    /**
     * A new direction can be set only if this method is called and 
     * you are allowed to change direction.
     */
    public void allowChangeDirection() {
        this.canChangeDirection = true;
    }

    @Override
    public final void setReverseDirection(final boolean reverse) {
        this.reversed = reverse;
    }

    @Override
    public final boolean getReverseDirection() {
        return this.reversed;
    }

    private void checkDirection(final Direction direction) {
        if (direction == null) {
            throw new IllegalArgumentException();
        }
    }

    private Direction reversedDirection(final Direction direction) {
        switch (direction) {
        case UP: return Direction.DOWN;
        case DOWN: return Direction.UP;
        case LEFT: return Direction.RIGHT;
        case RIGHT: return Direction.LEFT;
        default: throw new IllegalStateException();
        }
    }

    @Override
    public final boolean hasNextValidDirection() {
        return nextInputedDirection.isPresent();
    }

    @Override
    public final Direction getNextValidDirection() {
        if (nextInputedDirection.isPresent()) {
            Direction result = nextInputedDirection.get();
            nextInputedDirection = Optional.empty();
            return result;
        }
        return direction;
    }

    /**
     * @return a string that contains the current snake direction and if snake
     * reverse status is active or not.
     */
    public String toString() {
        return "Current direction: " + this.direction.name() + "\n" 
                + "Reversed status: " + this.reversed + "\n";
    }
}
