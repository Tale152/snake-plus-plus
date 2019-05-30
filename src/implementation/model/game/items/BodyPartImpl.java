package implementation.model.game.items;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

import design.model.game.BodyPart;
import design.model.game.CollisionProperty;
import design.model.game.Snake;

public class BodyPartImpl extends CollidableAbstract implements BodyPart {

	private final Snake owner;
	private boolean head;
	private boolean body;
	private boolean tail;
	private boolean top;
	private boolean bottom;
	private boolean left;
	private boolean right;

	public BodyPartImpl(Point point) {
		super(point);
		head = false;
		body = false;
		tail = false;
		top = false;
		bottom = false;
		left = false;
		right = false;
		owner = null;
	}
	
	@Override
	public void onCollision(Snake collider) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (collider == owner) {
			interlacement();
		}
		else {
			collision(collider);
		}
	}

	private void interlacement() {
		CollisionProperty property = owner.getProperties().getCollisionProperty();
		if (!property.getIntangibility() && !property.getInvincibility()) {
			owner.kill();
		}
	}
	
	private void collision(Snake collider) {
		if (!collider.getProperties().getCollisionProperty().getIntangibility()) {
			CollisionProperty colliderProperty = collider.getProperties().getCollisionProperty();
			if (!colliderProperty.getIntangibility() && !colliderProperty.getInvincibility()) {
				collider.kill();
			}
		}
	}
	
	@Override
	public Snake getOwner() {
		return owner;
	}

	@Override
	public boolean isHead() {
		return head;
	}

	@Override
	public void setHead(boolean head) {
		this.head = head;
	}

	@Override
	public boolean isBody() {
		return body;
	}

	@Override
	public void setBody(boolean body) {
		this.body = body;
	}

	@Override
	public boolean isTail() {
		return tail;
	}

	@Override
	public void setTail(boolean tail) {
		this.tail = tail;
	}
	
	@Override
	public boolean isCombinedOnTop() {
		return top;
	}

	@Override
	public void setCombinedOnTop(boolean combined) {
		top = combined;
	}

	@Override
	public boolean isCombinedOnBottom() {
		return bottom;
	}

	@Override
	public void setCombinedOnBottom(boolean combined) {
		bottom = combined;
	}

	@Override
	public boolean isCombinedOnLeft() {
		return left;
	}

	@Override
	public void setCombinedOnLeft(boolean combined) {
		left = combined;
	}

	@Override
	public boolean isCombinedOnRight() {
		return right;
	}

	@Override
	public void setCombinedOnRight(boolean combined) {
		right = combined;
	}

}
