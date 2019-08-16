package implementation.model.game.gameRules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import design.model.game.Field;
import design.model.game.LossConditions;
import design.model.game.Snake;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.gamerules.LossConditionsImpl;
import implementation.model.game.items.SnakeFactoryForTestsUtils;

/**
 * Tests regarding loss conditions. 
 */
public class LossConditionsTest {

    private LossConditions loss;

    /**
     * Used to test if loss condition are initialized correctly.
     */
    @Test
    public void testInit() {
        assertTrue("Check if a negative time throws an IllegalArgumentException",
                checkInitLossConditionIllegalArgumentException(-1L));
    }

    /**
     * Used to test if a loss condition based on the snake can be reached correctly.
     */
    @Test
    public void testAllSnakeDiedContitions() {
        loss = new LossConditionsImpl(true, Optional.empty(), true);
        final Field field = new FieldImpl(new Point(100, 100));
        final List<Snake> snakes = new ArrayList<>();
        final int nSnakes = 4;
        for (int i = 0; i < nSnakes; ++i) {
            snakes.add(SnakeFactoryForTestsUtils.baseSnake(
                    new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field));
        }
        for (int i = 0; i < nSnakes; ++i) {
            assertFalse("Check if the loss condition is not reached until at least one snake is alive", loss.checkSnakes(snakes));
            snakes.get(i).kill();
        }
        assertTrue("Check if the loss condition is reached when all the snakes are dead", loss.checkSnakes(snakes));
    }

    /**
     * Used to test if a loss condition base on the time can be reached correctly.
     */
    @Test
    public void testTimeConditions() {
        final long time = 10L;
        loss = new LossConditionsImpl(false, Optional.of(time), true);
        for (long i = 0; i < time; ++i) {
            assertFalse("Check if the loss condition is not reached if the time is lower and the time goes forward", loss.checkTime(i));
        }
        assertTrue("Check if the loss condition has been reached when the time is the expected one and the time goes forward",
                loss.checkTime(time));
        loss = new LossConditionsImpl(false, Optional.of(0L), false);
        for (long i = time; i > 0; --i) {
            assertFalse("Check if the loss condition is not reached if the time is bigger and the time goes backward", loss.checkTime(i));
        }
        assertTrue("Check if the loss condition has been reached when the time is the expected one and the time goes backward", loss.checkTime(0L));
    }

    private boolean checkInitLossConditionIllegalArgumentException(final long time) {
        try {
            loss = new LossConditionsImpl(false, Optional.of(time), false);
        } catch (IllegalArgumentException e) {
            return true;
        }
        return false;
    }
}
