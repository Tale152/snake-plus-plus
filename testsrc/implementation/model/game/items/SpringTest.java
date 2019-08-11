package implementation.model.game.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.*;
import org.junit.Test;
import design.model.game.*;
import implementation.model.game.field.FieldImpl;

public class SpringTest {
	
	private Item spring;
	private Point pointZero = new Point(2,0);
	
	@SuppressWarnings("deprecation")
	@Test
	public void testInstantaneousEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		spring = itemFactory.createItem(pointZero, Spring.class, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(1,0), new Point(0,0))), field);
		assertEquals(testSnake.getProperties().getDirectionProperty().getDirection(), Direction.RIGHT);
		AppleTest.collide(spring, testSnake);
		
		Thread t = new Thread(testSnake);
		t.start();
		try {
			while(testSnake.getProperties().getDirectionProperty().getDirection().equals(Direction.RIGHT)) {
				Thread.sleep(10L);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.stop();
		assertEquals(testSnake.getProperties().getDirectionProperty().getDirection(), Direction.LEFT);
		assertTrue(testSnake.getBodyParts().get(1).getPoint().getX() - testSnake.getBodyParts().get(0).getPoint().getX() <= 1);
		assertTrue(testSnake.getBodyParts().get(0).getPoint().equals(new Point(1,0)));
		assertTrue(testSnake.getBodyParts().get(1).getPoint().equals(new Point(2,0)));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testInstantaneousEffectOnGhost() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		spring = itemFactory.createItem(pointZero, Spring.class, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(1,0), new Point(0,0))), field);
		assertEquals(testSnake.getProperties().getDirectionProperty().getDirection(), Direction.RIGHT);
		AppleTest.collide(spring, testSnake);
		
		Thread t = new Thread(testSnake);
		t.start();
		try {
			while(testSnake.getBodyParts().get(0).getPoint().equals(new Point(1,0))) {
				Thread.sleep(10L);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.stop();
		assertEquals(testSnake.getProperties().getDirectionProperty().getDirection(), Direction.RIGHT);
		assertTrue(testSnake.getBodyParts().get(0).getPoint().equals(new Point(2,0)));
		assertTrue(testSnake.getBodyParts().get(1).getPoint().equals(new Point(1,0)));
	}
	
	
	@SuppressWarnings("deprecation")
	@Test 
	public void testLastingEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		spring = itemFactory.createItem(pointZero, Spring.class, Optional.empty(), Optional.of(10L));
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(1,0), new Point(0,0))), field);
		assertEquals(testSnake.getProperties().getDirectionProperty().getDirection(), Direction.RIGHT);
		
		AppleTest.collide(spring, testSnake);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(10L));
		
		spring = itemFactory.createItem(pointZero, Spring.class, Optional.empty(), Optional.of(10L));
		AppleTest.collide(spring, testSnake);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(20L));
	
		Thread t = new Thread(testSnake);
		t.start();
		try {
			Thread.sleep(10L);
			assertTrue(testSnake.getProperties().getCollisionProperty().getSpring());
			Thread.sleep(20L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.stop();
		assertFalse(testSnake.getProperties().getCollisionProperty().getSpring());	
	}	
}