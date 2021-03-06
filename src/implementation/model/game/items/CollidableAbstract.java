package implementation.model.game.items;

import java.awt.Point;
import design.model.game.Collidable;
/**
 * This abstract class defines all common behaviors of a collidable.
 * @see Collidable
 */
public abstract class CollidableAbstract implements Collidable {

    private Point point;

    /**
     * @param point coordinates where the collidable is placed into the field
     */
    public CollidableAbstract(final Point point) {
        this.point = point;
    }

    @Override
    public final Point getPoint() {
        return new Point(point);
    }

    @Override
    public final void setPoint(final Point point) {
        this.point = point;
    }

}
