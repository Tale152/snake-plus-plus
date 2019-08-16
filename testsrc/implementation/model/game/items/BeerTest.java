package implementation.model.game.items;

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

/**
 * Test regarding Item holding Beer effect.
 * @see Item
 * @see Beer
 * @author Alessandro Talmi
 */
public class BeerTest {

    private Item beer;
    private Point pointZero = new Point(0, 0);

    /**
     * Test beer's instantaneous effect.
     */
    @Test
    public void testInstantaneousEffect() {
        Field field = new FieldImpl(new Point(10, 10));
        ItemFactory itemFactory = new ItemFactory(field);
        beer = itemFactory.createItem(pointZero, Beer.class, Optional.empty(), Optional.empty());
        Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertEquals(testSnake.getProperties().getDirectionProperty().getDirection(), Direction.RIGHT);
        AppleTest.collide(beer, testSnake);
        assertTrue(testSnake.getProperties().getDirectionProperty().getDirection().equals(Direction.UP) 
                || testSnake.getProperties().getDirectionProperty().getDirection().equals(Direction.DOWN));
        assertFalse(testSnake.getProperties().getDirectionProperty().isDirectionReversed());
    }

    /**
     * Test beer's instantaneous effect while collision occurs with a snake under ghost's effect.
     */
    @Test
    public void testInstantaneousEffectOnGhost() {
        Field field = new FieldImpl(new Point(10, 10));
        ItemFactory itemFactory = new ItemFactory(field);
        beer = itemFactory.createItem(pointZero, Beer.class, Optional.empty(), Optional.empty());
        Snake testSnake = SnakeFactoryForTests.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        Direction current = testSnake.getProperties().getDirectionProperty().getDirection();
        AppleTest.collide(beer, testSnake);
        assertEquals(testSnake.getProperties().getDirectionProperty().getDirection(), current);
        assertFalse(testSnake.getProperties().getDirectionProperty().isDirectionReversed());
    }

    /**
     * Test beer's lasting effect.
     */
    @SuppressWarnings("deprecation")
    @Test 
    public void testLastingEffect() {
        final long effectDuration = 10;
        Field field = new FieldImpl(new Point(10, 10));
        ItemFactory itemFactory = new ItemFactory(field);
        beer = itemFactory.createItem(pointZero, Beer.class, Optional.empty(), Optional.of(effectDuration));
        Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertFalse(testSnake.getProperties().getDirectionProperty().isDirectionReversed());
        AppleTest.collide(beer, testSnake);
        assertFalse(Direction.RIGHT.equals(testSnake.getProperties().getDirectionProperty().getDirection()));
        assertEquals(testSnake.getEffects().size(), 1);
        assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration));
        beer = itemFactory.createItem(pointZero, Beer.class, Optional.empty(), Optional.of(effectDuration));
        AppleTest.collide(beer, testSnake);
        assertEquals(testSnake.getEffects().size(), 1);
        assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration * 2));
        assertTrue(testSnake.getProperties().getDirectionProperty().getDirection().equals(Direction.RIGHT) 
                || testSnake.getProperties().getDirectionProperty().getDirection().equals(Direction.LEFT));
        Thread t = new Thread(testSnake);
        t.start();
        try {
            Thread.sleep(effectDuration);
            assertTrue(testSnake.getProperties().getDirectionProperty().isDirectionReversed());
            Thread.sleep(effectDuration * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertFalse(testSnake.getProperties().getDirectionProperty().isDirectionReversed());
    }

}
