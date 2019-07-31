package design.model.game;

/**
 * Provides snake'd pick up properties.
 * @author elisa
 *
 */
public interface PickupProperty {
	
	/**
	 * Set the pick up radius, the number of cell around snake's head he will collide with. 
	 * If the radius is 1, snake will collide only with items in front of is head. 
	 * @param radius The radius to set.
	 */
	public void setPickupRadius(int radius);
	
	/**
	 * Method used to get the current pick up radius. 
	 * @return The current pick up radius.
	 */
	public int getPickupRadius();
	
}
