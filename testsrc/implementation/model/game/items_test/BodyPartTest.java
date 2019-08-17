package implementation.model.game.items_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import design.model.game.BodyPart;
import design.model.game.Field;
import design.model.game.Snake;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.items.BodyPartImpl;

/**
 * Test regarding body part.
 * @see BodyPart
 * @see BodyPartImpl
 * @see Effect
 */
public class BodyPartTest {

    private final Point pointZero = new Point(0, 0);

    /**
     * Tests that body part is initiated correctly.
     */
    @Test
    public void testInitBodyPart() {
        final Field field = new FieldImpl(new Point(10, 10));
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        final BodyPart bodyPart = new BodyPartImpl(pointZero, testSnake);
        assertTrue("checking body part point", bodyPart.getPoint().equals(pointZero));
        assertFalse("checking is not head", bodyPart.isHead());
        assertFalse("checking is not body", bodyPart.isBody());
        assertFalse("checking is not tail", bodyPart.isTail());
        assertFalse("checking is not combined on top", bodyPart.isCombinedOnTop());
        assertFalse("checking is not combined on right", bodyPart.isCombinedOnRight());
        assertFalse("checking is not combined on bottom", bodyPart.isCombinedOnBottom());
        assertFalse("checking is not combined on left", bodyPart.isCombinedOnLeft());
        try {
            bodyPart.onCollision(testSnake);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        assertEquals("checking that snake has no active effects", testSnake.getEffects().size(), 0);
    }

}
