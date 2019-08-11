package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;
import implementation.model.game.field.FieldImpl;

public class BeerTest {

	private Item beer;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		beer = itemFactory.createItem(pointZero, Beer.class, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		assertEquals(testSnake.getProperties().getDirectionProperty().getDirection(), Direction.RIGHT);
		AppleTest.collide(beer, testSnake);
		assertTrue(testSnake.getProperties().getDirectionProperty().getDirection().equals(Direction.UP) || 
				testSnake.getProperties().getDirectionProperty().getDirection().equals(Direction.DOWN));
		
		assertFalse(testSnake.getProperties().getDirectionProperty().getReverseDirection());
	}
	
	@Test
	public void testInstantaneousEffectOnGhost() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		beer = itemFactory.createItem(pointZero, Beer.class, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		Direction current = testSnake.getProperties().getDirectionProperty().getDirection();
		AppleTest.collide(beer, testSnake);
		assertEquals(testSnake.getProperties().getDirectionProperty().getDirection(), current);
		assertFalse(testSnake.getProperties().getDirectionProperty().getReverseDirection());
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	public void testLastingEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		beer = itemFactory.createItem(pointZero, Beer.class, Optional.empty(), Optional.of(10L));
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		assertFalse(testSnake.getProperties().getDirectionProperty().getReverseDirection());
		AppleTest.collide(beer, testSnake);
		assertFalse(Direction.RIGHT.equals(testSnake.getProperties().getDirectionProperty().getDirection()));
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(10L));
		beer = itemFactory.createItem(pointZero, Beer.class, Optional.empty(), Optional.of(10L));
		AppleTest.collide(beer, testSnake);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(20L));
		assertTrue(testSnake.getProperties().getDirectionProperty().getDirection().equals(Direction.RIGHT) ||
				testSnake.getProperties().getDirectionProperty().getDirection().equals(Direction.LEFT));
		Thread t = new Thread(testSnake);
		assertFalse(testSnake.getProperties().getDirectionProperty().getReverseDirection());
		t.start();
		try {
			Thread.sleep(10L);
			assertTrue(testSnake.getProperties().getDirectionProperty().getReverseDirection());
			Thread.sleep(20L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.stop();
		assertFalse(testSnake.getProperties().getDirectionProperty().getReverseDirection());
		
	}
	
}
