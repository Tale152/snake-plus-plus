package implementation.model.game.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;

import design.model.game.Field;
import design.model.game.Item;
import design.model.game.Snake;
import implementation.model.game.field.FieldImpl;

/**
 * Test regarding Item holding Apple effect.
 * @see Item
 * @see Apple
 * @see Effect
 * @author Alessandro Talmi
 */
public class AppleTest {

    private Item apple;
    private Point pointZero = new Point(0, 0);

    /**
     * Makes the item and the snake collide.
     * @param item to test
     * @param snake to collide with the item on test
     */
    public static void collide(final Item item, final Snake snake) {
        try {
            item.onCollision(snake);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            fail("something went very bad");
        }
    }

    /**
     * Test apple's instantaneous effect.
     */
    @Test
    public void testInstantaneousEffect() {
        Field field = new FieldImpl(new Point(10, 10));
        ItemFactory itemFactory = new ItemFactory(field);
        apple = itemFactory.createItem(pointZero, Apple.class, Optional.empty(), Optional.empty());
        Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertEquals(testSnake.getPlayer().getScore(), 0);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1);
        collide(apple, testSnake);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 2);
        assertEquals(testSnake.getPlayer().getScore(), (int) (testSnake.getPlayer().getScoreMultiplier() * 10));
    }

    /**
     * Test apple's instantaneous effect while collision occurs with a snake under ghost's effect.
     */
    @Test
    public void testInstantaneousEffectOnGhost() {
        Field field = new FieldImpl(new Point(10, 10));
        ItemFactory itemFactory = new ItemFactory(field);
        apple = itemFactory.createItem(pointZero, Apple.class, Optional.empty(), Optional.empty());
        Snake testSnake = SnakeFactoryForTests.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertEquals(testSnake.getPlayer().getScore(), 0);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1);
        collide(apple, testSnake);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1);
        assertEquals(testSnake.getPlayer().getScore(), 0);
    }

    /**
     * Test apple's lasting effect.
     */
    @SuppressWarnings("deprecation")
    @Test 
    public void testLastingEffect() {
        long effectDuration = 10;
        Field field = new FieldImpl(new Point(10, 10));
        ItemFactory itemFactory = new ItemFactory(field);
        apple = itemFactory.createItem(pointZero, Apple.class, Optional.empty(), Optional.of(effectDuration));
        Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertEquals(testSnake.getPlayer().getScore(), 0);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1);
        collide(apple, testSnake);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1 + Apple.LENGTH_INCREMENT);
        assertEquals(testSnake.getPlayer().getScore(), Apple.SCORE_INCREMENT);
        assertEquals(testSnake.getEffects().size(), 1);
        assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration));
        apple = itemFactory.createItem(pointZero, Apple.class, Optional.empty(), Optional.of(effectDuration));
        collide(apple, testSnake);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1 + Apple.LENGTH_INCREMENT * 2);
        assertEquals(testSnake.getPlayer().getScore(), Apple.SCORE_INCREMENT * 2);
        assertEquals(testSnake.getEffects().size(), 1);
        assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration * 2));
        Thread t = new Thread(testSnake);
        t.start();
        try {
            Thread.sleep(effectDuration * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertEquals(testSnake.getPlayer().getScore(), 0);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1);
    }

}
