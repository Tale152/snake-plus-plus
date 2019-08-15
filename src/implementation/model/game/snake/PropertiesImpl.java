package implementation.model.game.snake;

import design.model.game.CollisionProperty;
import design.model.game.Direction;
import design.model.game.DirectionProperty;
import design.model.game.LengthProperty;
import design.model.game.PickupProperty;
import design.model.game.Properties;
import design.model.game.SpeedProperty;

/**
 * This class initialize and return all the snake different properties,
 * such as length, direction, collision, speed and pick up properties.
 * @author Elisa Tronetti
 */
public class PropertiesImpl implements Properties {

    private final LengthProperty length;
    private final DirectionProperty direction;
    private final PickupProperty pickup;
    private final CollisionProperty collision;
    private final SpeedProperty speed;

    /**
     * This method initialize all the properties.
     * @param direction is the direction set to snake at first
     * @param deltaT is the delta of snaka's movement
     * @param speedMultiplier is the multiplier of the normal snake speed
     */
    public PropertiesImpl(final Direction direction, final long deltaT, final double speedMultiplier) {
        this.length = new LengthPropertyImpl();
        this.direction = new DirectionPropertyImpl(direction);
        this.pickup = new PickupPropertyImpl();
        this.collision = new CollisionPropertyImpl();
        this.speed = new SpeedPropertyImpl(deltaT, speedMultiplier);
    }

    @Override
    public final LengthProperty getLengthProperty() {
        return this.length;
    }

    @Override
    public final DirectionProperty getDirectionProperty() {
        return this.direction;
    }

    @Override
    public final PickupProperty getPickupProperty() {
        return this.pickup;
    }

    @Override
    public final CollisionProperty getCollisionProperty() {
        return this.collision;
    }

    @Override
    public final SpeedProperty getSpeedProperty() {
        return this.speed;
    }

}
