package implementation.model.game.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import design.model.game.BodyPart;
import design.model.game.Field;
import design.model.game.Snake;
import implementation.model.game.field.FieldImpl;

/**
 * Test regarding body part.
 * @see BodyPart
 * @see BodyPartImpl
 * @see Effect
 * @author Alessandro Talmi
 */
public class BodyPartTest {

    private BodyPart bodyPart;
    private Point pointZero = new Point(0, 0);

    /**
     * Tests that body part is initiated correctly.
     */
    @Test
    public void testInitBodyPart() {
        Field field = new FieldImpl(new Point(10, 10));
        Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        bodyPart = new BodyPartImpl(pointZero, testSnake);
        assertTrue(bodyPart.getPoint().equals(pointZero));
        assertFalse(bodyPart.isHead());
        assertFalse(bodyPart.isBody());
        assertFalse(bodyPart.isTail());
        assertFalse(bodyPart.isCombinedOnTop());
        assertFalse(bodyPart.isCombinedOnRight());
        assertFalse(bodyPart.isCombinedOnBottom());
        assertFalse(bodyPart.isCombinedOnLeft());
        try {
            bodyPart.onCollision(testSnake);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        assertEquals(testSnake.getEffects().size(), 0);
    }

}
