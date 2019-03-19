package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import org.junit.Test;
import design.model.game.*;

public class BodyPartTest {

	private Item bodyPart;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInitBodyPart() {
		
		try{
            bodyPart = ItemFactory.createBodyPart(pointZero, null);
            fail("Body part's owner snake cannot be null");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
		
		try{
            bodyPart = ItemFactory.createBodyPart(null, SnakeFactoryForTests.baseSnake());
            fail("Body part's point cannot ben null");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
		
		bodyPart = ItemFactory.createBodyPart(pointZero, SnakeFactoryForTests.baseSnake());
		assertTrue(bodyPart.getPoint().equals(pointZero));
		assertFalse(bodyPart.getDuration().isPresent());
		
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		bodyPart.onCollision(testSnake, 0L);
		assertEquals(testSnake.getEffects().size(), 0);
		
	}
	
	@Test
	public void testAllCollisions() {
		Snake tmp;
		
		//base snake
		tmp = SnakeFactoryForTests.baseSnake();
		noOneSurvives(tmp, tmp);
		onlyASurvives(SnakeFactoryForTests.baseSnake(), SnakeFactoryForTests.baseSnake());
		onlyBSurvives(SnakeFactoryForTests.baseSnake(), SnakeFactoryForTests.godSnake());
		bothSurvive(SnakeFactoryForTests.baseSnake(), SnakeFactoryForTests.ghostSnake());
		onlyASurvives(SnakeFactoryForTests.baseSnake(), SnakeFactoryForTests.springSnake());
		
		//god snake
		tmp = SnakeFactoryForTests.godSnake();
		bothSurvive(tmp, tmp);
		onlyASurvives(SnakeFactoryForTests.godSnake(), SnakeFactoryForTests.baseSnake());
		bothSurvive(SnakeFactoryForTests.godSnake(), SnakeFactoryForTests.godSnake());
		bothSurvive(SnakeFactoryForTests.godSnake(), SnakeFactoryForTests.ghostSnake());
		onlyASurvives(SnakeFactoryForTests.godSnake(), SnakeFactoryForTests.springSnake());
		
		//ghost snake
		tmp = SnakeFactoryForTests.ghostSnake();
		bothSurvive(tmp, tmp);
		bothSurvive(SnakeFactoryForTests.ghostSnake(), SnakeFactoryForTests.baseSnake());
		bothSurvive(SnakeFactoryForTests.ghostSnake(), SnakeFactoryForTests.godSnake());
		bothSurvive(SnakeFactoryForTests.ghostSnake(), SnakeFactoryForTests.ghostSnake());
		bothSurvive(SnakeFactoryForTests.ghostSnake(), SnakeFactoryForTests.springSnake());
		
		//spring snake
		tmp = SnakeFactoryForTests.springSnake();
		noOneSurvives(tmp, tmp);
		onlyASurvives(SnakeFactoryForTests.springSnake(), SnakeFactoryForTests.baseSnake());
		onlyBSurvives(SnakeFactoryForTests.springSnake(), SnakeFactoryForTests.godSnake());
		bothSurvive(SnakeFactoryForTests.springSnake(), SnakeFactoryForTests.ghostSnake());
		onlyASurvives(SnakeFactoryForTests.springSnake(), SnakeFactoryForTests.springSnake());
		
	}
	
	private void collision(Snake SnakeA, Snake SnakeB) {
		assertTrue(SnakeA.isAlive());
		assertTrue(SnakeB.isAlive());
		bodyPart = ItemFactory.createBodyPart(pointZero, SnakeA);
		bodyPart.onCollision(SnakeB, 0L);
	}
	
	private void bothSurvive(Snake SnakeA, Snake SnakeB) {
		collision(SnakeA, SnakeB);
		assertTrue(SnakeA.isAlive());
		assertTrue(SnakeB.isAlive());
	}
	
	private void onlyASurvives(Snake SnakeA, Snake SnakeB) {
		collision(SnakeA, SnakeB);
		assertTrue(SnakeA.isAlive());
		assertFalse(SnakeB.isAlive());
	}
	
	private void onlyBSurvives(Snake SnakeA, Snake SnakeB) {
		collision(SnakeA, SnakeB);
		assertFalse(SnakeA.isAlive());
		assertTrue(SnakeB.isAlive());
	}
	
	private void noOneSurvives(Snake SnakeA, Snake SnakeB) {
		collision(SnakeA, SnakeB);
		assertFalse(SnakeA.isAlive());
		assertFalse(SnakeB.isAlive());
	}
	
}
