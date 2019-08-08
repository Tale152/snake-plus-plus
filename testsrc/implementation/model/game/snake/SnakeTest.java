package implementation.model.game.snake;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.*;
import org.junit.Test;
import design.model.game.*;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.items.*;

public class SnakeTest {
	Snake snake; 
	List<Point> pos = new ArrayList<>(Arrays.asList(new Point(0,0)));
	
	@Test
	public void testInit() {

		snake = SnakeComponentsFactoryForTest.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
				1.0, new FieldImpl(new Point(1000,1000)), new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		assertTrue(snake.getPlayer() != null);
		assertTrue(snake.getProperties() != null);
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testNormalMove() {
		snake = SnakeComponentsFactoryForTest.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
				1.0, new FieldImpl(new Point(1000,1000)), new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		assertEquals(snake.getProperties().getLengthProperty().getLength(), 1);
		Thread t = new Thread(snake);
		t.start();
		while(true) {
			if (snake.getBodyParts().get(0).getPoint().getX() >= 3) {
				t.stop();
				break;
			}
		}
		snake.getBodyParts().get(0).getPoint().equals(new Point(0,1));
		assertEquals(snake.getProperties().getLengthProperty().getLength(), 1);
		
		List<Point> tmp = new ArrayList<>(Arrays.asList(new Point(3,0), new Point(2,0), new Point(1,0), new Point(0,0))); //Point(3,0) is head
		snake = SnakeComponentsFactoryForTest.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
				1.0, new FieldImpl(new Point(1000,1000)), tmp);
		assertEquals(snake.getProperties().getLengthProperty().getLength(), 4);
		t = new Thread(snake);
		t.start();
		while(true) {
			if (snake.getBodyParts().get(0).getPoint().getX() >= 5) {
				t.stop();
				break;
			}
		}
		snake.getBodyParts().get(1).getPoint().equals(new Point((int)snake.getBodyParts().get(0).getPoint().getX() - 1,0));
		snake.getBodyParts().get(2).getPoint().equals(new Point((int)snake.getBodyParts().get(0).getPoint().getX() - 2,0));
		snake.getBodyParts().get(3).getPoint().equals(new Point((int)snake.getBodyParts().get(0).getPoint().getX() - 3,0));
		assertEquals(snake.getProperties().getLengthProperty().getLength(), 4); 
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testLenghtenMove() {
		snake = SnakeComponentsFactoryForTest.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
				1.0, new FieldImpl(new Point(1000,1000)), new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		snake.getProperties().getLengthProperty().lengthen(1);
		assertEquals(snake.getProperties().getLengthProperty().getLength(), 2);
		assertEquals(snake.getBodyParts().size(), 1);
		Thread t = new Thread(snake);
		t.start();
		while(true) {
			if (snake.getBodyParts().size() == 2) {
				t.stop();
				break;
			}
		}
		
		snake = SnakeComponentsFactoryForTest.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
				1.0, new FieldImpl(new Point(1000,1000)), new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		snake.getProperties().getLengthProperty().lengthen(3);
		assertEquals(snake.getProperties().getLengthProperty().getLength(), 4);
		assertEquals(snake.getBodyParts().size(), 1);
		t = new Thread(snake);
		t.start();
		while(true) {
			if (snake.getBodyParts().size() == 4) {
				t.stop();
				break;
			}
		}
		int headX = (int)snake.getBodyParts().get(0).getPoint().getX();
		for (int i = 1; i < snake.getBodyParts().size(); ++i) {
			assertEquals(headX - i, (int) snake.getBodyParts().get(i).getPoint().getX());
		}

	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testShortenMove() {
		List<Point> tmp = new ArrayList<>(Arrays.asList(new Point(3,0), new Point(2,0), new Point(1,0), new Point(0,0))); //Point(3,0) is head
		snake = SnakeComponentsFactoryForTest.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
				1.0, new FieldImpl(new Point(1000,1000)), tmp);
		assertEquals(snake.getProperties().getLengthProperty().getLength(), 4);
		assertEquals(snake.getBodyParts().size(), 4);
		snake.getProperties().getLengthProperty().shorten(2);
		assertEquals(snake.getProperties().getLengthProperty().getLength(), 2);
		assertEquals(snake.getBodyParts().size(), 4);
		Thread t = new Thread(snake);
		t.start();
		while(true) {
			if (snake.getBodyParts().size() == 2) {
				t.stop();
				break;
			}
		}
		int headX = (int)snake.getBodyParts().get(0).getPoint().getX();
		for (int i = 1; i < snake.getBodyParts().size(); ++i) {
			assertEquals(headX - i, (int) snake.getBodyParts().get(i).getPoint().getX());
		}
	}
	
	@Test
	public void testKill() {
		snake = SnakeComponentsFactoryForTest.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
				1.0, new FieldImpl(new Point(1000,1000)), new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		assertTrue(snake.isAlive());
		snake.kill();
		assertFalse(snake.isAlive());
	}
	
	@Test
	public void testEffect() {
		snake = SnakeComponentsFactoryForTest.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
				1.0, new FieldImpl(new Point(1000,1000)), new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		assertEquals(snake.getEffects().size(), 0);
		Effect eff1 = new EffectAbstract(Optional.empty()) {
			@Override
			public void instantaneousEffect(Snake target) {}
			@Override
			public void expirationEffect(Field field) {}
			@Override
			protected void behaviorOnLastingEffectStart(Snake snake) {}
			@Override
			protected void behaviorOnLastingEffectEnd(Snake snake) {}
		};
		
		Effect eff2 = new EffectAbstract(Optional.empty()) {
			@Override
			public void instantaneousEffect(Snake target) {}
			@Override
			public void expirationEffect(Field field) {}
			@Override
			protected void behaviorOnLastingEffectStart(Snake snake) {}
			@Override
			protected void behaviorOnLastingEffectEnd(Snake snake) {}
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
		List<Point> tmp = new ArrayList<>(Arrays.asList(new Point(3,0), new Point(2,0), new Point(1,0), new Point(0,0))); //Point(3,0) is head
		snake = SnakeComponentsFactoryForTest.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
				1.0, new FieldImpl(new Point(1000,1000)), tmp);
		assertEquals(snake.getProperties().getDirectionProperty().getDirection(), Direction.RIGHT);
		snake.reverse();
		assertEquals(snake.getProperties().getDirectionProperty().getDirection(), Direction.LEFT);
		for (int i = 0; i < 4; ++i) {
			assertEquals(new Point(i,0), snake.getBodyParts().get(i).getPoint());
		}
		
	}
	
	
}
