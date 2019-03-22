package implementation.model.game;

import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import implementation.model.game.items.*;

public class TestEntireGame {

	@Test
	public void testSnake() {
		//TODO implement
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
		//new SpringTest().testInstantaneousEffect(); TODO i didn't program that part yet -cit
		//new SpringTest().testInstantaneousEffectOnGhost(); TODO i didn't program that part yet -cit
		//new SpringTest().testLastingEffect(); TODO i didn't program that part yet -cit
		new TurboTest().testInstantaneousEffect();
		//new TurboTest().testLastingEffect(); TODO applyMultiplier does not behave properly
	}
	
	
}
