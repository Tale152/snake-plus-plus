package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;

public class TurboTest {

	private Item turbo;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		turbo = ItemFactory.createTurbo(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		double baseSpeedMul = testSnake.getProperties().getSpeed().getSpeedMultiplier();
		assertTrue(baseSpeedMul == testSnake.getProperties().getSpeed().getSpeedMultiplier());
		turbo.onCollision(testSnake, 0);
		assertTrue(baseSpeedMul == testSnake.getProperties().getSpeed().getSpeedMultiplier());
	}
	
	/*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/
	
	@Test 
	public void testLastingEffect() {
		turbo = ItemFactory.createTurbo(pointZero, Optional.empty(), Optional.of(100L));
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		double baseSpeedMul = testSnake.getProperties().getSpeed().getSpeedMultiplier();
		assertTrue(baseSpeedMul == testSnake.getProperties().getSpeed().getSpeedMultiplier());
		turbo.onCollision(testSnake, 1000L);
		assertTrue((baseSpeedMul*1.5) == testSnake.getProperties().getSpeed().getSpeedMultiplier());
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime().get(), Optional.of(1100L));
		assertFalse(testSnake.getEffects().get(0).getExpirationTime().isPresent());
		turbo = ItemFactory.createTurbo(pointZero, Optional.empty(), Optional.of(250L));
		turbo.onCollision(testSnake, 1050L);
		assertTrue((baseSpeedMul*1.5) == testSnake.getProperties().getSpeed().getSpeedMultiplier());
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime().get(), Optional.of(1350L));
		testSnake.getEffects().get(0).effectEnd(testSnake);
		assertTrue(baseSpeedMul == testSnake.getProperties().getSpeed().getSpeedMultiplier());
	}
	
}