package implementation.model.game.items;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import design.model.game.Snake;
import design.model.game.Wall;
import implementation.controller.game.loader.WallDeserializer;

/**
 * @see Wall
 */
@JsonDeserialize(using = WallDeserializer.class)
public class WallImpl extends CollidableAbstract implements Wall {

    /**
     * @param point where will be placed into the field
     */
    public WallImpl(final Point point) {
        super(point);
    }

    @Override
    public final void onCollision(final Snake collider) throws NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (collider.getProperties().getCollisionProperty().isSpring()) {
            collider.reverse();
        } else if (!collider.getProperties().getCollisionProperty().isIntangible() 
                && !collider.getProperties().getCollisionProperty().isInvincible()) {
            collider.kill();
        }
    }
}
