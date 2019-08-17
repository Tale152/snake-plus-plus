package implementation.model.game.snake_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import design.model.game.SpeedProperty;

/**
 * Tests regarding snake speed properties.
 */
public class SpeedPropertyTest {

    private static final long DELTA = 900L;
    private static final double SPEED_MULTIPLIER = 1.5;

    /**
     * Test if snake speed is correctly initialize and if the speed can be increased
     * and decreased correctly.
     */
    @Test
    public void testSpeedProperty() {
        assertTrue("delta cannot be zero while initializing", 
                checkSpeedPropertyInitIllegalArgumenException(0, 1));
        assertTrue("delta cannot be negative while initializing", 
                checkSpeedPropertyInitIllegalArgumenException(-1, 1));
        assertTrue("multiplier cannot be negative while initializing", 
                checkSpeedPropertyInitIllegalArgumenException(100, -1));
        final SpeedProperty speed = SnakeComponentsFactoryUtils.createSpeedProperty(DELTA, 1);
        assertEquals("Check if the speed multiplier is correct", speed.getDeltaT(), DELTA);
        speed.setDeltaT(1000L);
        assertEquals("Check if the speed multiplier is correct after setting it", speed.getDeltaT(), 1000L);
        assertTrue("delta cannot be zero",
                checkSetDeltaTIllegalArgumenException(speed, 0L));
        assertTrue("delta cannot be negative",
                checkSetDeltaTIllegalArgumenException(speed, -1L));
        assertSame("Check if the speed multiplier is 1", speed.getSpeedMultiplier(), 1);
        speed.applySpeedMultiplier(0.5);
        assertSame("Check if the speed multiplier has been changed correctly", speed.getSpeedMultiplier(), SPEED_MULTIPLIER);
        speed.applySpeedMultiplier(-SPEED_MULTIPLIER);
        assertSame("Check if the speed multiplier is 0 when attempted to go negative", speed.getSpeedMultiplier(), 0);
        assertTrue("multiplier cannot be negative",
                checkApplySpeedMultiplierIllegalArgumenException(speed, -SPEED_MULTIPLIER));
    }

    private boolean checkSpeedPropertyInitIllegalArgumenException(final long delta, final double speedMultiplier) {
        try {
            @SuppressWarnings("unused")
            final SpeedProperty speed = SnakeComponentsFactoryUtils.createSpeedProperty(delta, speedMultiplier);
        } catch (IllegalArgumentException e) {
            return true;
        }
        return false;
    }

    private boolean checkSetDeltaTIllegalArgumenException(final SpeedProperty speed, final long deltaT) {
        try {
            speed.setDeltaT(deltaT);
        } catch (IllegalArgumentException e) {
            return true;
        } 
        return false;
    }

    private boolean checkApplySpeedMultiplierIllegalArgumenException(final SpeedProperty speed, final double multiplier) {
        try {
            speed.applySpeedMultiplier(multiplier);
        } catch (IllegalStateException e) {
            return true;
        }
        return false;
    }

}
