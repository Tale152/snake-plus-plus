package implementation.model.game.items;

import java.util.Optional;

import design.model.game.Effect;
import design.model.game.Field;
import design.model.game.Snake;

/**
 * Instant effect: decreases snake's score by double of Apple's score increment.<p>
 * Lasting effect: decreases snake's score by a single ScoreEarning.SCORE_INCREMENT value
 * multiplied by number of ScoreEarning collided before lasting effect ends.
 */
public class ScoreLoss extends EffectAbstract {

    /**
    * @param dEffectDuration how long this effect's lasting effect will last,
    * empty if no effect duration
    */
    public ScoreLoss(final Optional<Long> dEffectDuration) {
        super(dEffectDuration);
    }

    @Override
    public final void instantaneousEffect(final Snake target) {
        //the lasting effect of this item is a little bit trickier
        //eating this item while this lasting effect is active will result in a multiplication
        //of the points you loose every time you eat one instance of this item
        //but this needs to be done instantly and not on lasting effect
        //so I check if snake has already an instance of this effect and if so 
        //I calculate how much point to remove based on the counter
        final Optional<Effect> active = target.getEffects()
                                    .stream()
                                    .filter(e -> {
                                        return e.getClass().equals(this.getClass()); })
                                    .findFirst();
        if (active.isPresent()) {
            target.getPlayer().reduceScore(ScoreEarning.SCORE_INCREMENT * (active.get().getComboCounter() + 1));
        } else {
            target.getPlayer().reduceScore(ScoreEarning.SCORE_INCREMENT);
        }
    }

    @Override
    public void expirationEffect(final Field field) {
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
