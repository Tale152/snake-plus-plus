package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;

public class BeerTest {

	private Item beer;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		beer = ItemFactory.createBeer(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		assertFalse(testSnake.getProperties().getDirection().getReverseDirection());
		beer.onCollision(testSnake, 0);
		assertFalse(testSnake.getProperties().getDirection().getReverseDirection());
	}
	
	/*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/
	
	@Test 
	public void testLastingEffect() {
		beer = ItemFactory.createBeer(pointZero, Optional.empty(), Optional.of(100L));
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		assertFalse(testSnake.getProperties().getDirection().getReverseDirection());
		beer.onCollision(testSnake, 1000L);
		assertTrue(testSnake.getProperties().getDirection().getReverseDirection());
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime(), Optional.of(1100L));
		assertFalse(testSnake.getEffects().get(0).getExpirationTime().isPresent());
		beer = ItemFactory.createBeer(pointZero, Optional.empty(), Optional.of(250L));
		beer.onCollision(testSnake, 1050L);
		assertTrue(testSnake.getProperties().getDirection().getReverseDirection());
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime(), Optional.of(1350L));
		testSnake.getEffects().get(0).effectEnd(testSnake);
		assertFalse(testSnake.getProperties().getDirection().getReverseDirection());
	}
	
}
