package implementation.model.game.snake;

import java.awt.Point;
import java.util.List;

import design.model.game.CollisionProperty;
import design.model.game.Direction;
import design.model.game.DirectionProperty;
import design.model.game.Field;
import design.model.game.LengthProperty;
import design.model.game.PickupProperty;
import design.model.game.Player;
import design.model.game.PlayerNumber;
import design.model.game.Properties;
import design.model.game.Snake;
import design.model.game.SpeedProperty;

/**
 * Factory to create all possible kind of snake's properties to use them into tests.
 */
public final class SnakeComponentsFactoryUtils {

    private SnakeComponentsFactoryUtils() {
        //not called
    }

    /**
     * This method creates a new player.
     * @param pn is the number of the player
     * @param name is the name of the player
     * @return the player created
     */
    public static Player createPlayer(final PlayerNumber pn, final String name) {
        return new PlayerImpl(pn, name);
    }

    /**
     * This method creates a new length property, with all default value.
     * @return the length property created
     */
    public static LengthProperty createLengthProperty() {
        return new LengthPropertyImpl();
    }

    /**
     * This method creates a new direction property.
     * @param initialDirection is the initial direction that snake will have
     * @return the direction property just created
     */
    public static DirectionProperty createDirectionProperty(final Direction initialDirection) {
        return new DirectionPropertyImpl(initialDirection);
    }

    /**
     * This method creates a new pick up property, with all default value.
     * @return the pick up property just created.
     */
    public static PickupProperty createPickupProperty() {
        return new PickupPropertyImpl();
    }

    /**
     * This method creates a new collision property.
     * @return the collision property just created.
     */
    public static CollisionProperty createCollisionProperty() {
        return new CollisionPropertyImpl();
    }

    /**
     * This method creates a new speed property.
     * @param delta is the time in milliseconds between two snake's movements.
     * @param speedMultiplier is the multiplier to apply to the delta
     * @return the speed property created.
     */
    public static SpeedProperty createSpeedProperty(final long delta, final double speedMultiplier) {
        return new SpeedPropertyImpl(delta, speedMultiplier);
    }

    /**
     * This method creates all the property: the player, length property, direction property
     * pick up property, collision property and speed property, all with default parameters.
     * @return a property which contains all snake's properties.
     */
    public static Properties createProperties() {
        return new PropertiesImpl(Direction.DOWN, 1000L, 1);
    }

    /**
     * This method creates a new snake.
     * @param playerNumber is the number of the player that owns snake
     * @param playerName is the name of the player that owns snake
     * @param direction is the first snake direction
     * @param deltaT is the time in milliseconds between two snake's movements
     * @param speedMultiplier is the multiplier to apply to snake's delta
     * @param field is the field where snake is
     * @param spawn is the point where snake will spawn
     * @return the snake just created
     */
    public static Snake createSnake(final PlayerNumber playerNumber, final String playerName, final Direction direction, final long deltaT, 
            final double speedMultiplier, final Field field, final List<Point> spawn) {
        return new SnakeImpl(playerNumber, playerName, direction, deltaT, speedMultiplier, field, spawn);
    }

}

