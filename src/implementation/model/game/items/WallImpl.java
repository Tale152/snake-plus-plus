package implementation.model.game.items;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import design.model.game.*;

public class WallImpl extends CollidableAbstract implements Wall{

	public WallImpl(Point point) {
		super(point);
	}

	@Override
	public void onCollision(Snake collider) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (!collider.getProperties().getCollision().getIntangibility() &&
				!collider.getProperties().getCollision().getInvincibility() &&
				!collider.getProperties().getCollision().getSpring()) {
			collider.kill();
		}
	}
}
