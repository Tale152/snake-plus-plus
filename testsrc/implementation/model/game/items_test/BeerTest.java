package implementation.model.game.items_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;

import design.model.game.Direction;
import design.model.game.Field;
import design.model.game.Item;
import design.model.game.Snake;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.items.Beer;
import implementation.model.game.items.ItemFactory;

/**
 * Test regarding Item holding Beer effect.
 * @see Item
 * @see Beer
 * @see Effect
 */
public class BeerTest {

    private Item beer;
    private final Point pointZero = new Point(0, 0);

    /**
     * Test beer's instantaneous effect.
     */
    @Test
    public void testInstantaneousEffect() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        beer = itemFactory.createItem(pointZero, Beer.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertEquals("checking that current direction is right",
                testSnake.getProperties().getDirectionProperty().getDirection(), Direction.RIGHT);
        AppleTest.collide(beer, testSnake);
        assertTrue("checking that direction changed into either up or down",
                testSnake.getProperties().getDirectionProperty().getDirection().equals(Direction.UP) 
                || testSnake.getProperties().getDirectionProperty().getDirection().equals(Direction.DOWN));
        assertFalse("checking that direction reversed property now is false",
                testSnake.getProperties().getDirectionProperty().isDirectionReversed());
    }

    /**
     * Test beer's instantaneous effect while collision occurs with a snake under ghost's effect.
     */
    @Test
    public void testInstantaneousEffectOnGhost() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        beer = itemFactory.createItem(pointZero, Beer.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        final Direction current = testSnake.getProperties().getDirectionProperty().getDirection();
        AppleTest.collide(beer, testSnake);
        assertEquals("checking that direction didn't change",
                testSnake.getProperties().getDirectionProperty().getDirection(), current);
        assertFalse("checking that direction reversed property is false",
                testSnake.getProperties().getDirectionProperty().isDirectionReversed());
    }

    /**
     * Test beer's lasting effect.
     */
    @SuppressWarnings("deprecation")
    @Test 
    public void testLastingEffect() {
        final long effectDuration = 10;
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        beer = itemFactory.createItem(pointZero, Beer.class, Optional.empty(), Optional.of(effectDuration));
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertFalse("checking that direction reversed property is false",
                testSnake.getProperties().getDirectionProperty().isDirectionReversed());
        AppleTest.collide(beer, testSnake);
        assertEquals("checking that current direction is right",
                testSnake.getProperties().getDirectionProperty().getDirection(), Direction.RIGHT);
        assertEquals("checking that snake has only one effect active", testSnake.getEffects().size(), 1);
        assertEquals("checking that the effect duration is the same as expected", 
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration));
        beer = itemFactory.createItem(pointZero, Beer.class, Optional.empty(), Optional.of(effectDuration));
        AppleTest.collide(beer, testSnake);
        assertEquals("checking that snake has only one effect active", testSnake.getEffects().size(), 1);
        assertEquals("checking that the effect duration is doubled", 
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration * 2));
        assertTrue("checking that direction changed into either right or left",
                testSnake.getProperties().getDirectionProperty().getDirection().equals(Direction.RIGHT) 
                || testSnake.getProperties().getDirectionProperty().getDirection().equals(Direction.LEFT));
        final Thread t = new Thread(testSnake);
        t.start();
        try {
            Thread.sleep(effectDuration);
            assertTrue("checking that direction reversed property is true",
                    testSnake.getProperties().getDirectionProperty().isDirectionReversed());
            Thread.sleep(effectDuration * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertFalse("checking that direction reversed property is false",
                testSnake.getProperties().getDirectionProperty().isDirectionReversed());
    }

}
