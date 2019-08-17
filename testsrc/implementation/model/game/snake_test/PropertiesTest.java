package implementation.model.game.snake_test;

import design.model.game.Properties;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * Tests regarding all snake the properties.
 */
public class PropertiesTest {

    /**
     * Test than all the snake properties can not be non-initialize.
     */
    @Test
    public void testProperties() {
        final Properties properties = SnakeComponentsFactoryUtils.createProperties();
        assertNotNull("Check if collision properties are not null", properties.getCollisionProperty());
        assertNotNull("Check if direction properties are not null", properties.getDirectionProperty());
        assertNotNull("Check if length properties are not null", properties.getLengthProperty());
        assertNotNull("Check if pick up properties are not null", properties.getPickupProperty());
        assertNotNull("Check if speed properties are not null", properties.getSpeedProperty());
    }

}
