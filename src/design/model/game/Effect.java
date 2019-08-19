package design.model.game;

import java.util.Optional;

/**
* An Effect is contained into an Item and can be passed to a Snake if has a duration.<p>
* When contained into an Item specifies it's behavior on collision.<p>
* When contained into a Snake changes it's properties depending on Effect's implementation.
 */
public interface Effect extends Runnable {

    /**
     * Specifies what happens to a snake that has collided with the 
     * Item containing this effect.
     * @param target snake affected by instantaneous effect
     */
    void instantaneousEffect(Snake target);

    /**
     * Specifies what happens when the Item containing this Effect expires. 
     * @param field that will be affected by expiration effect
     */
    void expirationEffect(Field field);

    /**
     * Saves which snakes is under this Effect's lasting effect.
     * @param snake that this effect will be attached to
     */
    void attachSnake(Snake snake);

    /**
     * Returns the snakes that now is under this Effect's lasting effect.
     * @return Snake if present, empty otherwise
     */
    Optional<Snake> getAttachedSnake();

    /**
     * Return the value (in milliseconds) representing how long this effect will 
     * affect a Snake when lasting effect is activated.
     * @return milliseconds that lasting effect will last, 
     * empty if no lasting effect will be activated
     */
    Optional<Long> getEffectDuration();

    /**
     * Extends effect duration by specified parameter.
     * @param duration how long (milliseconds) this effect will be extended
     */
    void incrementDuration(long duration);

    /**
     * Return an integer representing how many times a lasting effect of the same class has been
     * activated before having time to expire.
     * @return number of times the same effect has been activated withot reaching 
     * lasting effect's end
     */
    int getComboCounter();

}
