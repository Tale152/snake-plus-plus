package implementation.model.game.snake;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import design.model.game.Direction;
import design.model.game.DirectionProperty;

/**
 * Tests regarding snake direction properties.
 * @see DirectionProperty
 */
public class DirectionPropertyTest {

    private boolean standardDirectionTestSupport(final Direction current, final Direction opposite,
            final Direction possible1, final Direction possible2) {
        DirectionProperty direction = SnakeComponentsFactoryForTest.createDirectionProperty(current);
        direction.setDirection(current);
        if (!direction.getDirection().equals(current)) {
            return false;
        }
        direction = SnakeComponentsFactoryForTest.createDirectionProperty(current);
        direction.setDirection(opposite);
        if (!direction.getDirection().equals(current)) {
            return false;
        }
        direction = SnakeComponentsFactoryForTest.createDirectionProperty(current);
        direction.setDirection(possible1);
        if (!direction.getDirection().equals(possible1)) {
            return false;
        }
        direction = SnakeComponentsFactoryForTest.createDirectionProperty(current);
        direction.setDirection(possible2);
        return direction.getDirection().equals(possible2);
    }

    private boolean oppositeDirectionTestSupport(final Direction current, final Direction opposite, final Direction possible1, final Direction possible2) {
        DirectionProperty direction = SnakeComponentsFactoryForTest.createDirectionProperty(current);
        direction.setReverseDirection(true);
        direction.setDirection(opposite);
        if (!direction.getDirection().equals(current)) {
            return false;
        }
        direction = SnakeComponentsFactoryForTest.createDirectionProperty(current);
        direction.setReverseDirection(true);
        direction.setDirection(current);
        if (!direction.getDirection().equals(current)) {
            return false;
        }
        direction = SnakeComponentsFactoryForTest.createDirectionProperty(current);
        direction.setReverseDirection(true);
        direction.setDirection(possible1);
        if (!direction.getDirection().equals(possible2)) {
            return false;
        }
        direction = SnakeComponentsFactoryForTest.createDirectionProperty(current);
        direction.setReverseDirection(true);
        direction.setDirection(possible2);
        return direction.getDirection().equals(possible1);
    }

    /**
     * Test if snake directions work how they suppose to do.
     */
    @Test
    public void testDirectionProperty() {
        DirectionProperty direction;

        direction = SnakeComponentsFactoryForTest.createDirectionProperty(Direction.DOWN);
        assertFalse("Check if reverse direction is not active", direction.isDirectionReversed());
        direction.setReverseDirection(true);
        assertTrue("Check if reverse direction has been activated", direction.isDirectionReversed());
        assertTrue("Check if the opposite and the possible direction are correct when current direction is UP",
                standardDirectionTestSupport(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));
        assertTrue("Check if the opposite and the possible direction are correct when current direction is DOWN",
                standardDirectionTestSupport(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT));
        assertTrue("Check if the opposite and the possible direction are correct when current direction is LEFT",
                standardDirectionTestSupport(Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN));
        assertTrue("Check if the opposite and the possible direction are correct when current direction is RIGHT",
                standardDirectionTestSupport(Direction.RIGHT, Direction.LEFT, Direction.UP, Direction.DOWN));
        assertTrue("Check if the opposite and the possible direction are correct when current direction is UP and reverse is active",
                oppositeDirectionTestSupport(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));
        assertTrue("Check if the opposite and the possible direction are correct when current direction is DOWN and reverse is active",
                oppositeDirectionTestSupport(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT));
        assertTrue("Check if the opposite and the possible direction are correct when current direction is LEFT and reverse is active",
                oppositeDirectionTestSupport(Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN));
        assertTrue("Check if the opposite and the possible direction are correct when current direction is RIGHT and reverse is active",
                oppositeDirectionTestSupport(Direction.RIGHT, Direction.LEFT, Direction.UP, Direction.DOWN));
    }

}
