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
 * @author Alessandro Talmi
 */
public class BadAppleTest {

    private Item badApple;
    private Point pointZero = new Point(0, 0);
    private final int score = 100;
    private final int lenghtToAdd = 5;

    /**
     * Test bad apple's instantaneous effect.
     */
    @Test
    public void testInstantaneousEffect() {
        Field field = new FieldImpl(new Point(10, 10));
        ItemFactory itemFactory = new ItemFactory(field);
        badApple = itemFactory.createItem(pointZero, BadApple.class, Optional.empty(), Optional.empty());
        Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        testSnake.getPlayer().addScore(score);
        testSnake.getProperties().getLengthProperty().lengthen(lenghtToAdd);
        assertEquals(testSnake.getPlayer().getScore(), score);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), lenghtToAdd + 1);
        AppleTest.collide(badApple, testSnake);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), (lenghtToAdd + 1) / 2);
        assertEquals(testSnake.getPlayer().getScore(), score / 2);
    }

    /**
     * Test bad apple's instantaneous effect while collision occurs with a snake under ghost's effect.
     */
    @Test
    public void testInstantaneousEffectOnGhost() {
        Field field = new FieldImpl(new Point(10, 10));
        ItemFactory itemFactory = new ItemFactory(field);
        badApple = itemFactory.createItem(pointZero, BadApple.class, Optional.empty(), Optional.empty());
        Snake testSnake = SnakeFactoryForTests.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        testSnake.getPlayer().addScore(score);
        testSnake.getProperties().getLengthProperty().lengthen(lenghtToAdd);
        assertEquals(testSnake.getPlayer().getScore(), score);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), lenghtToAdd + 1);
        AppleTest.collide(badApple, testSnake);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), lenghtToAdd + 1);
        assertEquals(testSnake.getPlayer().getScore(), score);
    }

    /**
     * Test bad apple's lasting effect.
     */
    @SuppressWarnings("deprecation")
    @Test 
    public void testLastingEffect() {
        long effectDuration = 10;
        Field field = new FieldImpl(new Point(10, 10));
        ItemFactory itemFactory = new ItemFactory(field);
        badApple = itemFactory.createItem(pointZero, BadApple.class, Optional.empty(), Optional.of(effectDuration));
        Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        testSnake.getPlayer().addScore(score);
        assertEquals(testSnake.getPlayer().getScore(), score);
        testSnake.getProperties().getLengthProperty().lengthen(lenghtToAdd);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), lenghtToAdd + 1);
        AppleTest.collide(badApple, testSnake);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 3);
        assertEquals(testSnake.getPlayer().getScore(), score / 2);
        badApple = itemFactory.createItem(pointZero, BadApple.class, Optional.empty(), Optional.of(effectDuration));
        AppleTest.collide(badApple, testSnake);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 2);
        assertEquals(testSnake.getPlayer().getScore(), score / 4);
        Thread t = new Thread(testSnake);
        t.start();
        try {
            Thread.sleep(effectDuration * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), lenghtToAdd + 1);
        assertEquals(testSnake.getPlayer().getScore(), score / 4);
    }

}
