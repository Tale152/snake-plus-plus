package implementation.model.game.items;

import java.util.Optional;
import java.util.Random;

import design.model.game.Direction;
import design.model.game.Field;
import design.model.game.Snake;

/**
 * Instant effect: changes snake's direction randomly into one of two possible 
 * (example: snake going RIGHT, collides with beer, now goes UP or DOWN).<p>
 * Lasting effect: snake's controls are swapped 
 * (example: pressing RIGHT equals to pressing LEFT and the other way around).
 * @see Effect
 * @see Item
 */
public final class Beer extends EffectAbstract {

    /**
     * @param dEffectDuration how long this effect's lasting effect will last,
     * empty if no effect duration
     */
    public Beer(final Optional<Long> dEffectDuration) {
        super(dEffectDuration);
    }

    @Override
    public void instantaneousEffect(final Snake target) {
        final Direction direction = target.getProperties().getDirectionProperty().getDirection();
        final Random rnd = new Random();
        //creating a random boolean variable to chose between two possible directions
        final boolean directionGuess = rnd.nextBoolean();
        if (direction == Direction.UP || direction == Direction.DOWN) {
            if (directionGuess) {
                target.getProperties().getDirectionProperty().forceDirection(Direction.LEFT);
            } else {
                target.getProperties().getDirectionProperty().forceDirection(Direction.RIGHT);
            }
        } else {
            if (directionGuess) {
                target.getProperties().getDirectionProperty().forceDirection(Direction.UP);
            } else {
                target.getProperties().getDirectionProperty().forceDirection(Direction.DOWN);
            }
        }
    }

    @Override
    public void expirationEffect(final Field field) {
        //does nothing
    }

    @Override
    protected void behaviorOnLastingEffectStart(final Snake snake) {
        snake.getProperties().getDirectionProperty().setReverseDirection(true);
    }

    @Override
    protected void behaviorOnLastingEffectEnd(final Snake snake) {
        snake.getProperties().getDirectionProperty().setReverseDirection(false);
    }

}
