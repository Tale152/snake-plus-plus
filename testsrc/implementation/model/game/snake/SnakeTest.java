package implementation.model.game.snake;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import design.model.game.Direction;
import design.model.game.Field;
import design.model.game.Item;
import design.model.game.PlayerNumber;
import design.model.game.Snake;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.items.Apple;
import implementation.model.game.items.BadApple;
import implementation.model.game.items.ItemFactory;

/**
 * Tests regarding snake behaviors.
 */
@SuppressWarnings("deprecation")
public class SnakeTest {

    private static final int FIVE_BODY_PART = 5;

    private Snake snake; 

    /**
     * Test if snake is initialized correctly.
     */
    @Test
    public void testInit() {
        snake = SnakeComponentsFactoryUtils.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
                1.0, new FieldImpl(new Point(1000, 1000)), new ArrayList<Point>(Arrays.asList(new Point(0, 0))));
        assertNotNull("Check if snake player is initialized", snake.getPlayer());
        assertNotNull("Check if snake properties are initialized", snake.getProperties());
    }

    /**
     * Test if snake moves correctly when he does not increase or decrease his length.
     */
    @Test
    public void testNormalMove() {
        snake = SnakeComponentsFactoryUtils.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
                1.0, new FieldImpl(new Point(1000, 1000)), new ArrayList<Point>(Arrays.asList(new Point(0, 0))));
        assertEquals("Check if snake length is 1", snake.getProperties().getLengthProperty().getLength(), 1);
        Thread t = new Thread(snake);
        t.start();
        while (true) {
            if (snake.getBodyParts().get(0).getPoint().getX() >= 3) {
                t.stop();
                break;
            }
        }
        snake.getBodyParts().get(0).getPoint().equals(new Point(0, 1));
        assertEquals("Chek if snake length is still 1 after one movement", snake.getProperties().getLengthProperty().getLength(), 1);

        final List<Point> tmp = new ArrayList<>(Arrays.asList(new Point(3, 0), new Point(2, 0), new Point(1, 0), new Point(0, 0))); //Point(3,0) is head
        snake = SnakeComponentsFactoryUtils.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
                1.0, new FieldImpl(new Point(1000, 1000)), tmp);
        assertEquals("Check if snake current length is 4", snake.getProperties().getLengthProperty().getLength(), 4);
        t = new Thread(snake);
        t.start();
        while (true) {
            if (snake.getBodyParts().get(0).getPoint().getX() >= FIVE_BODY_PART) {
                t.stop();
                break;
            }
        }
        snake.getBodyParts().get(1).getPoint().equals(new Point((int) snake.getBodyParts().get(0).getPoint().getX() - 1, 0));
        snake.getBodyParts().get(2).getPoint().equals(new Point((int) snake.getBodyParts().get(0).getPoint().getX() - 2, 0));
        snake.getBodyParts().get(3).getPoint().equals(new Point((int) snake.getBodyParts().get(0).getPoint().getX() - 3, 0));
        assertEquals("Check if snake length is still 4 after a movement", snake.getProperties().getLengthProperty().getLength(), 4); 
    }

    /**
     * Test if snake moves correctly when he increases his length.
     */
    @Test
    public void testLenghtenMove() {
        snake = SnakeComponentsFactoryUtils.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
                1.0, new FieldImpl(new Point(1000, 1000)), new ArrayList<Point>(Arrays.asList(new Point(0, 0))));
        snake.getProperties().getLengthProperty().lengthen(1);
        assertEquals("Check if snake length has increase and it is now 2", snake.getProperties().getLengthProperty().getLength(), 2);
        assertEquals("Check if the list of snake's body part size is 1", snake.getBodyParts().size(), 1);
        Thread t = new Thread(snake);
        t.start();
        while (true) {
            if (snake.getBodyParts().size() == 2) {
                t.stop();
                break;
            }
        }

        snake = SnakeComponentsFactoryUtils.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
                1.0, new FieldImpl(new Point(1000, 1000)), new ArrayList<Point>(Arrays.asList(new Point(0, 0))));
        snake.getProperties().getLengthProperty().lengthen(3);
        assertEquals("Check if snake length is 4, after increased his length by 3", 
                snake.getProperties().getLengthProperty().getLength(), 4);
        assertEquals("Check if the list of snake's body part is still 1 before the movement", snake.getBodyParts().size(), 1);
        t = new Thread(snake);
        t.start();
        while (true) {
            if (snake.getBodyParts().size() == 4) {
                t.stop();
                break;
            }
        }
        final int headX = (int) snake.getBodyParts().get(0).getPoint().getX();
        for (int i = 1; i < snake.getBodyParts().size(); ++i) {
            assertEquals("Check if all the body part are where they have to be, with the increasement",
                    headX - i, (int) snake.getBodyParts().get(i).getPoint().getX());
        }
    }

    /**
     * Test if snake moves correctly when he decrease his length.
     */
    @Test
    public void testShortenMove() {
        final List<Point> tmp = new ArrayList<>(Arrays.asList(new Point(3, 0), new Point(2, 0), new Point(1, 0), new Point(0, 0))); //Point(3,0) is head
        snake = SnakeComponentsFactoryUtils.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
                1.0, new FieldImpl(new Point(1000, 1000)), tmp);
        assertEquals("Check if snake length is actually 4", snake.getProperties().getLengthProperty().getLength(), 4);
        assertEquals("Check if snake body part list size is actually 4", snake.getBodyParts().size(), 4);
        snake.getProperties().getLengthProperty().shorten(2);
        assertEquals("Check if snake lenght is 2, after shorten his length by 2", snake.getProperties().getLengthProperty().getLength(), 2);
        assertEquals("Check if snake body part list is still 4 before the movement", snake.getBodyParts().size(), 4);
        final Thread t = new Thread(snake);
        t.start();
        while (true) {
            if (snake.getBodyParts().size() == 2) {
                t.stop();
                break;
            }
        }
        final int headX = (int) snake.getBodyParts().get(0).getPoint().getX();
        for (int i = 1; i < snake.getBodyParts().size(); ++i) {
            assertEquals("Check if all the body part are where they have to be, with the decreasement", 
                    headX - i, (int) snake.getBodyParts().get(i).getPoint().getX());
        }
    }

    /**
     * Test if snake can be killed correctly.
     */
    @Test
    public void testKill() {
        snake = SnakeComponentsFactoryUtils.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
                1.0, new FieldImpl(new Point(1000, 1000)), new ArrayList<Point>(Arrays.asList(new Point(0, 0))));
        assertTrue("Check if snake is currently alive", snake.isAlive());
        snake.kill();
        assertFalse("Check if snake is not alive after killing it", snake.isAlive());
    }

    /**
     * Test if snake can collide with an item correctly.
     */
    @Test
    public void testEffect() {
        final Field field = new FieldImpl(new Point(1000, 1000));
        snake = SnakeComponentsFactoryUtils.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
                1.0, field, new ArrayList<Point>(Arrays.asList(new Point(0, 0))));
        assertEquals("Check if the size of the list of snake effect is 0", snake.getEffects().size(), 0);

        final ItemFactory itemFactory = new ItemFactory(field);
        final Item apple = itemFactory.createItem(new Point(0, 0), Apple.class, Optional.empty(), Optional.of(10L));
        try {
            apple.onCollision(snake);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        assertEquals("Check if the size of snake effect list is 1, after colliding with an item", snake.getEffects().size(), 1);
        assertTrue("Check if the effect in the effect list is the same of the item collided",
                snake.getEffects().get(0).getClass().equals(apple.getEffectClass()));
        assertNotSame("Check if the effect list is a safe copie of the internal list", snake.getEffects(), snake.getEffects());
        final Item badApple = itemFactory.createItem(new Point(0, 0), BadApple.class, Optional.empty(), Optional.of(10L));
        try {
            badApple.onCollision(snake);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        assertEquals("Check if the size of snake's effect list is 2, after colliding with 2 items", snake.getEffects().size(), 2);
        assertTrue("Check if the second effect in the effect list is the same of the second item collided",
                snake.getEffects().get(1).getClass().equals(badApple.getEffectClass()));
    }

    /**
     * Test snake reverse property.
     */
    @Test
    public void testReverse() {
        final List<Point> tmp = new ArrayList<Point>(Arrays.asList(new Point(1, 0), new Point(0, 0))); //Point(1,0) is head
        snake = SnakeComponentsFactoryUtils.createSnake(PlayerNumber.PLAYER1, "p1", Direction.RIGHT, 100L, 
                1.0, new FieldImpl(new Point(1000, 1000)), tmp);
        assertEquals("Check if snake current direction is right", snake.getProperties().getDirectionProperty().getDirection(), Direction.RIGHT);
        snake.reverse();
        final Thread t = new Thread(snake);
        t.start();
        try {
            while (snake.getProperties().getDirectionProperty().getDirection().equals(Direction.RIGHT)) {
                Thread.sleep(10L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertEquals("Check if snake current direction is left, after activated the reverse property", 
                snake.getProperties().getDirectionProperty().getDirection(), Direction.LEFT);
        assertTrue("Check if snake bodypart are near each other", snake.getBodyParts().get(1).getPoint().getX() - snake.getBodyParts().get(0).getPoint().getX() <= 1);
    }

}
