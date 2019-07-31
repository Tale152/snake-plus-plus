package design.model.game;

/**
 * From this class you can have access to all snake different properties.
 * @author elisa
 *
 */
public interface Properties {
	
	/**
	 * 
	 * @return length properties.
	 */
	public LengthProperty getLengthProperty();
	
	/**
	 * 
	 * @return direction properties.
	 */
	public DirectionProperty getDirectionProperty();
	
	/**
	 * 
	 * @return pick up properties.
	 */
	public PickupProperty getPickupProperty();
	
	/**
	 * 
	 * @return collision properties.
	 */
	public CollisionProperty getCollisionProperty();
	
	/**
	 * 
	 * @return speed properties.
	 */
	public SpeedProperty getSpeedProperty();
	
}
