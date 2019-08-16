package implementation.model.game.items;

import static org.junit.Assert.assertEquals;
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
 * Test regarding Item holding DoublePoints effect.
 * @see Item
 * @see DoublePoints
 * @see Effect
 * @author Alessandro Talmi
 */
public class DoublePointsTest {

    private Item doublePoints;
    private Point pointZero = new Point(0, 0);

    /**
     * Test double points's instantaneous effect.
     */
    @Test
    public void testInstantaneousEffect() {
        Field field = new FieldImpl(new Point(10, 10));
        ItemFactory itemFactory = new ItemFactory(field);
        doublePoints = itemFactory.createItem(pointZero, DoublePoints.class, Optional.empty(), Optional.empty());
        Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertTrue(testSnake.getPlayer().getScoreMultiplier() == 1.0);
        assertEquals(testSnake.getPlayer().getScore(), 0);
        AppleTest.collide(doublePoints, testSnake);
        assertTrue(testSnake.getPlayer().getScoreMultiplier() == 1.0);
        assertEquals(testSnake.getPlayer().getScore(), 0);
    }

    /*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/

    /**
     * Test double points's lasting effect.
     */
    @SuppressWarnings("deprecation")
    @Test 
    public void testLastingEffect() {
        final long effectDuration = 10;
        Field field = new FieldImpl(new Point(10, 10));
        ItemFactory itemFactory = new ItemFactory(field);
        doublePoints = itemFactory.createItem(pointZero, DoublePoints.class, Optional.empty(), Optional.of(effectDuration));
        Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertEquals(testSnake.getPlayer().getScore(), 0);
        AppleTest.collide(doublePoints, testSnake);
        assertEquals(testSnake.getPlayer().getScore(), 0);
        assertEquals(testSnake.getEffects().size(), 1);
        assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration));
        doublePoints = itemFactory.createItem(pointZero, DoublePoints.class, Optional.empty(), Optional.of(effectDuration));
        AppleTest.collide(doublePoints, testSnake);
        assertEquals(testSnake.getPlayer().getScore(), 0);
        assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(2 * effectDuration));
        assertTrue(testSnake.getPlayer().getScoreMultiplier() == 1.0);
        Thread t = new Thread(testSnake);
        t.start();
        try {
            Thread.sleep(effectDuration);
            assertTrue(testSnake.getPlayer().getScoreMultiplier() == 2.0);
            Thread.sleep(2 * effectDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertTrue(testSnake.getPlayer().getScoreMultiplier() == 1.0);
    }

}
