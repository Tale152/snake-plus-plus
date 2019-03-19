package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
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
		assertEquals(wall.getDuration(), Optional.of(0L));
		
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		wall.onCollision(testSnake, 300L);
		assertEquals(testSnake.getEffects().size(),0);
		
	}
	
	@Test
	public void testCollision(){
		assertFalse(survives(SnakeFactoryForTests.baseSnake()));
		assertTrue(survives(SnakeFactoryForTests.godSnake()));
		assertTrue(survives(SnakeFactoryForTests.ghostSnake()));
		assertTrue(survives(SnakeFactoryForTests.springSnake()));
	}
	
	private boolean survives(Snake snake) {
		wall = ItemFactory.createWall(pointZero, Optional.empty());
		wall.onCollision(snake, 0L);
		return snake.isAlive();
	}
	
}
