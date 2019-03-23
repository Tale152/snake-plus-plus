package implementation.model.game;

import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import implementation.model.game.items.*;
import implementation.model.game.snake.*;

public class TestEntireGame {

	@Test
	public void testSnake() {
		// new PlayerTest().testPlayer(); TODO il check degli argomenti chiama l'equals su null, che da nullpointer invece di illegal arguments
		new CollisionPropertyTest().testCollisionProperty();
		// new DirectionPropertyTest().testDirectionProperty(); TODO il check di null è sbagliato e direction non gestisce quando la reverse è attiva
		// new LengthPropertyTest().testLengthProperty(); TODO può far comodo poter passare zero a shorten e lenghten
		new PickupPropertyTest().testPickupProperty();
		// new SpeedPropertyTest().testSpeedProperty(); TODO un po di cose da correggere, in più mi sa che prima devi convertire il last update in double, moltiplicarlo per il multilier e POI riconvertirlo in long sennò i dati si sminchiano
		/*new PropertiesTest().testProperties(); //TODO manca Point nel costruttore di Snake
		new SnakeTest().testInit();
		new SnakeTest().testEffect();
		new SnakeTest().testKill();
		new SnakeTest().testNormalMove();
		new SnakeTest().testLenghtenMove();
		new SnakeTest().testShortenMove();
		new SnakeTest().testReverse();*/
		
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
		//new WallTest().testCollision(); TODO reverse does not work
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
		//new SlugTest().testLastingEffect(); TODO applyMultiplier does not behave properly
		//new SpringTest().testInstantaneousEffect(); TODO i haven't program that part yet -cit
		//new SpringTest().testInstantaneousEffectOnGhost(); TODO i haven't program that part yet -cit
		//new SpringTest().testLastingEffect(); TODO i haven't program that part yet -cit
		new TurboTest().testInstantaneousEffect();
		//new TurboTest().testLastingEffect(); TODO applyMultiplier does not behave properly
	}
	
	
}
