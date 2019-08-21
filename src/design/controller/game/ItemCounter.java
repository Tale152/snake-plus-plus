package design.controller.game;

import java.util.NoSuchElementException;
import design.model.game.Effect;

/**
 * Used to track Items on field and decide if it's time to spawn them or not.
 */
public interface ItemCounter {

    /**
     * @param effect the effect contained into the desired item, identifying it
     * @return if item counter was incremented or not (does not increment if max reached)
     * @throws NoSuchElementException no effect class found
     */
    boolean increase(Class<? extends Effect> effect) throws NoSuchElementException;

    /**
     * @param effect the effect contained into the desired item, identifying it
     * @return if item counter was decremented or not (does not decrement if zero)
     * @throws NoSuchElementException no effect class found
     */
    boolean decrease(Class<? extends Effect> effect) throws NoSuchElementException;

    /**
     * @param effect the effect contained into the desired item, identifying it
     * @return the current number of selected Item
     * @throws NoSuchElementException no effect class found
     */
    int getCurrent(Class<? extends Effect> effect) throws NoSuchElementException;

    /**
     * @param effect the effect contained into the desired item, identifying it
     * @return the maximum number of selected Item that can be on field.
     * @throws NoSuchElementException no effect class found
     */
    int getMax(Class<? extends Effect> effect) throws NoSuchElementException;

    /**
     * @param effect the effect contained into the desired item, identifying it
     * @return if maximum number is reached
     * @throws NoSuchElementException no effect class found
     */
    boolean isMax(Class<? extends Effect> effect) throws NoSuchElementException;

    /**
     * @param effect the effect contained into the desired item, identifying it
     * @return last time (milliseconds) that this item has been tried to spawn
     */
    long getLastSpawnAttempt(Class<? extends Effect> effect);

    /**
     * @param effect the effect contained into the desired item, identifying it
     * @param time new last time (milliseconds) that this item has been tried to spawn
     */
    void setLastSpawnAttempt(Class<? extends Effect> effect, long time);

}
