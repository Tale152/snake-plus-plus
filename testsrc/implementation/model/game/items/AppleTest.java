package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;

public class AppleTest {

	private Item apple;
	private Point pointZero = new Point(0,0);
	@Test
	public void testInitApple() {
		
		try{
            apple = ItemFactory.createApple(null, Optional.empty(), Optional.empty());
            fail("Apple's point cannot be null");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
		
		try{
            apple = ItemFactory.createApple(pointZero, null, Optional.empty());
            fail("Apple's expiration time cannot be null");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
		
		try{
            apple = ItemFactory.createApple(pointZero, Optional.empty(), null);
            fail("Apple's effect duration cannot be null");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
		
		apple = ItemFactory.createApple(pointZero, Optional.empty(), Optional.empty());
		assertEquals(apple.getPoint(), pointZero);
		assertEquals(apple.getDuration(), Optional.empty());
		
		apple = ItemFactory.createApple(pointZero, Optional.empty(), Optional.of(0L));
		assertEquals(apple.getDuration(), Optional.of(0L));
	
	}
	
	@Test
	public void testInstantaneousEffect() {
		apple = ItemFactory.createApple(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		assertEquals(testSnake.getPlayer().getScore(), 0);
		assertEquals(testSnake.getProperties().getLength().getLength(), 1);
		apple.onCollision(testSnake, 0);
		assertEquals(testSnake.getProperties().getLength().getLength(), 1 + ItemFactory.LENGHT_INCREMENT);
		assertEquals(testSnake.getPlayer().getScore(), (int)(testSnake.getPlayer().getScoreMultiplier() * ItemFactory.SCORE));
	}
	
	@Test
	public void testInstantaneousEffectOnGhost() {
		apple = ItemFactory.createApple(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.ghostSnake();
		assertEquals(testSnake.getPlayer().getScore(), 0);
		assertEquals(testSnake.getProperties().getLength().getLength(), 1);
		apple.onCollision(testSnake, 0);
		assertEquals(testSnake.getProperties().getLength().getLength(), 1);
		assertEquals(testSnake.getPlayer().getScore(), 0);
	}
	
	@Test 
	public void testLastingEffect() {
		apple = ItemFactory.createApple(pointZero, Optional.empty(), Optional.of(100L));
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		assertEquals(testSnake.getPlayer().getScore(), 0);
		assertEquals(testSnake.getProperties().getLength().getLength(), 1);
		apple.onCollision(testSnake, 1000L);
		assertEquals(testSnake.getProperties().getLength().getLength(), 1 + ItemFactory.LENGHT_INCREMENT);
		assertEquals(testSnake.getPlayer().getScore(), (int)(testSnake.getPlayer().getScoreMultiplier() * ItemFactory.SCORE));
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime().get(), Optional.of(1100L));
		assertFalse(testSnake.getEffects().get(0).getExpirationTime().isPresent());
		apple = ItemFactory.createApple(pointZero, Optional.empty(), Optional.of(250L));
		apple.onCollision(testSnake, 1050L);
		assertEquals(testSnake.getProperties().getLength().getLength(), 1 + ItemFactory.LENGHT_INCREMENT * 2);
		assertEquals(testSnake.getPlayer().getScore(), (int)(testSnake.getPlayer().getScoreMultiplier() * ItemFactory.SCORE) * 2);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime().get(), Optional.of(1350L));
		testSnake.getEffects().get(0).effectEnd(testSnake);
		assertEquals(testSnake.getPlayer().getScore(), 0);
		assertEquals(testSnake.getProperties().getLength().getLength(), 1);
		assertEquals(testSnake.getEffects().size(),0);
	}
	
	@Test
	public void testLastingEffectOnGhost() {
		apple = ItemFactory.createApple(pointZero, Optional.empty(), Optional.of(100L));
		Snake testSnake = SnakeFactoryForTests.ghostSnake();
		assertEquals(testSnake.getPlayer().getScore(), 0);
		assertEquals(testSnake.getProperties().getLength().getLength(), 1);
		apple.onCollision(testSnake, 1000L);
		assertEquals(testSnake.getProperties().getLength().getLength(), 1);
		assertEquals(testSnake.getPlayer().getScore(), 0);
		assertEquals(testSnake.getEffects().size(),0);
	}
}
