package implementation.model.game.items_test;

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
import implementation.model.game.items.Apple;
import implementation.model.game.items.ItemFactory;

/**
 * Test regarding Item holding Apple effect.
 * @see Item
 * @see Apple
 * @see Effect
 */
public class AppleTest {

    private Item apple;
    private final Point pointZero = new Point(0, 0);

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
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        apple = itemFactory.createItem(pointZero, Apple.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertEquals("checking that the current score is 0", testSnake.getPlayer().getScore(), 0);
        assertEquals("checking that the current length is 1", testSnake.getProperties().getLengthProperty().getLength(), 1);
        collide(apple, testSnake);
        assertEquals("checking that the current length is 2", testSnake.getProperties().getLengthProperty().getLength(), 2);
        assertEquals("checking that the score increased accordingly to rules", 
                testSnake.getPlayer().getScore(), (int) (testSnake.getPlayer().getScoreMultiplier() * Apple.SCORE_INCREMENT));
    }

    /**
     * Test apple's instantaneous effect while collision occurs with a snake under ghost's effect.
     */
    @Test
    public void testInstantaneousEffectOnGhost() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        apple = itemFactory.createItem(pointZero, Apple.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertEquals("checking that the current score is 0", testSnake.getPlayer().getScore(), 0);
        assertEquals("checking that the current length is 1", testSnake.getProperties().getLengthProperty().getLength(), 1);
        collide(apple, testSnake);
        assertEquals("checking that the current score is still 0", testSnake.getPlayer().getScore(), 0);
        assertEquals("checking that the current length is stll 1", testSnake.getProperties().getLengthProperty().getLength(), 1);
    }

    /**
     * Test apple's lasting effect.
     */
    @SuppressWarnings("deprecation")
    @Test 
    public void testLastingEffect() {
        final long effectDuration = 10;
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        apple = itemFactory.createItem(pointZero, Apple.class, Optional.empty(), Optional.of(effectDuration));
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertEquals("checking that the current score is 0", testSnake.getPlayer().getScore(), 0);
        assertEquals("checking that the current length is 1", testSnake.getProperties().getLengthProperty().getLength(), 1);
        collide(apple, testSnake);
        assertEquals("checking that the current snake's length is increased", 
                testSnake.getProperties().getLengthProperty().getLength(), 1 + Apple.LENGTH_INCREMENT);
        assertEquals("checking that the current score is increased", testSnake.getPlayer().getScore(), Apple.SCORE_INCREMENT);
        assertEquals("checking that snake has only one effect active", testSnake.getEffects().size(), 1);
        assertEquals("checking that the effect duration is the same as expected", 
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration));
        apple = itemFactory.createItem(pointZero, Apple.class, Optional.empty(), Optional.of(effectDuration));
        collide(apple, testSnake);
        assertEquals("checking that the snake length is increased even more", 
                testSnake.getProperties().getLengthProperty().getLength(), 1 + Apple.LENGTH_INCREMENT * 2);
        assertEquals("checking that the snake's score is increased even more",
                testSnake.getPlayer().getScore(), Apple.SCORE_INCREMENT * 2);
        assertEquals("checking that snake has only one effect active", testSnake.getEffects().size(), 1);
        assertEquals("checking that the effect duration is doubled", 
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration * 2));
        final Thread t = new Thread(testSnake);
        t.start();
        try {
            Thread.sleep(effectDuration * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertEquals("checking that the snake's score is returned to 0", testSnake.getPlayer().getScore(), 0);
        assertEquals("checking that the snake's lenght is returned to 1", testSnake.getProperties().getLengthProperty().getLength(), 1);
    }

}
