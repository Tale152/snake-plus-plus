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
import implementation.model.game.items.DoublePoints;
import implementation.model.game.items.ItemFactory;

/**
 * Test regarding Item holding DoublePoints effect.
 * @see Item
 * @see DoublePoints
 * @see Effect
 */
public class DoublePointsTest {

    private Item doublePoints;
    private final Point pointZero = new Point(0, 0);

    /**
     * Test double points's instantaneous effect.
     */
    @Test
    public void testInstantaneousEffect() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        doublePoints = itemFactory.createItem(pointZero, DoublePoints.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertSame("check that multiplier is one", testSnake.getPlayer().getScoreMultiplier(), 1.0);
        assertEquals("check that score is zero", testSnake.getPlayer().getScore(), 0);
        AppleTest.collide(doublePoints, testSnake);
        assertSame("check that multiplier is still one", testSnake.getPlayer().getScoreMultiplier(), 1.0);
        assertEquals("check that score is still zero", testSnake.getPlayer().getScore(), 0);
    }

    /*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/

    /**
     * Test double points's lasting effect.
     */
    @SuppressWarnings("deprecation")
    @Test 
    public void testLastingEffect() {
        final long effectDuration = 10;
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        doublePoints = itemFactory.createItem(pointZero, DoublePoints.class, Optional.empty(), Optional.of(effectDuration));
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertEquals("checking snake's score equals 0", 
                testSnake.getPlayer().getScore(), 0);
        AppleTest.collide(doublePoints, testSnake);
        assertEquals("checking snake's score still equals 0", 
                testSnake.getPlayer().getScore(), 0);
        assertEquals("checking that snake has one effect active", 
                testSnake.getEffects().size(), 1);
        assertEquals("checking that active effect has duration equal to effectDuration",
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration));
        doublePoints = itemFactory.createItem(pointZero, DoublePoints.class, Optional.empty(), Optional.of(effectDuration));
        AppleTest.collide(doublePoints, testSnake);
        assertEquals("checking snake's score still equals 0", 
                testSnake.getPlayer().getScore(), 0);
        assertEquals("checking that active effect duration has doubled",
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(2 * effectDuration));
        assertSame("checking that currently score multiplier equals one", 
                testSnake.getPlayer().getScoreMultiplier(), 1.0);
        final Thread t = new Thread(testSnake);
        t.start();
        try {
            Thread.sleep(effectDuration);
            assertSame("checking that now score multiplier equal 2", 
                    testSnake.getPlayer().getScoreMultiplier(), 2.0);
            Thread.sleep(2 * effectDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertSame("checking that now score multiplier returned to 1",
                testSnake.getPlayer().getScoreMultiplier(), 1.0);
    }

}
