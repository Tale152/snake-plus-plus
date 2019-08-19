package implementation.model.game.items;

import java.util.Optional;

import design.model.game.Field;
import design.model.game.Snake;

/**
 * Instant effect: reverses snake. <p>
 * Lasting effect: reverses snake every time it collides with a wall.
 */
public class Spring extends EffectAbstract {

    /**
     * @param dEffectDuration how long this effect's lasting effect will last,
     * empty if no effect duration
     */
    public Spring(final Optional<Long> dEffectDuration) {
        super(dEffectDuration);
    }

    @Override
    public final void instantaneousEffect(final Snake target) {
        target.reverse();
    }

    @Override
    public void expirationEffect(final Field field) {
        //does nothing
    }

    @Override
    protected final void behaviorOnLastingEffectStart(final Snake snake) {
        snake.getProperties().getCollisionProperty().setSpring(true);
    }

    @Override
    protected final void behaviorOnLastingEffectEnd(final Snake snake) {
        snake.getProperties().getCollisionProperty().setSpring(false);
    }

}
