package implementation.model.game.snake;

import design.model.game.Properties;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PropertiesTest {

	@Test
	public void testProperties() {
		Properties properties = SnakeComponentsFactoryForTest.createProperties();
		assertTrue(properties.getCollision() != null);
		assertTrue(properties.getDirection() != null);
		assertTrue(properties.getLength() != null);
		assertTrue(properties.getPickup() != null);
		assertTrue(properties.getSpeed() != null);
	}
}
