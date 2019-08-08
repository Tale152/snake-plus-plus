package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;
import implementation.model.game.field.FieldImpl;

public class AppleTest {

	private Item apple;
	private Point pointZero = new Point(0,0);
	
	public static void collide(Item item, Snake snake) {
		try {
			item.onCollision(snake);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			fail("something went very bad");
		}
	}
	
	@Test
	public void testInstantaneousEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		apple = itemFactory.createItem(pointZero, Apple.class, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		assertEquals(testSnake.getPlayer().getScore(), 0);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1);
		collide(apple, testSnake);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 2);
		assertEquals(testSnake.getPlayer().getScore(), (int)(testSnake.getPlayer().getScoreMultiplier() * 10));
	}
	
	@Test
	public void testInstantaneousEffectOnGhost() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		apple = itemFactory.createItem(pointZero, Apple.class, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		assertEquals(testSnake.getPlayer().getScore(), 0);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1);
		collide(apple, testSnake);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1);
		assertEquals(testSnake.getPlayer().getScore(), 0);
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	public void testLastingEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		apple = itemFactory.createItem(pointZero, Apple.class, Optional.empty(), Optional.of(10L));
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		assertEquals(testSnake.getPlayer().getScore(), 0);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1);
		collide(apple, testSnake);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 2);
		assertEquals(testSnake.getPlayer().getScore(), 10);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(10L));
		apple = itemFactory.createItem(pointZero, Apple.class, Optional.empty(), Optional.of(10L));
		collide(apple, testSnake);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 3);
		assertEquals(testSnake.getPlayer().getScore(), 20);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(20L));
		Thread t = new Thread(testSnake);
		t.start();
		try {
			Thread.sleep(30L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.stop();
		assertEquals(testSnake.getPlayer().getScore(), 0);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1);
	}
	
}
