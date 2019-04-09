package implementation.model.game;

import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import implementation.model.game.items.*;
import implementation.model.game.snake.*;

public class TestEntireGame {

	@Test
	public void testSnake() {
		new PlayerTest().testPlayer();
		new CollisionPropertyTest().testCollisionProperty();
		new DirectionPropertyTest().testDirectionProperty(); 
		new LengthPropertyTest().testLengthProperty();
		new PickupPropertyTest().testPickupProperty();
		new SpeedPropertyTest().testSpeedProperty();
		new PropertiesTest().testProperties();
		new SnakeTest().testInit();
		new SnakeTest().testEffect();
		new SnakeTest().testKill();
		new SnakeTest().testNormalMove();
		new SnakeTest().testLenghtenMove();
		new SnakeTest().testShortenMove();
		new SnakeTest().testReverse();
		
	}
	
	@Test
	public void testItems() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		new GeneralItemsTests().testInit();
		new GeneralItemsTests().testInstantaneousEffect();
		new GeneralItemsTests().testLastingEffect();
		new GeneralItemsTests().testOnGhost();
		new BodyPartTest().testInitBodyPart();
		new BodyPartTest().testAllCollisions();
		new WallTest().testInitWall();
		new WallTest().testCollision();
		new AppleTest().testInstantaneousEffect();
		new AppleTest().testInstantaneousEffectOnGhost();
		new AppleTest().testLastingEffect();
		new BadAppleTest().testInstantaneousEffect();
		new BadAppleTest().testInstantaneousEffectOnGhost();
		new BadAppleTest().testLastingEffect();
		new BeerTest().testInstantaneousEffect();
		new BeerTest().testLastingEffect();
		new DoublePointsTest().testInstantaneousEffect();
		new DoublePointsTest().testLastingEffect();
		new GhostModeTest().testInstantaneousEffect();
		new GhostModeTest().testLastingEffect();
		new GodModeTest().testInstantaneousEffect();
		new GodModeTest().testLastingEffect();
		new IceTest().testInstantaneousEffect();
		new IceTest().testLastingEffect();
		new MagnetTest().testInstantaneousEffect();
		new MagnetTest().testLastingEffect();
		new ScoreEarningTest().testInstantaneousEffect();
		new ScoreEarningTest().testInstantaneousEffectOnGhost();
		new ScoreEarningTest().testLastingEffect();
		new ScoreLossTest().testInstantaneousEffect();
		new ScoreLossTest().testInstantaneousEffectOnGhost();
		new ScoreLossTest().testLastingEffect();
		new SlugTest().testInstantaneousEffect();
		new SlugTest().testLastingEffect(); 
		new SpringTest().testInstantaneousEffect();
		new SpringTest().testInstantaneousEffectOnGhost();
		new SpringTest().testLastingEffect();
		new TurboTest().testInstantaneousEffect();
		new TurboTest().testLastingEffect();
	}
	
	
}
