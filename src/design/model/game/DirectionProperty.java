package design.model.game;

/**
 * Provides properties about snake direction
 * @author elisa
 *
 */
public interface DirectionProperty {
	
	/**
	 * Method that return the current snake direction
	 * @return current snake direction
	 */
	public Direction getDirection();
	
	/**
	 * Method that make you decide the new snake direction. 
	 * It can not be set a direction that is opposite to the current one. 
	 * @param direction The direction to set.
	 * @return True if the operation can be set, false otherwise. 
	 */
	public boolean setDirection(Direction direction);
	
	/**
	 * Method that set the reverse direction property.
	 * @param reverse If it is true, the input direction are reversed.
	 * If false, the input direction will be not modified. 
	 */
	public void setReverseDirection(boolean reverse);
	
	/**
	 * Get reverse property status.
	 * @return True if reverse is active, false otherwise. 
	 */
	public boolean getReverseDirection();
	
	/**
	 * Method used to force to change direction, even though it is the opposite one
	 * @param direction The direction to force.
	 * @return True when the operation is completed.
	 */
	public boolean forceDirection(Direction direction);
	
	/**
	 * This method change a flag, that allow to change direction. 
	 * When the new direction is changing, you can not set a new direction.
	 * Once the direction has been changed, you can set a new direction. 
	 */
	public void allowChangeDirection();
}
