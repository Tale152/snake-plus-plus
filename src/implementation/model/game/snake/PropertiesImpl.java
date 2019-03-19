package implementation.model.game.snake;

import design.model.game.CollisionProperty;
import design.model.game.Direction;
import design.model.game.DirectionProperty;
import design.model.game.LengthProperty;
import design.model.game.PickupProperty;
import design.model.game.Properties;
import design.model.game.SpeedProperty;

public class PropertiesImpl implements Properties{

	private final LengthProperty length;
	private final DirectionProperty direction;
	private final PickupProperty pickup;
	private final CollisionProperty collision;
	private final SpeedProperty speed;
	
	public PropertiesImpl(int startLength, Direction direction, boolean reversed, int radius, boolean invincible,
			boolean intangible, boolean spring, long deltaT, double multiplier, long lastUpdate) {
		this.length = new LengthPropertyImpl(startLength);
		this.direction = new DirectionPropertyImpl(direction, reversed);
		this.pickup = new PickupPropertyImpl(radius);
		this.collision = new CollisionPropertyImpl(invincible, intangible, spring);
		this.speed = new SpeedPropertyImpl(deltaT, multiplier, lastUpdate);
	}
	
	@Override
	public LengthProperty getLength() {
		return this.length;
	}

	@Override
	public DirectionProperty getDirection() {
		return this.direction;
	}

	@Override
	public PickupProperty getPickup() {
		return this.pickup;
	}

	@Override
	public CollisionProperty getCollision() {
		return this.collision;
	}

	@Override
	public SpeedProperty getSpeed() {
		return this.speed;
	}
	
}
