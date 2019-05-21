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
	
	public PropertiesImpl(Direction direction, long deltaT, double speedMultiplier) {
		this.length = new LengthPropertyImpl();
		this.direction = new DirectionPropertyImpl(direction);
		this.pickup = new PickupPropertyImpl();
		this.collision = new CollisionPropertyImpl();
		this.speed = new SpeedPropertyImpl(deltaT, speedMultiplier);
	}
	
	@Override
	public LengthProperty getLengthProperty() {
		return this.length;
	}

	@Override
	public DirectionProperty getDirectionProperty() {
		return this.direction;
	}

	@Override
	public PickupProperty getPickupProperty() {
		return this.pickup;
	}

	@Override
	public CollisionProperty getCollisionProperty() {
		return this.collision;
	}

	@Override
	public SpeedProperty getSpeedProperty() {
		return this.speed;
	}
	
}
