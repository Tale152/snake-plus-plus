package implementation.model.game.items_test;

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
import implementation.model.game.items.GodMode;
import implementation.model.game.items.ItemFactory;

/**
 * Test regarding Item holding GodMode effect.
 * @see Item
 * @see GodMode
 * @see Effect
 */
public class GodModeTest {

    private Item god;
    private final Point pointZero = new Point(0, 0);

    /**
     * Test god mode's instantaneous effect.
     */
    @Test
    public void testInstantaneousEffect() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        god = itemFactory.createItem(pointZero, GodMode.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertFalse("checking that snake is not invincible",
                testSnake.getProperties().getCollisionProperty().isInvincible());
        AppleTest.collide(god, testSnake);
        assertFalse("checking that snake is still not invincible",
                testSnake.getProperties().getCollisionProperty().isInvincible());
    }

    /*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/

    /**
     * Test god mode's lasting effect.
     */
    @SuppressWarnings("deprecation")
    @Test 
    public void testLastingEffect() {
        final long effectDuration = 10;
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        god = itemFactory.createItem(pointZero, GodMode.class, Optional.empty(), Optional.of(effectDuration));
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        AppleTest.collide(god, testSnake);
        assertEquals("checking that snake's current length is one",
                testSnake.getEffects().size(), 1);
        assertEquals("checking that active effect duration equals to effectDuration",
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration));
        god = itemFactory.createItem(pointZero, GodMode.class, Optional.empty(), Optional.of(effectDuration));
        AppleTest.collide(god, testSnake);
        assertEquals("checking that active effect duration has doubled",
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration * 2));
        assertFalse("checking that snake is still not invincible",
                testSnake.getProperties().getCollisionProperty().isInvincible());
        final Thread t = new Thread(testSnake);
        t.start();
        try {
            Thread.sleep(effectDuration);
            assertTrue("check that snake is currently invincible",
                    testSnake.getProperties().getCollisionProperty().isInvincible());
            Thread.sleep(effectDuration * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertFalse("check that snake has returned being not invincible",
                testSnake.getProperties().getCollisionProperty().isInvincible());
    }
}
