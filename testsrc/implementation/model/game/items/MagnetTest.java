package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;

public class MagnetTest {

	private Item magnet;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		magnet = ItemFactory.createMagnet(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		assertEquals(testSnake.getProperties().getPickup().getPickupRadius(),1);
		magnet.onCollision(testSnake, 0);
		assertEquals(testSnake.getProperties().getPickup().getPickupRadius(),1);
	}
	
	/*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/
	
	@Test 
	public void testLastingEffect() {
		magnet = ItemFactory.createMagnet(pointZero, Optional.empty(), Optional.of(100L));
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		assertEquals(testSnake.getProperties().getPickup().getPickupRadius(),1);
		magnet.onCollision(testSnake, 1000L);
		assertEquals(testSnake.getProperties().getPickup().getPickupRadius(),2);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime(), Optional.of(1100L));
		assertFalse(testSnake.getEffects().get(0).getExpirationTime().isPresent());
		magnet = ItemFactory.createMagnet(pointZero, Optional.empty(), Optional.of(250L));
		magnet.onCollision(testSnake, 1050L);
		assertEquals(testSnake.getProperties().getPickup().getPickupRadius(),2);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime(), Optional.of(1350L));
		testSnake.getEffects().get(0).effectEnd(testSnake);
		assertEquals(testSnake.getProperties().getPickup().getPickupRadius(),1);
	}
	
}