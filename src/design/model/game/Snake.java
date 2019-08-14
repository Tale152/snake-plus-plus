package design.model.game;

import java.util.List;

/**
 * Snake contains all the method that can be used to communicate with him, to kill him 
 * and to get all his properties.
 * @author Elisa Tronetti
 * @author Alessandro Talmi
 * @author Nicola Orlando
 *
 */
public interface Snake extends Runnable {

/***
 * @return the player that owns this snake
 */
    Player getPlayer();

/**
 * Method used to get all snake properties, such as length properties, direction properties...
 * @return all snake properties
 */
    Properties getProperties();

/**
 * This method is used to add an effect to snake active effect list.
 * If an effect is already present, the duration of that effect is increased
 * If it is not present, the effect is added, the effect is now active, and
 * the effect thread is started
 * @param effect is the effect to add to the list
 */
    void addEffect(Effect effect);

/**
 * @param effect is the effect to remove to the list of snake's active effects
 * @return true if it has been removed successfully, false otherwise
 */
    boolean removeEffect(Effect effect);

/**
 * @return all the affect associated with snake
 */
    List<Effect> getEffects();

/**
 * @return true if snake is alive, false otherwise
 */
    boolean isAlive();

/**
 * This method sets snake as dead, he is not alive anymore.
 */
    void kill();

/**
 * Used to reverse snake, when he eat some items.
 * He reverse his head and direction only after a movement
 */
    void reverse();

/**
 * @return all snake's body part
 */
    List<BodyPart> getBodyParts();

/**
 * @return return true if snake has moved, false otherwise
 */
    boolean hasMoved();

}
