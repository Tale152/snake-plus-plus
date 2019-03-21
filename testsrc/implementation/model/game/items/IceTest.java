package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;

public class IceTest {
	
	private Item ice;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		ice = ItemFactory.createIce(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		double speedMultiplier = testSnake.getProperties().getSpeed().getSpeedMultiplier();
		ice.onCollision(testSnake, 0);
		assertTrue(testSnake.getProperties().getSpeed().getSpeedMultiplier() == speedMultiplier);
	}
	
	/*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/
	
	@Test 
	public void testLastingEffect() {
		ice = ItemFactory.createIce(pointZero, Optional.empty(), Optional.of(100L));
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		double speedMultiplier = testSnake.getProperties().getSpeed().getSpeedMultiplier();
		ice.onCollision(testSnake, 1000L);
		assertTrue(testSnake.getProperties().getSpeed().getSpeedMultiplier() == 0);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime(), Optional.of(1100L));
		assertFalse(testSnake.getEffects().get(0).getExpirationTime().isPresent());
		ice = ItemFactory.createIce(pointZero, Optional.empty(), Optional.of(250L));
		ice.onCollision(testSnake, 1050L);
		assertTrue(testSnake.getProperties().getSpeed().getSpeedMultiplier() == 0);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime(), Optional.of(1350L));
		testSnake.getEffects().get(0).effectEnd(testSnake);
		assertTrue(testSnake.getProperties().getSpeed().getSpeedMultiplier() == speedMultiplier);
	}
}