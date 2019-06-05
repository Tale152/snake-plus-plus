package implementation.model.game.items;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import design.model.game.*;
import implementation.controller.game.gameLoader.WallDeserializer;

@JsonDeserialize(using = WallDeserializer.class)
public class WallImpl extends CollidableAbstract implements Wall{

	public WallImpl(Point point) {
		super(point);
	}

	@Override
	public void onCollision(Snake collider) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (collider.getProperties().getCollisionProperty().getSpring()) {
			collider.reverse();
		}
		else if (!collider.getProperties().getCollisionProperty().getIntangibility() &&
				!collider.getProperties().getCollisionProperty().getInvincibility()) {
			collider.kill();
		}
	}
}
