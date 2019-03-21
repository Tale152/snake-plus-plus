package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;

public class BadAppleTest {

	private Item badApple;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		badApple = ItemFactory.createBadApple(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		testSnake.getPlayer().addScore(100);
		testSnake.getProperties().getLength().lengthen(5);
		assertEquals(testSnake.getPlayer().getScore(), 100);
		assertEquals(testSnake.getProperties().getLength().getLength(), 6);
		badApple.onCollision(testSnake, 0);
		assertEquals(testSnake.getProperties().getLength().getLength(), 3);
		assertEquals(testSnake.getPlayer().getScore(), 50);
	}
	
	@Test
	public void testInstantaneousEffectOnGhost() {
		badApple = ItemFactory.createBadApple(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		testSnake.getPlayer().addScore(100);
		testSnake.getProperties().getLength().lengthen(5);
		assertEquals(testSnake.getPlayer().getScore(), 100);
		assertEquals(testSnake.getProperties().getLength().getLength(), 6);
		badApple.onCollision(testSnake, 0);
		assertEquals(testSnake.getProperties().getLength().getLength(), 6);
		assertEquals(testSnake.getPlayer().getScore(), 100);
	}
	
	@Test 
	public void testLastingEffect() {
		badApple = ItemFactory.createBadApple(pointZero, Optional.empty(), Optional.of(100L));
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		testSnake.getPlayer().addScore(1000);
		testSnake.getProperties().getLength().lengthen(7);
		assertEquals(testSnake.getPlayer().getScore(), 1000);
		assertEquals(testSnake.getProperties().getLength().getLength(), 8);
		badApple.onCollision(testSnake, 1000L);
		assertEquals(testSnake.getProperties().getLength().getLength(), 4);
		assertEquals(testSnake.getPlayer().getScore(), 500);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime(), Optional.of(1100L));
		assertFalse(testSnake.getEffects().get(0).getExpirationTime().isPresent());
		badApple = ItemFactory.createBadApple(pointZero, Optional.empty(), Optional.of(250L));
		badApple.onCollision(testSnake, 1050L);
		assertEquals(testSnake.getProperties().getLength().getLength(), 2);
		assertEquals(testSnake.getPlayer().getScore(), 250);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime(), Optional.of(1350L));
		testSnake.getEffects().get(0).effectEnd(testSnake);
		assertEquals(testSnake.getPlayer().getScore(), 250);
		assertEquals(testSnake.getProperties().getLength().getLength(), 8);
	}
	
}
