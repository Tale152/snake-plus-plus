package implementation.model.game.items;

import java.util.Optional;
import design.model.game.Field;
import design.model.game.Snake;

/**
 * Instant effect: nothing. <p>
 * Lasting effect: halves snake's speed.
 * @author Alessandro Talmi
 */
public class Slug extends EffectAbstract {

    private double delta;

    /**
     * @param dEffectDuration how long this effect's lasting effect will last,
     * empty if no effect duration
     */
    public Slug(final Optional<Long> dEffectDuration) {
        super(dEffectDuration);
        delta = 0;
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
        delta = (snake.getProperties().getSpeedProperty().getSpeedMultiplier() * Turbo.SPEED_MULTIPLICATOR) - snake.getProperties().getSpeedProperty().getSpeedMultiplier();
        snake.getProperties().getSpeedProperty().applySpeedMultiplier(delta);
    }

    @Override
    protected final void behaviorOnLastingEffectEnd(final Snake snake) {
        snake.getProperties().getSpeedProperty().applySpeedMultiplier(-delta);
    }

}
