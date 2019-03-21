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
		snake = SnakeComponentsFactoryForTest.createSnake(pos);
		assertTrue(snake.getPlayer() != null);
		assertTrue(snake.getProperties() != null);
		
	}
	
	@Test
	public void testNormalMove() {
		snake = SnakeComponentsFactoryForTest.createSnake(pos);
		snake.getProperties().getDirection().setDirection(Direction.RIGHT);
		assertEquals(snake.getProperties().getLength().getLength(), 1);
		assertEquals(snake.move(new Point(0, 1)).size(),0);
		//TODO verifica che la posizione della testa ora si sia effettivamente spostata in (0,1)
	}
	
	@Test
	public void testLenghtenMove() {
		snake = SnakeComponentsFactoryForTest.createSnake(pos);
		snake.getProperties().getDirection().setDirection(Direction.RIGHT);
		snake.getProperties().getLength().lengthen(1);
		assertEquals(snake.getProperties().getLength().getLength(), 2);
		List<Item> differences = snake.move(new Point(0, 1));
		assertEquals(differences.size(), 1);
		assertTrue(differences.get(0) instanceof BodyPart);
		assertEquals(differences.get(0).getPoint(), new Point(0,0));
		//TODO verifica che ora il corpo sia effettivamente composto da 2 pezzi e che essi siano in (0,1) e (0,2) (serve ottenere tutta la lista dei bodypart)
		//e che la coda sia la stessa Istanza contenuta dentro l'unico item di differences
		snake.getProperties().getLength().lengthen(2);
		assertEquals(snake.getProperties().getLength().getLength(), 4);
		assertEquals(snake.move(new Point(0, 2)).size(),1);
		assertEquals(snake.move(new Point(0, 3)).size(),1);
		assertEquals(differences.get(0).getPoint(), new Point(0, 2)); //just to be sure that you updated Point and not BodyPart
	}
	
	@Test
	public void testShortenMove() {
		List<Point> tmp = new ArrayList<>(Arrays.asList(new Point(0,3), new Point(0,2), new Point(0,1), new Point(0,0))); //Point(0,3) is head
		snake = SnakeComponentsFactoryForTest.createSnake(tmp);
		snake.getProperties().getDirection().setDirection(Direction.RIGHT);
		snake.getProperties().getLength().shorten(2);
		assertEquals(snake.getProperties().getLength().getLength(), 2);
		List<Item> differences = snake.move(new Point(0, 4));
		assertEquals(differences.size(), 2);
		assertTrue(differences.get(0) instanceof BodyPart);
		assertTrue(differences.get(1) instanceof BodyPart);
		assertTrue(differences.stream().anyMatch(b -> {return b.getPoint().equals(new Point(0,0));}));
		assertTrue(differences.stream().anyMatch(b -> {return b.getPoint().equals(new Point(0, 1));}));
		//TODO verificare che i pezzi rimasti siano in posizione (0,3) e (0,4)
		assertEquals(snake.move(new Point(0, 5)), 0);
		
	}
	
	@Test
	public void testKill() {
		snake = SnakeComponentsFactoryForTest.createSnake(pos);
		assertTrue(snake.isAlive());
		snake.kill();
		assertFalse(snake.isAlive());
	}
	
	@Test
	public void testEffect() {
		snake = SnakeComponentsFactoryForTest.createSnake(pos);
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
		fail("beh senza sapere come stan messi i pezzi non c'è proprio niente da implementare per ora, Eli metti quel metodo maledetto");
	}
	
	
}
