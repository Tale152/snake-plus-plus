package implementation.model.game.snake;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import design.model.game.PickupProperty;
/**
 * Tests regarding snake pick up properties.
 */
public class PickupPropertyTest {

    /**
     * Test that check if snake pick up radius is set correctly.
     */
    @Test
    public void testPickupProperty() {
        final PickupProperty pickup = SnakeComponentsFactoryForTest.createPickupProperty();
        assertEquals("Check if snake basic pick up radius is 1", pickup.getPickupRadius(), 1);
        pickup.setPickupRadius(2);
        assertEquals("Check if snake pick up radius is 2", pickup.getPickupRadius(), 2);
        try {
            pickup.setPickupRadius(0);
            fail("pickup range cannot be zero");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception");
        }
        try {
            pickup.setPickupRadius(-1);
            fail("pickup range cannot be negative");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception");
        }
    }
}
