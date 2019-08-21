package implementation.model.game.items;

import java.util.Optional;
import design.model.game.Field;
import design.model.game.Snake;

/**
 * Instant effect: nothing.<p>
 * Lasting effect: increases snake's pickup radius (example:
 * if normally a snake would have passed by side of an item, 
 * now it will collide with it even tho that item is not in
 * the next cell where the snake will be)
 * @see Effect
 * @see Item
 * 
 */
public class Magnet extends EffectAbstract {

    /**
     * How much snake's radious will be multiplied.
     */
    public static final int MAGNET_RADIOUS_MULTIPLIER = 2;

    /**
     * @param dEffectDuration how long this effect's lasting effect will last,
     * empty if no effect duration
     */
    public Magnet(final Optional<Long> dEffectDuration) {
        super(dEffectDuration);
    }

    @Override
    public void instantaneousEffect(final Snake target) {
        //does nothing
    }

    @Override
    public void expirationEffect(final Field field) {
        //does nothing
    }

    @Override
    protected final void behaviorOnLastingEffectStart(final Snake snake) {
        snake.getProperties().getPickupProperty().setPickupRadius(snake.getProperties().getPickupProperty().getPickupRadius() * MAGNET_RADIOUS_MULTIPLIER);
    }

    @Override
    protected final void behaviorOnLastingEffectEnd(final Snake snake) {
        snake.getProperties().getPickupProperty().setPickupRadius(snake.getProperties().getPickupProperty().getPickupRadius() / MAGNET_RADIOUS_MULTIPLIER);
    }

}
