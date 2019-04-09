package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            bodyPart = ItemFactory.createBodyPart(null, SnakeFactoryForTests.baseSnake(new ArrayList<Point>(null)));
            fail("Body part's point cannot ben null");
        } catch (NullPointerException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
		
		try{
            bodyPart = ItemFactory.createBodyPart(null, SnakeFactoryForTests.baseSnake(new ArrayList<Point>()));
            fail("Body part's point cannot ben null");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
		
		bodyPart = ItemFactory.createBodyPart(pointZero, SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0)))));
		assertTrue(bodyPart.getPoint().equals(pointZero));
		assertFalse(bodyPart.getDuration().isPresent());
		
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		bodyPart.onCollision(testSnake, 0L);
		assertEquals(testSnake.getEffects().size(), 0);
		
	}
	
	private List<Point> pointZero(){
		return new ArrayList<Point>(Arrays.asList(new Point(0,0)));
	}
	
	@Test
	public void testAllCollisions() {
		Snake tmp;
		
		//base snake
		tmp = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		noOneSurvives(tmp, tmp);
		onlyASurvives(SnakeFactoryForTests.baseSnake(pointZero()), SnakeFactoryForTests.baseSnake(pointZero()));
		onlyBSurvives(SnakeFactoryForTests.baseSnake(pointZero()), SnakeFactoryForTests.godSnake(pointZero()));
		bothSurvive(SnakeFactoryForTests.baseSnake(pointZero()), SnakeFactoryForTests.ghostSnake(pointZero()));
		onlyASurvives(SnakeFactoryForTests.baseSnake(pointZero()), SnakeFactoryForTests.springSnake(pointZero()));
		
		//god snake
		tmp = SnakeFactoryForTests.godSnake(pointZero());
		bothSurvive(tmp, tmp);
		onlyASurvives(SnakeFactoryForTests.godSnake(pointZero()), SnakeFactoryForTests.baseSnake(pointZero()));
		bothSurvive(SnakeFactoryForTests.godSnake(pointZero()), SnakeFactoryForTests.godSnake(pointZero()));
		bothSurvive(SnakeFactoryForTests.godSnake(pointZero()), SnakeFactoryForTests.ghostSnake(pointZero()));
		onlyASurvives(SnakeFactoryForTests.godSnake(pointZero()), SnakeFactoryForTests.springSnake(pointZero()));
		
		//ghost snake
		tmp = SnakeFactoryForTests.ghostSnake(pointZero());
		bothSurvive(tmp, tmp);
		bothSurvive(SnakeFactoryForTests.ghostSnake(pointZero()), SnakeFactoryForTests.baseSnake(pointZero()));
		bothSurvive(SnakeFactoryForTests.ghostSnake(pointZero()), SnakeFactoryForTests.godSnake(pointZero()));
		bothSurvive(SnakeFactoryForTests.ghostSnake(pointZero()), SnakeFactoryForTests.ghostSnake(pointZero()));
		bothSurvive(SnakeFactoryForTests.ghostSnake(pointZero()), SnakeFactoryForTests.springSnake(pointZero()));
		
		//spring snake
		tmp = SnakeFactoryForTests.springSnake(pointZero());
		noOneSurvives(tmp, tmp);
		onlyASurvives(SnakeFactoryForTests.springSnake(pointZero()), SnakeFactoryForTests.baseSnake(pointZero()));
		onlyBSurvives(SnakeFactoryForTests.springSnake(pointZero()), SnakeFactoryForTests.godSnake(pointZero()));
		bothSurvive(SnakeFactoryForTests.springSnake(pointZero()), SnakeFactoryForTests.ghostSnake(pointZero()));
		onlyASurvives(SnakeFactoryForTests.springSnake(pointZero()), SnakeFactoryForTests.springSnake(pointZero()));
		
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
