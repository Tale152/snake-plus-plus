package implementation.model.game.items_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

import design.model.game.Field;
import design.model.game.Snake;
import design.model.game.Wall;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.items.WallImpl;

/**
 * Test regarding Wall.
 * @see Collidable
 * @see Wall
 */
public class WallTest {

    private Wall wall;
    private final Point pointZero = new Point(0, 0);

    /**
     * Tests initializing a wall.
     */
    @Test
    public void testInitWall() {
        final Field field = new FieldImpl(new Point(10, 10));
        wall = new WallImpl(pointZero);
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        try {
            wall.onCollision(testSnake);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        assertEquals("checks that snake has no effects active",
                testSnake.getEffects().size(), 0);
    }

    /**
     * Test various snake having different properties active while colliding with a wall.
     */
    @Test
    public void testCollision() {
        final Field field = new FieldImpl(new Point(10, 10));
        assertFalse("checks that a base snake does not survive colliding with a wall",
                survives(SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field)));
        assertTrue("checks that an invincible snake survives colliding with a wall",
                survives(SnakeFactoryForTestsUtils.godSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field)));
        assertTrue("checks that an intangible snake survives colliding with a wall",
                survives(SnakeFactoryForTestsUtils.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field)));
        assertTrue("checks that a spring snake survives colliding with a wall",
                survives(SnakeFactoryForTestsUtils.springSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field)));
    }

    private boolean survives(final Snake snake) {
        wall = new WallImpl(pointZero);
        try {
            wall.onCollision(snake);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return snake.isAlive();
    }

}
