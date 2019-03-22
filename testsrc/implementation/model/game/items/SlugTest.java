package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;

public class SlugTest {

	private Item slug;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		slug = ItemFactory.createSlug(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		double baseSpeedMul = testSnake.getProperties().getSpeed().getSpeedMultiplier();
		assertTrue(baseSpeedMul == testSnake.getProperties().getSpeed().getSpeedMultiplier());
		slug.onCollision(testSnake, 0);
		assertTrue(baseSpeedMul == testSnake.getProperties().getSpeed().getSpeedMultiplier());
	}
	
	/*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/
	
	@Test 
	public void testLastingEffect() {
		slug = ItemFactory.createSlug(pointZero, Optional.empty(), Optional.of(100L));
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		double baseSpeedMul = testSnake.getProperties().getSpeed().getSpeedMultiplier();
		assertTrue(baseSpeedMul == testSnake.getProperties().getSpeed().getSpeedMultiplier());
		slug.onCollision(testSnake, 1000L);
		assertTrue((baseSpeedMul*0.5) == testSnake.getProperties().getSpeed().getSpeedMultiplier());
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime(), Optional.of(1100L));
		assertFalse(testSnake.getEffects().get(0).getExpirationTime().isPresent());
		slug = ItemFactory.createSlug(pointZero, Optional.empty(), Optional.of(250L));
		slug.onCollision(testSnake, 1050L);
		System.out.println(testSnake.getProperties().getSpeed().getSpeedMultiplier());
		assertTrue((baseSpeedMul*0.5) == testSnake.getProperties().getSpeed().getSpeedMultiplier());
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime(), Optional.of(1350L));
		testSnake.getEffects().get(0).effectEnd(testSnake);
		assertTrue(baseSpeedMul == testSnake.getProperties().getSpeed().getSpeedMultiplier());
	}
	
}
