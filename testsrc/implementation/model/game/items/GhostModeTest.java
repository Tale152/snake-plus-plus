package implementation.model.game.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import design.model.game.Field;
import design.model.game.Item;
import design.model.game.Snake;
import implementation.model.game.field.FieldImpl;

public class GhostModeTest {
	
	private Item ghost;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		ghost = itemFactory.createItem(pointZero, GhostMode.class, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		assertFalse(testSnake.getProperties().getCollisionProperty().getIntangibility());
		AppleTest.collide(ghost, testSnake);
		assertFalse(testSnake.getProperties().getCollisionProperty().getIntangibility());
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	public void testLastingEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		ghost = itemFactory.createItem(pointZero, GhostMode.class, Optional.empty(), Optional.of(10L));
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		AppleTest.collide(ghost, testSnake);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(10L));
		ghost = itemFactory.createItem(pointZero, GhostMode.class, Optional.empty(), Optional.of(10L));
		AppleTest.collide(ghost, testSnake);
		assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(20L));
		assertFalse(testSnake.getProperties().getCollisionProperty().getIntangibility());
		Thread t = new Thread(testSnake);
		t.start();
		try {
			Thread.sleep(10L);
			assertTrue(testSnake.getProperties().getCollisionProperty().getIntangibility());
			Thread.sleep(20L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.stop();
		assertFalse(testSnake.getProperties().getCollisionProperty().getIntangibility());
	}
}
