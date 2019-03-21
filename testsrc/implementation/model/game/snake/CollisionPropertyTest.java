package implementation.model.game.snake;

import static org.junit.Assert.*;
import org.junit.Test;
import design.model.game.*;

public class CollisionPropertyTest {
	
	@Test
	public void testCollisionProperty() {
		CollisionProperty collision = SnakeComponentsFactoryForTest.createCollisionProperty(false, false, false);
		
		assertFalse(collision.getInvincibility());
		collision.setInvincibility(true);
		assertTrue(collision.getInvincibility());
		
		assertFalse(collision.getIntangibility());
		collision.setIntangibility(true);
		assertTrue(collision.getIntangibility());
		
		assertFalse(collision.getSpring());
		collision.setSpring(true);
		assertTrue(collision.getSpring());
		
	}

}
