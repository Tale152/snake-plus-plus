package implementation.model.game.items;

import java.util.Optional;

import design.model.game.Effect;
import design.model.game.Field;
import design.model.game.Snake;

/**
 * Instant effect: increases snake's score by double of Apple's score increment.<p>
 * Lasting effect: increases snake's score by a single ScoreEarning SCORE_INCREMENT value
 * multiplied by number of ScoreEarning collided before lasting effect ends.
 */
public class ScoreEarning extends EffectAbstract {

    /**
     * How much a snake's score will be increased.
     */
    public static final int SCORE_INCREMENT = Apple.SCORE_INCREMENT * 2;

    /**
     * @param dEffectDuration how long this effect's lasting effect will last,
     * empty if no effect duration
     */
    public ScoreEarning(final Optional<Long> dEffectDuration) {
        super(dEffectDuration);
    }

    @Override
    public final void instantaneousEffect(final Snake target) {
        final Optional<Effect> active = target.getEffects().stream().filter(e -> {
            return e.getClass().equals(this.getClass()); })
                .findFirst();
        if (active.isPresent()) {
            target.getPlayer().addScore(SCORE_INCREMENT * (active.get().getComboCounter() + 1));
        } else {
            target.getPlayer().addScore(SCORE_INCREMENT);
        }
    }

    @Override
    public final void expirationEffect(final Field field) {
        // does nothing
    }

    @Override
    protected void behaviorOnLastingEffectStart(final Snake snake) {
        // does nothing
    }

    @Override
    protected void behaviorOnLastingEffectEnd(final Snake snake) {
        // does nothing
    }

}
