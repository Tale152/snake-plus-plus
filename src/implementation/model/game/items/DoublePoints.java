package implementation.model.game.items;

import java.util.Optional;
import design.model.game.Field;
import design.model.game.Snake;

/**
 * Instant effect: nothing.<p>
 * Lasting effect: doubles the value of every added score to that snake.
 * @author Alessandro Talmi
 */
public final class DoublePoints extends EffectAbstract {

    /**
     * How much points will be multiplied.
     */
    public static final double MULTIPLIER = 2;

    /**
     * @param dEffectDuration how long this effect's lasting effect will last,
     * empty if no effect duration
     */
    public DoublePoints(final Optional<Long> dEffectDuration) {
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
    protected void behaviorOnLastingEffectStart(final Snake snake) {
        snake.getPlayer().applyScoreMultiplier(snake.getPlayer().getScoreMultiplier() * MULTIPLIER);
    }

    @Override
    protected void behaviorOnLastingEffectEnd(Snake snake) {
        snake.getPlayer().applyScoreMultiplier(snake.getPlayer().getScoreMultiplier() / MULTIPLIER);
    }
    
}
