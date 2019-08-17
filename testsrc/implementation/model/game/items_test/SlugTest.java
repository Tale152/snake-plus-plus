package implementation.model.game.items_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;

import design.model.game.Field;
import design.model.game.Item;
import design.model.game.Snake;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.items.ItemFactory;
import implementation.model.game.items.Slug;

/**
 * Test regarding Item holding Slug effect.
 * @see Item
 * @see Slug
 * @see Effect
 */
public class SlugTest {

    private Item slug;
    private final Point pointZero = new Point(0, 0);

    /**
     * Test slug's instantaneous effect.
     */
    @Test
    public void testInstantaneousEffect() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        slug = itemFactory.createItem(pointZero, Slug.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertSame("checking that speed multiplier is 1",
                testSnake.getProperties().getSpeedProperty().getSpeedMultiplier(), 1.0);
        AppleTest.collide(slug, testSnake);
        assertSame("checking that speed multiplier is still 1",
                testSnake.getProperties().getSpeedProperty().getSpeedMultiplier(), 1.0);
    }

    /*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/

    /**
     * Test slug's lasting effect.
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testLastingEffect() {
        final long effectDuration = 10;
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        slug = itemFactory.createItem(pointZero, Slug.class, Optional.empty(), Optional.of(effectDuration));
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertSame("checking that speed multiplier is 1",
                testSnake.getProperties().getSpeedProperty().getSpeedMultiplier(), 1.0);
        AppleTest.collide(slug, testSnake);
        assertEquals("checking that snake has only one effect active", testSnake.getEffects().size(), 1);
        assertEquals("checking that active effect duration is equal to effectDuration", 
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration));
        slug = itemFactory.createItem(pointZero, Slug.class, Optional.empty(), Optional.of(effectDuration));
        AppleTest.collide(slug, testSnake);
        assertEquals("check that snake's score is zero",
                testSnake.getPlayer().getScore(), 0);
        assertEquals("check that active effect duration's is doubled",
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration * 2));

        final Thread t = new Thread(testSnake);
        t.start();
        try {
            Thread.sleep(effectDuration);
            assertSame("checking that speed multiplier is now 2",
                    testSnake.getProperties().getSpeedProperty().getSpeedMultiplier(), 2.0);
            Thread.sleep(effectDuration  * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertSame("checking that speed multiplier is back to one",
                testSnake.getProperties().getSpeedProperty().getSpeedMultiplier(), 1.0);
    }

}
