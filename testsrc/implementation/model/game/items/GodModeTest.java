package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;
import implementation.model.game.field.FieldImpl;

public class GodModeTest {
	
	private Item god;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		god = itemFactory.createItem(pointZero, GodMode.class, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		assertFalse(testSnake.getProperties().getCollisionProperty().getInvincibility());
		AppleTest.collide(god, testSnake);
		assertFalse(testSnake.getProperties().getCollisionProperty().getInvincibility());
	}
	
	/*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/
	
	@SuppressWarnings("deprecation")
	@Test 
	public void testLastingEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		god = itemFactory.createItem(pointZero, GodMode.class, Optional.empty(), Optional.of(10L));
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		AppleTest.collide(god, testSnake);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(10L));
		god = itemFactory.createItem(pointZero, GodMode.class, Optional.empty(), Optional.of(10L));
		AppleTest.collide(god, testSnake);
		assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(20L));
		assertFalse(testSnake.getProperties().getCollisionProperty().getInvincibility());
		Thread t = new Thread(testSnake);
		t.start();
		try {
			Thread.sleep(10L);
			assertTrue(testSnake.getProperties().getCollisionProperty().getInvincibility());
			Thread.sleep(20L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.stop();
		assertFalse(testSnake.getProperties().getCollisionProperty().getInvincibility());
	}
}
