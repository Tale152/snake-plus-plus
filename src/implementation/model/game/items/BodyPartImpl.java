package implementation.model.game.items;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

import design.model.game.BodyPart;
import design.model.game.CollisionProperty;
import design.model.game.Snake;

/**
 * @see BodyPart
 * @author Alessandro Talmi
 */
public class BodyPartImpl extends CollidableAbstract implements BodyPart {

    private boolean head;
    private boolean body;
    private boolean tail;
    private boolean top;
    private boolean bottom;
    private boolean left;
    private boolean right;
    private final Snake owner;

    /**
     * @param point where this body part is relative to the field
     * @param owner Snake that owns this bodypart
     */
    public BodyPartImpl(final Point point, final Snake owner) {
        super(point);
        head = false;
        body = false;
        tail = false;
        top = false;
        bottom = false;
        left = false;
        right = false;
        this.owner = owner;
    }

    @Override
    public final void onCollision(final Snake collider) throws NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!collider.getProperties().getCollisionProperty().isIntangible()) {
            final CollisionProperty colliderProperty = collider.getProperties().getCollisionProperty();
            if (!colliderProperty.isIntangible() && !colliderProperty.isInvincible()) {
                collider.kill();
            }
        }
    }

    @Override
    public final boolean isHead() {
        return head;
    }

    @Override
    public final void setHead(final boolean head) {
        this.head = head;
    }

    @Override
    public final boolean isBody() {
        return body;
    }

    @Override
    public final void setBody(final boolean body) {
        this.body = body;
    }

    @Override
    public final boolean isTail() {
        return tail;
    }

    @Override
    public final void setTail(final boolean tail) {
        this.tail = tail;
    }

    @Override
    public final boolean isCombinedOnTop() {
        return top;
    }

    @Override
    public final void setCombinedOnTop(final boolean combined) {
        top = combined;
    }

    @Override
    public final boolean isCombinedOnBottom() {
        return bottom;
    }

    @Override
    public final void setCombinedOnBottom(final boolean combined) {
        bottom = combined;
    }

    @Override
    public final boolean isCombinedOnLeft() {
        return left;
    }

    @Override
    public final void setCombinedOnLeft(final boolean combined) {
        left = combined;
    }

    @Override
    public final boolean isCombinedOnRight() {
        return right;
    }

    @Override
    public final void setCombinedOnRight(final boolean combined) {
        right = combined;
    }

    @Override
    public final Snake getOwner() {
        return owner;
    }

}
