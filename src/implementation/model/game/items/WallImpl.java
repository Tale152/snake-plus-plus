package implementation.model.game.items;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import design.model.game.Snake;
import design.model.game.Wall;
import implementation.controller.game.gameLoader.WallDeserializer;

/**
 * @see Wall
 * @author Alessandro Talmi
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
        if (collider.getProperties().getCollisionProperty().getSpring()) {
            collider.reverse();
        } else if (!collider.getProperties().getCollisionProperty().getIntangibility() 
                && !collider.getProperties().getCollisionProperty().getInvincibility()) {
            collider.kill();
        }
    }
}
