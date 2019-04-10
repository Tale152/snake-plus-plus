package implementation.model.game.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.*;
import org.junit.Test;
import design.model.game.*;

public class SpringTest {
	
	private Item spring;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		spring = ItemFactory.createSpring(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		assertEquals(testSnake.getProperties().getDirection().getDirection(), Direction.RIGHT);
		spring.onCollision(testSnake, 0);
		assertEquals(testSnake.getProperties().getDirection().getDirection(), Direction.LEFT);
		assertTrue(testSnake.getEffects().isEmpty());
		assertFalse(testSnake.getProperties().getCollision().getSpring());
		
		spring = ItemFactory.createSpring(pointZero, Optional.empty(), Optional.empty());
		testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(1,0), new Point(0,0), new Point(0,1))));
		assertEquals(testSnake.getProperties().getDirection().getDirection(), Direction.RIGHT);
		spring.onCollision(testSnake, 0);
		assertEquals(testSnake.getProperties().getDirection().getDirection(), Direction.DOWN);
		assertEquals(testSnake.getBodyParts().get(0).getPoint(), new Point(0,1));
		assertEquals(testSnake.getBodyParts().get(1).getPoint(), new Point(0,0));
		assertEquals(testSnake.getBodyParts().get(2).getPoint(), new Point(1,0));
		assertTrue(testSnake.getEffects().isEmpty());
		assertFalse(testSnake.getProperties().getCollision().getSpring());
	}
	
	@Test
	public void testInstantaneousEffectOnGhost() {
		spring = ItemFactory.createSpring(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		assertEquals(testSnake.getProperties().getDirection().getDirection(), Direction.RIGHT);
		spring.onCollision(testSnake, 0);
		assertEquals(testSnake.getProperties().getDirection().getDirection(), Direction.RIGHT);
		assertTrue(testSnake.getEffects().isEmpty());
		assertFalse(testSnake.getProperties().getCollision().getSpring());
		
		spring = ItemFactory.createSpring(pointZero, Optional.empty(), Optional.empty());
		testSnake = SnakeFactoryForTests.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(1,0), new Point(0,0), new Point(0,1))));
		assertEquals(testSnake.getProperties().getDirection().getDirection(), Direction.RIGHT);
		spring.onCollision(testSnake, 0);
		assertEquals(testSnake.getProperties().getDirection().getDirection(), Direction.RIGHT);
		assertEquals(testSnake.getBodyParts().get(0).getPoint(), new Point(1,0));
		assertEquals(testSnake.getBodyParts().get(1).getPoint(), new Point(0,0));
		assertEquals(testSnake.getBodyParts().get(2).getPoint(), new Point(0,1));
		assertTrue(testSnake.getEffects().isEmpty());
		assertFalse(testSnake.getProperties().getCollision().getSpring());
	}
	
	@Test 
	public void testLastingEffect() {
		spring = ItemFactory.createSpring(pointZero, Optional.empty(), Optional.of(100L));
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		assertEquals(testSnake.getProperties().getDirection().getDirection(), Direction.RIGHT);
		spring.onCollision(testSnake, 0);
		assertEquals(testSnake.getProperties().getDirection().getDirection(), Direction.LEFT);
		assertEquals(testSnake.getEffects().size(), 1);
		assertTrue(testSnake.getProperties().getCollision().getSpring());
		testSnake.getEffects().get(0).effectEnd(testSnake);
		assertFalse(testSnake.getProperties().getCollision().getSpring());
		
		spring = ItemFactory.createSpring(pointZero, Optional.empty(), Optional.of(100L));
		testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(1,0), new Point(0,0), new Point(0,1))));
		assertEquals(testSnake.getProperties().getDirection().getDirection(), Direction.RIGHT);
		spring.onCollision(testSnake, 0);
		assertEquals(testSnake.getProperties().getDirection().getDirection(), Direction.DOWN);
		assertEquals(testSnake.getBodyParts().get(0).getPoint(), new Point(0,1));
		assertEquals(testSnake.getBodyParts().get(1).getPoint(), new Point(0,0));
		assertEquals(testSnake.getBodyParts().get(2).getPoint(), new Point(1,0));
		assertEquals(testSnake.getEffects().size(), 1);
		assertTrue(testSnake.getProperties().getCollision().getSpring());
		testSnake.getEffects().get(0).effectEnd(testSnake);
		assertFalse(testSnake.getProperties().getCollision().getSpring());
	}
	
}
