package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;

public class DoublePointsTest {

	private Item doublePoints;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		doublePoints = ItemFactory.createDoublePoints(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		assertTrue(testSnake.getPlayer().getScoreMultiplier() == 1);
		doublePoints.onCollision(testSnake, 0);
		assertTrue(testSnake.getPlayer().getScoreMultiplier() == 1);
	}
	
	/*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/
	
	@Test 
	public void testLastingEffect() {
		doublePoints = ItemFactory.createDoublePoints(pointZero, Optional.empty(), Optional.of(100L));
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		assertTrue(testSnake.getPlayer().getScoreMultiplier() == 1);
		doublePoints.onCollision(testSnake, 1000L);
		assertTrue(testSnake.getPlayer().getScoreMultiplier() == 2);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime().get(), Optional.of(1100L));
		assertFalse(testSnake.getEffects().get(0).getExpirationTime().isPresent());
		doublePoints = ItemFactory.createDoublePoints(pointZero, Optional.empty(), Optional.of(250L));
		doublePoints.onCollision(testSnake, 1050L);
		assertTrue(testSnake.getPlayer().getScoreMultiplier() == 2);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime().get(), Optional.of(1350L));
		testSnake.getEffects().get(0).effectEnd(testSnake);
		assertTrue(testSnake.getPlayer().getScoreMultiplier() == 1);
	}
	
}
