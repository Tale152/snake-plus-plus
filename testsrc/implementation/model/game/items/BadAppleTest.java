package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;
import implementation.model.game.field.FieldImpl;

public class BadAppleTest {

	private Item badApple;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		badApple = itemFactory.createItem(pointZero, BadApple.class, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		testSnake.getPlayer().addScore(100);
		testSnake.getProperties().getLengthProperty().lengthen(5);
		assertEquals(testSnake.getPlayer().getScore(), 100);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 6);
		AppleTest.collide(badApple, testSnake);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 3);
		assertEquals(testSnake.getPlayer().getScore(), 50);
	}
	
	@Test
	public void testInstantaneousEffectOnGhost() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		badApple = itemFactory.createItem(pointZero, BadApple.class, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		testSnake.getPlayer().addScore(100);
		testSnake.getProperties().getLengthProperty().lengthen(5);
		assertEquals(testSnake.getPlayer().getScore(), 100);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 6);
		AppleTest.collide(badApple, testSnake);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 6);
		assertEquals(testSnake.getPlayer().getScore(), 100);
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	public void testLastingEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		badApple = itemFactory.createItem(pointZero, BadApple.class, Optional.empty(), Optional.of(10L));
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		testSnake.getPlayer().addScore(100);
		assertEquals(testSnake.getPlayer().getScore(), 100);
		testSnake.getProperties().getLengthProperty().lengthen(5);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 6);
		AppleTest.collide(badApple, testSnake);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 3);
		assertEquals(testSnake.getPlayer().getScore(), 50);
		badApple = itemFactory.createItem(pointZero, BadApple.class, Optional.empty(), Optional.of(10L));
		AppleTest.collide(badApple, testSnake);
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 2);
		assertEquals(testSnake.getPlayer().getScore(), 25);
		Thread t = new Thread(testSnake);
		t.start();
		try {
			Thread.sleep(30L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.stop();
		assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 6);
		assertEquals(testSnake.getPlayer().getScore(), 25);
	}
	
}
