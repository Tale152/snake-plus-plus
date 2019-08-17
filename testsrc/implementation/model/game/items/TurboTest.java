package implementation.model.game.items;

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

/**
 * Test regarding Item holding Turbo effect.
 * @see Item
 * @see Turbo
 * @see Effect
 */
public class TurboTest {

    private Item turbo;
    private final Point pointZero = new Point(0, 0);

    /**
     * Test turbo's instantaneous effect.
     */
    @Test
    public void testInstantaneousEffect() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        turbo = itemFactory.createItem(pointZero, Turbo.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertSame("checks that speed multiplier is 1",
                testSnake.getProperties().getSpeedProperty().getSpeedMultiplier(), 1.0);
        AppleTest.collide(turbo, testSnake);
        assertSame("cheks that speed multiplier is still 1",
                testSnake.getProperties().getSpeedProperty().getSpeedMultiplier(), 1.0);
    }

    /*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/

    /**
     * Test turbo's lasting effect.
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testLastingEffect() {
        final long effectDuration = 10;
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        turbo = itemFactory.createItem(pointZero, Turbo.class, Optional.empty(), Optional.of(effectDuration));
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertSame("checks that speed multiplier is 1",
                testSnake.getProperties().getSpeedProperty().getSpeedMultiplier(), 1.0);
        AppleTest.collide(turbo, testSnake);
        assertEquals("checking that snake has one effect active",
                testSnake.getEffects().size(), 1);
        assertEquals("checking that active effect duration is equal to effectDuration", 
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration));
        turbo = itemFactory.createItem(pointZero, Turbo.class, Optional.empty(), Optional.of(effectDuration));
        AppleTest.collide(turbo, testSnake);
        assertEquals("checking that score is zero",
                testSnake.getPlayer().getScore(), 0);
        assertEquals("checking that active effect duration has doubled",
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration * 2));
        final Thread t = new Thread(testSnake);
        t.start();
        try {
            Thread.sleep(effectDuration);
            assertSame("checks that speed multiplier is now 0.5",
                    testSnake.getProperties().getSpeedProperty().getSpeedMultiplier(), 1.0);
            Thread.sleep(effectDuration * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertSame("checks that speed multiplier is back to 1",
                testSnake.getProperties().getSpeedProperty().getSpeedMultiplier(), 1.0);
    }

}
