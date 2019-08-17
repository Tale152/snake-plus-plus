package implementation.model.game.snake_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        final PickupProperty pickup = SnakeComponentsFactoryUtils.createPickupProperty();
        assertEquals("Check if snake basic pick up radius is 1", pickup.getPickupRadius(), 1);
        pickup.setPickupRadius(2);
        assertEquals("Check if snake pick up radius is 2", pickup.getPickupRadius(), 2);
        assertTrue("pickup range cannot be zero",
                checkSetPickupRadiusIllegalArgumenException(pickup, 0));
        assertTrue("pickup range cannot be negative",
                checkSetPickupRadiusIllegalArgumenException(pickup, -1));
    }

    private boolean checkSetPickupRadiusIllegalArgumenException(final PickupProperty pickup, final int radius) {
        try {
            pickup.setPickupRadius(radius);
        } catch (IllegalArgumentException e) {
            return true;
        } 
        return false;
    }
}
