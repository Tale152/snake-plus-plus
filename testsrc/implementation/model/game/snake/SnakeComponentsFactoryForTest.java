package implementation.model.game.snake;

import java.awt.Point;
import java.util.List;

import design.model.game.*;

public class SnakeComponentsFactoryForTest {

	public static Player createPlayer(PlayerNumber pn, String name, int startingScore, double baseMultiplier) {
		//TODO
		return null;
	}
	
	public static LengthProperty createLengthProperty(int initialLength) {
		//TODO
		return null;
	}
	
	public static DirectionProperty createDirectionProperty(Direction initialDirection, boolean reversDirection) {
		//TODO 
		return null;
	}
	
	public static PickupProperty createPickupProperty(int initialRange) {
		//TODO 
		return null;
	}
	
	public static CollisionProperty createCollisionProperty(boolean invincible, boolean intangible, boolean spring) {
		//TODO
		return null;
	}
	
	public static SpeedProperty createSpeedProperty(long delta, double speedMultipier, long lastUpdate) {
		//TODO
		return null;
	}
	
	public static Properties createProperties() {
		//TODO
		return null;
	}
	
	public static Snake createSnake(List<Point> initialPoints) {
		//TODO 
		return null;
	}
}

