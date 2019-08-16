package implementation.model.game.snake;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import design.model.game.LengthProperty;
/**
 * Tests regarding snake length properties.
 * @see LengthProperty
 */
public class LengthPropertyTest {

    private static final int FIVE_LENGTH = 5;
    private static final int SIX_LENGTH = 6;

    /**
     * Test if snake length is correct and if his length is shorten or increased correctly.
     */
    @Test
    public void testLengthProperty() {
        final LengthProperty length = SnakeComponentsFactoryForTest.createLengthProperty();
        assertEquals("Check if snake current length is 1", length.getLength(), 1);
        length.lengthen(4);
        assertEquals("Check if snake current length is 5, after lengthen by 4", length.getLength(), FIVE_LENGTH);
        length.lengthen(1);
        assertEquals("Check if snake current length is 6, after lengthen by 1", length.getLength(), SIX_LENGTH);
        length.lengthen(0);
        assertEquals("Check if snake current length is 6, after lengthen by 0", length.getLength(), SIX_LENGTH);
        try {
            length.lengthen(-1);
            fail("lengthen arg cannot be negative");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception");
        }
        length.shorten(0);
        assertEquals("Check if snake current length is 6 aftern shorten by 0", length.getLength(), SIX_LENGTH);
        try {
            length.shorten(-1);
            fail("shorten arg cannot be negative");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception");
        }
        length.shorten(1);
        assertEquals("Check if snake current length is 5, after shorten by 1", length.getLength(), FIVE_LENGTH);
        length.shorten(3);
        assertEquals("Check if snake current length is 2, after shorten by 3", length.getLength(), 2);
        length.shorten(3);
        assertEquals("Check if snake current length is 1, after shorten by 3", length.getLength(), 1);
    }

}
