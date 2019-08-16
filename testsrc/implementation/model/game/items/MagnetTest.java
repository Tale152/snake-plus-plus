package implementation.model.game.items;

import static org.junit.Assert.assertEquals;

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
 * Test regarding Item holding Magnet effect.
 * @see Item
 * @see Magnet
 * @see Effect
 */
public class MagnetTest {

    private Item magnet;
    private final Point pointZero = new Point(0, 0);

    /**
     * Test magnet's instantaneous effect.
     */
    @Test
    public void testInstantaneousEffect() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        magnet = itemFactory.createItem(pointZero, Magnet.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertEquals("checking that pickup radius is one",
                testSnake.getProperties().getPickupProperty().getPickupRadius(), 1);
        AppleTest.collide(magnet, testSnake);
        assertEquals("checking that pickup radius is still one",
                testSnake.getProperties().getPickupProperty().getPickupRadius(), 1);
    }

    /*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/

    /**
     * Test magnet's lasting effect.
     */
    @SuppressWarnings("deprecation")
    @Test 
    public void testLastingEffect() {
        final long effectDuration = 10;
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        magnet = itemFactory.createItem(pointZero, Magnet.class, Optional.empty(), Optional.of(effectDuration));
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        AppleTest.collide(magnet, testSnake);
        assertEquals("checking that pickup radius is one",
                testSnake.getProperties().getPickupProperty().getPickupRadius(), 1);
        assertEquals("checking that active effect duration is equal to effectDuration", 
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration));
        magnet = itemFactory.createItem(pointZero, Magnet.class, Optional.empty(), Optional.of(effectDuration));
        AppleTest.collide(magnet, testSnake);
        assertEquals("checking that active effect duration has doubled",
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration * 2));
        assertEquals("checking that pickup radius is one",
                testSnake.getProperties().getPickupProperty().getPickupRadius(), 1);
        final Thread t = new Thread(testSnake);
        t.start();
        try {
            Thread.sleep(effectDuration);
            assertEquals("checking that pickup radius is now 2",
                    testSnake.getProperties().getPickupProperty().getPickupRadius(), 2);
            Thread.sleep(effectDuration * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertEquals("checking that pickup radius is back to 1",
                testSnake.getProperties().getPickupProperty().getPickupRadius(), 1);
    }

}
