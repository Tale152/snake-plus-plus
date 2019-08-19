package implementation.model.game.items;

import java.util.Optional;

import design.model.game.Field;
import design.model.game.Snake;

/**
 * Instant effect: nothing. <p>
 * Lasting effect: doubles snake's speed.
 */
public class Turbo extends EffectAbstract {

    /**
     * How much speed will be multiplied.
     */
    public static final double SPEED_MULTIPLICATOR = 2;
    private double delta;

    /**
     * @param dEffectDuration how long this effect's lasting effect will last,
     * empty if no effect duration
     */
    public Turbo(final Optional<Long> dEffectDuration) {
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
        delta = snake.getProperties().getSpeedProperty().getSpeedMultiplier() / SPEED_MULTIPLICATOR;
        snake.getProperties().getSpeedProperty().applySpeedMultiplier(-delta);
    }

    @Override
    protected final void behaviorOnLastingEffectEnd(final Snake snake) {
        snake.getProperties().getSpeedProperty().applySpeedMultiplier(delta);
    }

}
