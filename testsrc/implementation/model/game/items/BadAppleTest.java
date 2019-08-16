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
 * Test regarding Item holding BadApple effect.
 * @see Item
 * @see BadApple
 * @see Effect
 */
public class BadAppleTest {

    private Item badApple;
    private final Point pointZero = new Point(0, 0);
    private static final int SCORE = 100;
    private static final int LENGTH_TO_ADD = 5;

    /**
     * Test bad apple's instantaneous effect.
     */
    @Test
    public void testInstantaneousEffect() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        badApple = itemFactory.createItem(pointZero, BadApple.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        testSnake.getPlayer().addScore(SCORE);
        testSnake.getProperties().getLengthProperty().lengthen(LENGTH_TO_ADD);
        assertEquals("checking snake's score equals to SCORE", testSnake.getPlayer().getScore(), SCORE);
        assertEquals("checking snake's length equals to 1 plus LENGTH_TO_ADD", 
                testSnake.getProperties().getLengthProperty().getLength(), LENGTH_TO_ADD + 1);
        AppleTest.collide(badApple, testSnake);
        assertEquals("checking that snake's length halved", testSnake.getProperties().getLengthProperty().getLength(), (LENGTH_TO_ADD + 1) / 2);
        assertEquals("checking that snake's score halved", testSnake.getPlayer().getScore(), SCORE / 2);
    }

    /**
     * Test bad apple's instantaneous effect while collision occurs with a snake under ghost's effect.
     */
    @Test
    public void testInstantaneousEffectOnGhost() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        badApple = itemFactory.createItem(pointZero, BadApple.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        testSnake.getPlayer().addScore(SCORE);
        testSnake.getProperties().getLengthProperty().lengthen(LENGTH_TO_ADD);
        assertEquals("checking snake's score equals to SCORE", testSnake.getPlayer().getScore(), SCORE);
        assertEquals("checking snake's length equals to 1 plus LENGTH_TO_ADD", 
                testSnake.getProperties().getLengthProperty().getLength(), LENGTH_TO_ADD + 1);
        AppleTest.collide(badApple, testSnake);
        assertEquals("checking that snake's length didn't change", 
                testSnake.getProperties().getLengthProperty().getLength(), LENGTH_TO_ADD + 1);
        assertEquals("checking that snake's score didn't change", 
                testSnake.getPlayer().getScore(), SCORE);
    }

    /**
     * Test bad apple's lasting effect.
     */
    @SuppressWarnings("deprecation")
    @Test 
    public void testLastingEffect() {
        final long effectDuration = 10;
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        badApple = itemFactory.createItem(pointZero, BadApple.class, Optional.empty(), Optional.of(effectDuration));
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        testSnake.getPlayer().addScore(SCORE);
        testSnake.getProperties().getLengthProperty().lengthen(LENGTH_TO_ADD);
        assertEquals("checking snake's score equals to SCORE", testSnake.getPlayer().getScore(), SCORE);
        assertEquals("checking snake's length equals to 1 plus LENGTH_TO_ADD", 
                testSnake.getProperties().getLengthProperty().getLength(), LENGTH_TO_ADD + 1);
        AppleTest.collide(badApple, testSnake);
        assertEquals("checking that snake's length halved",
                testSnake.getProperties().getLengthProperty().getLength(), (LENGTH_TO_ADD + 1) / 2);
        assertEquals("checking that snake's score halved",
                testSnake.getPlayer().getScore(), SCORE / 2);
        badApple = itemFactory.createItem(pointZero, BadApple.class, Optional.empty(), Optional.of(effectDuration));
        AppleTest.collide(badApple, testSnake);
        assertEquals("checking that snake's lenght was halved another time",
                testSnake.getProperties().getLengthProperty().getLength(), 2);
        assertEquals("checking that snake's score was halved another time",
                testSnake.getPlayer().getScore(), SCORE / 4);
        final Thread t = new Thread(testSnake);
        t.start();
        try {
            Thread.sleep(effectDuration * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertEquals("checking that snake's length is restored", 
                testSnake.getProperties().getLengthProperty().getLength(), LENGTH_TO_ADD + 1);
        assertEquals("checking that snake's score was not restored",
                testSnake.getPlayer().getScore(), SCORE / 4);
    }

}
