package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;

public class WallTest {

	private Item wall;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInitWall() {
		
		try{
            wall = ItemFactory.createWall(null, Optional.empty());
            fail("Wall's point cannot be null");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
		
		try{
            wall = ItemFactory.createWall(pointZero, null);
            fail("Wall's expirationTime cannot be null");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
		
		wall = ItemFactory.createWall(pointZero, Optional.empty());
		assertEquals(wall.getDuration(), Optional.empty());
		wall = ItemFactory.createWall(pointZero, Optional.of(100L));
		assertEquals(wall.getDuration(), Optional.of(100L));
		
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		wall.onCollision(testSnake, 300L);
		assertEquals(testSnake.getEffects().size(),0);
		
	}
	
	@Test
	public void testCollision(){
		assertFalse(survives(SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))))));
		assertTrue(survives(SnakeFactoryForTests.godSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))))));
		assertTrue(survives(SnakeFactoryForTests.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))))));
		assertTrue(survives(SnakeFactoryForTests.springSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))))));
	}
	
	private boolean survives(Snake snake) {
		wall = ItemFactory.createWall(pointZero, Optional.empty());
		wall.onCollision(snake, 0L);
		return snake.isAlive();
	}
	
}
