package implementation.model.game.snake;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import design.model.game.Direction;
import design.model.game.DirectionProperty;

public class DirectionPropertyTest {
	
	private boolean standardDirectionTestSupport(Direction current, Direction opposite, Direction possible1, Direction possible2) {
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
		if (!direction.getDirection().equals(possible2)) {
			return false;
		}
		return true;
	}
	
	private boolean oppositeDirectionTestSupport(Direction current, Direction opposite, Direction possible1, Direction possible2) {
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
		if (!direction.getDirection().equals(possible1)) {
			return false;
		}
		return true;
	}
	
	@Test
	public void testDirectionProperty() {
		DirectionProperty direction;
		try{
			direction = SnakeComponentsFactoryForTest.createDirectionProperty(null);
            fail("initial direction cannot be null");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		
		direction = SnakeComponentsFactoryForTest.createDirectionProperty(Direction.DOWN);
		assertFalse(direction.getReverseDirection());
		direction.setReverseDirection(true);
		assertTrue(direction.getReverseDirection());
		assertTrue(standardDirectionTestSupport(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));
		assertTrue(standardDirectionTestSupport(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT));
		assertTrue(standardDirectionTestSupport(Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN));
		assertTrue(standardDirectionTestSupport(Direction.RIGHT, Direction.LEFT, Direction.UP, Direction.DOWN));
		assertTrue(oppositeDirectionTestSupport(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));
		assertTrue(oppositeDirectionTestSupport(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT));
		assertTrue(oppositeDirectionTestSupport(Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN));
		assertTrue(oppositeDirectionTestSupport(Direction.RIGHT, Direction.LEFT, Direction.UP, Direction.DOWN));
	
	}
}
