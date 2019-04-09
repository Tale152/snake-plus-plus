package implementation.model.game.snake;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import design.model.game.SpeedProperty;

public class SpeedPropertyTest {

	@Test
	public void testSpeedProperty() {
		SpeedProperty speed;
		try{
			speed = SnakeComponentsFactoryForTest.createSpeedProperty(0, 1, 0);
            fail("delta cannot be zero while initializing");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			speed = SnakeComponentsFactoryForTest.createSpeedProperty(-1, 1, 0);
            fail("delta cannot be negative while initializing");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			speed = SnakeComponentsFactoryForTest.createSpeedProperty(100, -1, 0);
            fail("multiplier cannot be negative while initializing");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			speed = SnakeComponentsFactoryForTest.createSpeedProperty(100, 1, -1);
            fail("last update cannot be negative initializing");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		
		speed = SnakeComponentsFactoryForTest.createSpeedProperty(900L, 1, 0L);
		assertEquals(speed.getDeltaT(), 900L);
		speed.setDeltaT(1000L);
		assertEquals(speed.getDeltaT(), 1000L);
		try{
			speed.setDeltaT(0L);
            fail("delta cannot be zero");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			speed.setDeltaT(-1L);
            fail("delta cannot be negative");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		
		assertTrue(speed.getSpeedMultiplier() == 1);
		speed.applySpeedMultiplier(0.5);
		assertTrue(speed.getSpeedMultiplier() == 1.5);
		speed.applySpeedMultiplier(-1.5);
		assertTrue(speed.getSpeedMultiplier() == 0);
		try{
			speed.applySpeedMultiplier(-1.5);
            fail("multiplier cannot be negative");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		
		speed.applySpeedMultiplier(1.5);
		assertEquals(speed.getLastUpdate(), 0L);
		assertEquals(speed.getNextUpdate(), 1500L);
		speed.applySpeedMultiplier(0.5);
		assertEquals(speed.getNextUpdate(), 2000L);
		speed.setLastUpdate(2000L);
		assertEquals(speed.getNextUpdate(), 4000L);
		
	}
}
