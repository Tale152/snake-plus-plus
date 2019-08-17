package implementation.model.game.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import design.model.game.Field;
import design.model.game.Item;
import design.model.game.Snake;
import implementation.model.game.field.FieldImpl;

/**
 * Test regarding Item holding GhostMode effect.
 * @see Item
 * @see GhostMode
 * @see Effect
 */
public class GhostModeTest {

    private Item ghost;
    private final Point pointZero = new Point(0, 0);

    /**
     * Test ghost mode's instantaneous effect.
     */
    @Test
    public void testInstantaneousEffect() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        ghost = itemFactory.createItem(pointZero, GhostMode.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertFalse("checking that snake is not intangible", 
                testSnake.getProperties().getCollisionProperty().isIntangible());
        AppleTest.collide(ghost, testSnake);
        assertFalse("checking that snake is still not inangible",
                testSnake.getProperties().getCollisionProperty().isIntangible());
    }

    /**
     * Test ghost mode's lasting effect.
     */
    @SuppressWarnings("deprecation")
    @Test 
    public void testLastingEffect() {
        final long effectDuration = 10L;
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        ghost = itemFactory.createItem(pointZero, GhostMode.class, Optional.empty(), Optional.of(effectDuration));
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        AppleTest.collide(ghost, testSnake);
        assertEquals("checking that snake has only one effect active", testSnake.getEffects().size(), 1);
        assertEquals("checking that active effect has a duration equal to effectDuration", 
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration));
        ghost = itemFactory.createItem(pointZero, GhostMode.class, Optional.empty(), Optional.of(effectDuration));
        AppleTest.collide(ghost, testSnake);
        assertEquals("checking that active effect duration has doubled",
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration * 2));
        assertFalse("checking that snake is currently not intangible", 
                testSnake.getProperties().getCollisionProperty().isIntangible());
        final Thread t = new Thread(testSnake);
        t.start();
        try {
            Thread.sleep(effectDuration);
            assertTrue("checking that snake is currently tangible",
                    testSnake.getProperties().getCollisionProperty().isIntangible());
            Thread.sleep(effectDuration * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertFalse("checking that snake is tangible again",
                testSnake.getProperties().getCollisionProperty().isIntangible());
    }
}
