package implementation.model.game.items;

import java.util.Optional;
import design.model.game.Field;
import design.model.game.Snake;

/**
 * An Apple it's one of the basic effect into Snake. <p>
 * Instantaneous effect: increments size and score to a snake. <p>
 * Lasting effect: when lasting effect ends returns Snake's size to 
 * one and set's it's score to zero.
 * @author Alessandro Talmi
 *
 */
public final class Apple extends EffectAbstract {

    /**
     * how much a snake size will be increased interacting with an Apple.
     */
    private final int LENGTH_INCREMENT = 1;
    private final int SCORE_INCREMENT = 10;

    /**
     * @param dEffectDuration how long this effect's lasting effect will last,
     * empty if no effect duration
     */
    public Apple(final Optional<Long> dEffectDuration) {
        super(dEffectDuration);
    }

    @Override
    public void instantaneousEffect(final Snake target) {
        target.getProperties().getLengthProperty().lengthen(LENGTH_INCREMENT);
        target.getPlayer().addScore(SCORE_INCREMENT);
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
        snake.getProperties().getLengthProperty().shorten(snake.getProperties().getLengthProperty().getLength() - 1);
        snake.getPlayer().reduceScore(snake.getPlayer().getScore());
    }
}
