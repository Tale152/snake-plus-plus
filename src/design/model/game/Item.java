package design.model.game;

import java.awt.Point;
import java.util.Optional;

/**
 * An Item represents something that has a {@link Point}, might have a duration before it expires and when collides
 * with a snake applies some effect to that given snake.<br>
 * Various extension of Item represent different items, each one with it's own particular {@link Effect} on collision and/or on expire. 
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface Item {
	
	/**
	 * @return {@link Point} where this Item is located
	 */
	public Point getPoint();
	
	/**
	 * @param snake that collides with this {@link Item}
	 * @param time when the collision occurs
	 * @see Snake
	 */
	public void onCollision(Snake snake, long time);
	
	/**
	 * If no collision occurs an Item might expire.
	 * @return an {@link Optional} of long representing when the item needs to be removed (empty if does not need to be removed) 
	 */
	public Optional<Long> getDuration();
	
}
