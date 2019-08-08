package implementation.model.game.snake;

import design.model.game.Properties;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PropertiesTest {

	@Test
	public void testProperties() {
		Properties properties = SnakeComponentsFactoryForTest.createProperties();
		assertTrue(properties.getCollisionProperty() != null);
		assertTrue(properties.getDirectionProperty() != null);
		assertTrue(properties.getLengthProperty() != null);
		assertTrue(properties.getPickupProperty() != null);
		assertTrue(properties.getSpeedProperty() != null);
	}
}
