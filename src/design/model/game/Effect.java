package design.model.game;

import java.util.Optional;

/**
 * An effect represent a property an {@link Item} has.<br>
 * It also can be attached to a {@link Snake} to change it's properties.
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 *
 */
public interface Effect {
	
	/**
	 * Starts the effect on the target.<br>
	 * If the effect has an empty end time this method will apply an instantaneous effect.<br>
	 * If, instead, the effect has an end time this method will apply a lasting effect unless 
	 * the target already has active another instance of the same effect;
	 * in that case effectStart will simply increase that instance effect duration via {@link #incrementDuration(long)}.
	 * @param target snake at whom effect will be applied
	 * @param collisionTime when the collision that starts this effect occurred  
	 * @see Snake
	 */
	public void effectStart(Snake target, long collisionTime);
	
	/**
	 * What does the effect do on the target snake when effect time ends
	 * @param target snake whom receives effect end consequences
	 * @see Snake
	 */
	public void effectEnd(Snake target);
	
	/**
	 * What does the effect do when the expiration time comes
	 * @param field it may have effect on the field
	 * @see Field
	 */
	public void expirationEffect(Field field);
	
	/**
	 * @return {@link Optional} of long representing when the effect ends (empty if its instantaneous)
	 */
	public Optional<Long> getEffectEndTime();
	
	/**
	 * This is also how an {@link Item} knows if and when expire.
	 * @return {@link Optional} of long representing when the effect expires (empty if never expires)
	 * @see Item
	 */
	public Optional<Long> getExpirationTime();
	
	/**
	 * Increments effect end time (unless effect end time was empty)
	 * @param time how much effect end time has to increase
	 * @return if effect end time was incremented or not
	 * @see #getEffectEndTime()
	 * @see Optional
	 */
	public boolean incrementDuration(long time);

}
