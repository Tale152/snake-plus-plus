package implementation.model.game.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.Optional;

import org.junit.Test;

import design.model.game.Item;
import design.model.game.Snake;

public class GhostModeTest {
	
	private Item ghost;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		ghost = ItemFactory.createGhostMode(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		assertFalse(testSnake.getProperties().getCollision().getIntangibility());
		ghost.onCollision(testSnake, 0);
		assertFalse(testSnake.getProperties().getCollision().getIntangibility());
	}
	
	@Test 
	public void testLastingEffect() {
		ghost = ItemFactory.createGhostMode(pointZero, Optional.empty(), Optional.of(100L));
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		assertFalse(testSnake.getProperties().getCollision().getIntangibility());
		ghost.onCollision(testSnake, 1000L);
		assertTrue(testSnake.getProperties().getCollision().getIntangibility());
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime().get(), Optional.of(1100L));
		assertFalse(testSnake.getEffects().get(0).getExpirationTime().isPresent());
		ghost = ItemFactory.createGhostMode(pointZero, Optional.empty(), Optional.of(250L));
		ghost.onCollision(testSnake, 1050L);
		assertTrue(testSnake.getProperties().getCollision().getIntangibility());
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime().get(), Optional.of(1350L));
		testSnake.getEffects().get(0).effectEnd(testSnake);
		assertFalse(testSnake.getProperties().getCollision().getIntangibility());
	}
}
