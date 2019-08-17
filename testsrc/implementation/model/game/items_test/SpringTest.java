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
import implementation.model.game.items.ItemFactory;
import implementation.model.game.items.Spring;

/**
 * Test regarding Item holding Spring effect.
 * @see Item
 * @see Spring
 * @see Effect
 */
public class SpringTest {

    private Item spring;
    private final Point pointZero = new Point(2, 0);

    /**
     * Test spring's instantaneous effect.
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testInstantaneousEffect() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        spring = itemFactory.createItem(pointZero, Spring.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(1, 0), new Point(0, 0))), field);
        assertEquals("checking that current snake's direction is right",
                testSnake.getProperties().getDirectionProperty().getDirection(), Direction.RIGHT);
        AppleTest.collide(spring, testSnake);
        final Thread t = new Thread(testSnake);
        t.start();
        try {
            while (testSnake.getProperties().getDirectionProperty().getDirection().equals(Direction.RIGHT)) {
                Thread.sleep(10L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertEquals("check that now snake has changed direction to left",
                testSnake.getProperties().getDirectionProperty().getDirection(), Direction.LEFT);
        assertTrue("check that now the head is at the left of the tail",
                testSnake.getBodyParts().get(1).getPoint().getX() - testSnake.getBodyParts().get(0).getPoint().getX() <= 1);
    }

    /**
     * Test spring's instantaneous effect while snake has intangible property active.
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testInstantaneousEffectOnGhost() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        spring = itemFactory.createItem(pointZero, Spring.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(1, 0), new Point(0, 0))), field);
        assertEquals("checking that current snake's direction is right",
                testSnake.getProperties().getDirectionProperty().getDirection(), Direction.RIGHT);
        AppleTest.collide(spring, testSnake);
        final Thread t = new Thread(testSnake);
        t.start();
        try {
            while (testSnake.getBodyParts().get(0).getPoint().equals(new Point(1, 0))) {
                Thread.sleep(10L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertEquals("checking that current snake's direction is still right",
                testSnake.getProperties().getDirectionProperty().getDirection(), Direction.RIGHT);
        assertTrue("check that the head is at the right of the tail",
                testSnake.getBodyParts().get(0).getPoint().getX() - testSnake.getBodyParts().get(1).getPoint().getX() <= 1);
    }

    /**
     * Test spring's lasting effect.
     */
    @SuppressWarnings("deprecation")
    @Test 
    public void testLastingEffect() {
        final long effectDuration = 10;
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        spring = itemFactory.createItem(pointZero, Spring.class, Optional.empty(), Optional.of(10L));
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(1, 0), new Point(0, 0))), field);
        assertEquals("checking that current snake's direction is right",
                testSnake.getProperties().getDirectionProperty().getDirection(), Direction.RIGHT);
        AppleTest.collide(spring, testSnake);
        assertEquals("checking that snake has only one effect active", testSnake.getEffects().size(), 1);
        assertEquals("check that active effect duration equal to effectDuration",
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration));
        spring = itemFactory.createItem(pointZero, Spring.class, Optional.empty(), Optional.of(effectDuration));
        AppleTest.collide(spring, testSnake);
        assertEquals("checking that snake has still only one effect active", testSnake.getEffects().size(), 1);
        assertEquals("check that effect duration has doubled",
                testSnake.getEffects().get(0).getEffectDuration(), Optional.of(effectDuration * 2));
        final Thread t = new Thread(testSnake);
        t.start();
        try {
            Thread.sleep(effectDuration);
            assertTrue("checks that spring property is now true",
                    testSnake.getProperties().getCollisionProperty().isSpring());
            Thread.sleep(effectDuration * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        assertFalse("checks that spring property is back to false",
                testSnake.getProperties().getCollisionProperty().isSpring());
    }

}
