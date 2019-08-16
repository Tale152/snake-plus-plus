package implementation.model.game.snake;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

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
        SpeedProperty speed;
        try {
            speed = SnakeComponentsFactoryForTest.createSpeedProperty(0, 1);
            fail("delta cannot be zero while initializing");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception");
        }
        try {
            speed = SnakeComponentsFactoryForTest.createSpeedProperty(-1, 1);
            fail("delta cannot be negative while initializing");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception");
        }
        try {
            speed = SnakeComponentsFactoryForTest.createSpeedProperty(100, -1);
            fail("multiplier cannot be negative while initializing");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception");
        }

        speed = SnakeComponentsFactoryForTest.createSpeedProperty(DELTA, 1);
        assertEquals("Check if the speed multiplier is correct", speed.getDeltaT(), DELTA);
        speed.setDeltaT(1000L);
        assertEquals("Check if the speed multiplier is correct after setting it", speed.getDeltaT(), 1000L);
        try {
            speed.setDeltaT(0L);
            fail("delta cannot be zero");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception");
        }
        try {
            speed.setDeltaT(-1L);
            fail("delta cannot be negative");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception");
        }

        assertSame("Check if the speed multiplier is 1", speed.getSpeedMultiplier(), 1);
        speed.applySpeedMultiplier(0.5);
        assertSame("Check if the speed multiplier has been changed correctly", speed.getSpeedMultiplier(), SPEED_MULTIPLIER);
        speed.applySpeedMultiplier(-SPEED_MULTIPLIER);
        assertSame("Check if the speed multiplier is 0 when attempted to go negative", speed.getSpeedMultiplier(), 0);
        try {
            speed.applySpeedMultiplier(-SPEED_MULTIPLIER);
            fail("multiplier cannot be negative");
        } catch (IllegalStateException e) {
        } catch (Exception e) {
            fail("wrong exception");
        }
    }

}
