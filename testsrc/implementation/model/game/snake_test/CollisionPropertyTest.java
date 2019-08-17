package implementation.model.game.snake_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import design.model.game.CollisionProperty;

/**
 * Tests regarding snake collision properties.
 * @see CollisionProperty
 */
public class CollisionPropertyTest {

    /**
     * Testing if snake invincibility, intangibiliy and spring are set correctly.
     */
    @Test
    public void testCollisionProperty() {
        final CollisionProperty collision = SnakeComponentsFactoryUtils.createCollisionProperty();

        assertFalse("Checking if the invincibily is not active", collision.isInvincible());
        collision.setInvincibility(true);
        assertTrue("Checking if the invincibily has been activated", collision.isInvincible());

        assertFalse("Checking if the intagibility is not active", collision.isIntangible());
        collision.setIntangibility(true);
        assertTrue("Checking if the intangibility has been activated", collision.isIntangible());

        assertFalse("Checking if the spring is not active", collision.isSpring());
        collision.setSpring(true);
        assertTrue("Checking if the spring has been activated", collision.isSpring());
    }

}
