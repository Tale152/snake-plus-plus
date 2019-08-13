package implementation.model.game.items;

import java.util.Optional;
import design.model.game.Field;
import design.model.game.Snake;

/**
 * Instantaneous effect: halve snake's length and score.<p>
 * Lasting effect: multiplies snake's length by every bad apple eaten (+1)
 * since lasting effect activation. Does not restore score.
 * @author Alessandro Talmi
 *
 */
public final class BadApple extends EffectAbstract {

    private final int SHORTEN_DENOMINATOR = 2;

    /**
     * @param dEffectDuration how long this effect's lasting effect will last,
     * empty if no effect duration
     */
    public BadApple(final Optional<Long> dEffectDuration) {
        super(dEffectDuration);
    }

    @Override
    public void instantaneousEffect(final Snake target) {
        target.getProperties().getLengthProperty().shorten(target.getProperties().getLengthProperty().getLength() / SHORTEN_DENOMINATOR);
        target.getPlayer().reduceScore(target.getPlayer().getScore() / SHORTEN_DENOMINATOR);
    }

    @Override
    public void expirationEffect(final Field field) {
        //does nothing
    }

    @Override
    protected void behaviorOnLastingEffectStart(final Snake snake) {
        //does nothing
    }

    @Override
    protected void behaviorOnLastingEffectEnd(final Snake snake) {
        int length = snake.getProperties().getLengthProperty().getLength();
        snake.getProperties().getLengthProperty().lengthen((length * (getComboCounter() + 1)) - length);
    }

}
