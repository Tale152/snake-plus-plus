package implementation.model.game.snake;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import design.model.game.PickupProperty;

public class PickupPropertyTest {
	
	@Test
	public void testPickupProperty() {
		PickupProperty pickup;
		try{
			pickup = SnakeComponentsFactoryForTest.createPickupProperty(0);
            fail("initial pickup range cannot be zero");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			pickup = SnakeComponentsFactoryForTest.createPickupProperty(-1);
            fail("initial pickup range cannot be negative");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		pickup = SnakeComponentsFactoryForTest.createPickupProperty(1);
		assertEquals(pickup.getPickupRadius(), 1);
		pickup.setPickupRadius(2);
		assertEquals(pickup.getPickupRadius(), 2);
		try{
			pickup.setPickupRadius(0);
            fail("pickup range cannot be zero");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			pickup.setPickupRadius(-1);
            fail("pickup range cannot be negative");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
	}
}
