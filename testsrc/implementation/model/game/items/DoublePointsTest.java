package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;
import implementation.model.game.field.FieldImpl;

public class DoublePointsTest {

	private Item doublePoints;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		doublePoints = itemFactory.createItem(pointZero, DoublePoints.class, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		assertTrue(testSnake.getPlayer().getScoreMultiplier() == 1.0);
		assertEquals(testSnake.getPlayer().getScore(), 0);
		AppleTest.collide(doublePoints, testSnake);
		assertTrue(testSnake.getPlayer().getScoreMultiplier() == 1.0);
		assertEquals(testSnake.getPlayer().getScore(), 0);
	}
	
	/*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/
	
	@SuppressWarnings("deprecation")
	@Test 
	public void testLastingEffect() {
		
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		doublePoints = itemFactory.createItem(pointZero, DoublePoints.class, Optional.empty(), Optional.of(10L));
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		assertEquals(testSnake.getPlayer().getScore(), 0);
		AppleTest.collide(doublePoints, testSnake);
		assertEquals(testSnake.getPlayer().getScore(), 0);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(10L));
		doublePoints = itemFactory.createItem(pointZero, DoublePoints.class, Optional.empty(), Optional.of(10L));
		AppleTest.collide(doublePoints, testSnake);
		assertEquals(testSnake.getPlayer().getScore(), 0);
		assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(20L));
		assertTrue(testSnake.getPlayer().getScoreMultiplier() == 1.0);
		Thread t = new Thread(testSnake);
		t.start();
		assertTrue(testSnake.getPlayer().getScoreMultiplier() == 2.0);
		try {
			Thread.sleep(30L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.stop();
		assertTrue(testSnake.getPlayer().getScoreMultiplier() == 1.0);
	}
	
}
