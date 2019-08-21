package implementation.model.game.items;

import java.util.Optional;
import design.model.game.Field;
import design.model.game.Snake;

/**
 * Instant effect: nothing.<p>
 * Lasting effect: makes snake intangible.
 * @see Effect
 * @see Item
 */
public class GhostMode extends EffectAbstract {

    /**
     * @param dEffectDuration how long this effect's lasting effect will last,
     * empty if no effect duration
     */
    public GhostMode(final Optional<Long> dEffectDuration) {
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
        snake.getProperties().getCollisionProperty().setIntangibility(true);
    }

    @Override
    protected final void behaviorOnLastingEffectEnd(final Snake snake) {
        snake.getProperties().getCollisionProperty().setIntangibility(false);
    }

}
