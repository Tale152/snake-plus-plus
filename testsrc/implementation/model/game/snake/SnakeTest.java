package implementation.model.game.snake;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.*;
import org.junit.Test;
import design.model.game.*;
import implementation.model.game.items.*;

public class SnakeTest {
	Snake snake; 
	List<Point> pos = new ArrayList<>(Arrays.asList(new Point(0,0)));
	
	@Test
	public void testInit() {
		snake = SnakeComponentsFactoryForTest.createSnake(pos,PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 1.0, 0L);
		assertTrue(snake.getPlayer() != null);
		assertTrue(snake.getProperties() != null);
		
	}
	
	@Test
	public void testNormalMove() {
		snake = SnakeComponentsFactoryForTest.createSnake(pos,PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 1.0, 0L);
		assertEquals(snake.getProperties().getLength().getLength(), 1);
		assertEquals(snake.move(new Point(0, 1)).size(),0);
		snake.getBodyParts().get(0).getPoint().equals(new Point(0,1));
		assertEquals(snake.getProperties().getLength().getLength(), 1);
	}
	
	@Test
	public void testLenghtenMove() {
		snake = SnakeComponentsFactoryForTest.createSnake(pos,PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 1.0, 0L);
		snake.getProperties().getLength().lengthen(1);
		assertEquals(snake.getProperties().getLength().getLength(), 2);
		assertEquals(snake.getBodyParts().size(), 1);
		List<Item> differences = snake.move(new Point(0, 1));
		assertEquals(differences.size(), 1);
		assertTrue(differences.get(0) instanceof BodyPart);
		assertEquals(differences.get(0).getPoint(), new Point(0,0));
		assertTrue(snake.getBodyParts().get(0).getPoint().equals(new Point(0,1)));
		assertTrue(snake.getBodyParts().get(1).getPoint().equals(new Point(0,0)));
		assertTrue(differences.get(0) == snake.getBodyParts().get(1));
		assertEquals(snake.getProperties().getLength().getLength(), 2);
		assertEquals(snake.getBodyParts().size(), 2);
		snake.getProperties().getLength().lengthen(2);
		assertEquals(snake.getProperties().getLength().getLength(), 4);
		assertEquals(snake.move(new Point(0, 2)).size(),1);
		assertEquals(snake.move(new Point(0, 3)).size(),1);
		assertEquals(snake.move(new Point(0, 4)).size(),0);
	}
	
	@Test
	public void testShortenMove() {
		List<Point> tmp = new ArrayList<>(Arrays.asList(new Point(0,3), new Point(0,2), new Point(0,1), new Point(0,0))); //Point(0,3) is head
		snake = SnakeComponentsFactoryForTest.createSnake(tmp,PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 1.0, 0L);
		snake.getProperties().getLength().shorten(2);
		assertEquals(snake.getProperties().getLength().getLength(), 2);
		assertEquals(snake.getBodyParts().size(), 4);
		List<Item> differences = snake.move(new Point(0, 4));
		assertEquals(differences.size(), 2);
		assertTrue(differences.get(0) instanceof BodyPart);
		assertTrue(differences.get(1) instanceof BodyPart);
		assertTrue(differences.stream().anyMatch(b -> {return b.getPoint().equals(new Point(0,0));}));
		assertTrue(differences.stream().anyMatch(b -> {return b.getPoint().equals(new Point(0,1));}));
		snake.getBodyParts().get(0).getPoint().equals(new Point(0,4));
		snake.getBodyParts().get(1).getPoint().equals(new Point(0,3));
		assertEquals(snake.move(new Point(0, 5)), 0);
		
	}
	
	@Test
	public void testKill() {
		snake = SnakeComponentsFactoryForTest.createSnake(pos,PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 1.0, 0L);
		assertTrue(snake.isAlive());
		snake.kill();
		assertFalse(snake.isAlive());
	}
	
	@Test
	public void testEffect() {
		snake = SnakeComponentsFactoryForTest.createSnake(pos,PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 1.0, 0L);
		assertEquals(snake.getEffects().size(), 0);
		Effect eff1 = new EffectAbstract(Optional.empty(), Optional.empty()) {
			protected void behaviorOnEffectStart(Snake target) {}
			protected void behaviorOnEffectEnd(Snake target) {}
		};
		
		Effect eff2 = new EffectAbstract(Optional.empty(), Optional.of(10L)) {
			protected void behaviorOnEffectStart(Snake target) {}
			protected void behaviorOnEffectEnd(Snake target) {}
		};
		
		snake.addEffect(eff1);
		assertEquals(snake.getEffects().size(),1);
		assertTrue(snake.getEffects().get(0) == eff1);
		assertFalse(snake.getEffects() == snake.getEffects()); //safe copies of internal list, so a new list every time
		snake.addEffect(eff2);
		assertEquals(snake.getEffects().size(),2);
		assertTrue(snake.removeEffect(eff1));
		assertEquals(snake.getEffects().size(),1);
		assertFalse(snake.removeEffect(eff1));
		assertEquals(snake.getEffects().size(),1);
		assertTrue(snake.getEffects().get(0) == eff2); //same instance
	}
	
	@Test
	public void testReverse() {
		List<Point> even = new ArrayList<>(Arrays.asList(new Point(0,3), new Point(0,2), new Point(0,1), new Point(0,0))); //Point(0,3) is head
		List<Point> odd = new ArrayList<>(even);
		odd.add(0, new Point(0,4)); //Point (0,4) is head
		//TODO implement
	}
	
	
}
