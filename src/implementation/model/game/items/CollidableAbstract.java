package implementation.model.game.items;

import java.awt.Point;
import design.model.game.Collidable;

public abstract class CollidableAbstract implements Collidable {

	private Point point;
	
	public CollidableAbstract(Point point) {
		if (point == null) {
			throw new NullPointerException();
		}
		this.point = point;
	}
	
	@Override
	public Point getPoint() {
		return new Point(point);
	}

	@Override
	public void setPoint(Point point) {
		this.point = point;
	}

}
