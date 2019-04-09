package implementation.model.game.snake;

import java.awt.Point;
import java.util.List;

import design.model.game.*;

public class SnakeComponentsFactoryForTest {

	public static Player createPlayer(PlayerNumber pn, String name) {
		return new PlayerImpl(pn, name);
	}
	
	public static LengthProperty createLengthProperty() {
		return new LengthPropertyImpl();
	}
	
	public static DirectionProperty createDirectionProperty(Direction initialDirection) {
		return new DirectionPropertyImpl(initialDirection);
	}
	
	public static PickupProperty createPickupProperty() {
		return new PickupPropertyImpl();
	}
	
	public static CollisionProperty createCollisionProperty() {
		return new CollisionPropertyImpl();
	}
	
	public static SpeedProperty createSpeedProperty(long delta, double speedMultiplier, long lastUpdate) {
		return new SpeedPropertyImpl(delta, speedMultiplier, lastUpdate);
	}
	
	public static Properties createProperties() {
		return new PropertiesImpl(Direction.DOWN, 1000L, 1, 0);
	}
	
	public static Snake createSnake(List<Point> initialPoints, PlayerNumber playerNumber, String playerName, Direction direction, long deltaT, double speedMultiplier, long lastUpdate ) {
		return new SnakeImpl(initialPoints, playerNumber, playerName, direction, deltaT, speedMultiplier, lastUpdate);
	}
}

