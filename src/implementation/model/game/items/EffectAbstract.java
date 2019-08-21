package implementation.model.game.items;

import java.util.Optional;
import design.model.game.Effect;
import design.model.game.Snake;

/**
 * Abstract class that specifies common behaviors between all effects implementation.
 * @see Effect
 */
public abstract class EffectAbstract implements Effect {

    private Optional<Long> dEffectDuration;
    private Optional<Snake> attachedSnake;
    private int counter;

    /**
     * @param dEffectDuration how long this effect's lasting effect will last,
     * empty if no effect duration
     */
    public EffectAbstract(final Optional<Long> dEffectDuration) {
        this.dEffectDuration = dEffectDuration;
        attachedSnake = Optional.empty();
        counter = 1;
    }

    @Override
    public final void attachSnake(final Snake snake) {
        attachedSnake = Optional.of(snake);
    }

    @Override
    public final Optional<Snake> getAttachedSnake() {
        return attachedSnake;
    }

    @Override
    public final Optional<Long> getEffectDuration() {
        return dEffectDuration;
    }

    @Override
    public final void run() {
    if (!attachedSnake.isPresent()) {
        //that means that lasting effect was activated without a snake to reference, and that's impossible
        throw new IllegalStateException();
    }
    //what actually happens on lasting effect activation, depends on implementation
    behaviorOnLastingEffectStart(attachedSnake.get());
    final long activationTime = System.currentTimeMillis();
    long timeToWait = this.getEffectDuration().get();
    //sleeping until effect ends
    while (true) {
        try {
            Thread.sleep(timeToWait);
            final long enlapsedTime = System.currentTimeMillis() - activationTime;
            //the effect can be made longer if another effect of same instance is eaten
            if (enlapsedTime >= this.getEffectDuration().get()) {
                break;
            } else {
                timeToWait = this.getEffectDuration().get() - enlapsedTime;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    attachedSnake.get().removeEffect(this);
    //what actually happens on lasting effect end, depends on implementation
    behaviorOnLastingEffectEnd(attachedSnake.get());
    }

    @Override
    public final void incrementDuration(final long duration) {
        //other than incrementing effect duration it increments counter of effects
        //of the same kind eaten before effect end
        ++counter;
        this.dEffectDuration = Optional.of(dEffectDuration.get() + duration);
    }

    @Override
    public final int getComboCounter() {
        return counter;
    }

    /**
     * Specifies what the particular behavior of this lasting effect start,
     *  not common to every effect.
     * @param snake that will be affected by this lasting effect
     */
    protected abstract void behaviorOnLastingEffectStart(Snake snake);

    /**
     * Specifies what the particular behavior of this lasting effect end,
     *  not common to every effect.
     * @param snake that was affected by this lasting effect
     */
    protected abstract void behaviorOnLastingEffectEnd(Snake snake);

}
