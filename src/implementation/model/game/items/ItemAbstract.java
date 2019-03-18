package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;
import design.model.game.*;

public abstract class ItemAbstract implements Item {

	private final Point point;
	private Effect effect;
	
	public ItemAbstract(Point point) {
		this.point = point;
	}
	
	@Override
	public Point getPoint() {
		return point;
	}

	protected void setEffect(Effect effect) {
		this.effect = effect;
	}
	
	@Override
	public Optional<Long> getDuration() {
		return effect.getExpirationTime();
	}
	
	@Override
	public void onCollision(Snake snake, long time) {
		checkEffect();
		effect.effectStart(snake, time);
	}
	
	private void checkEffect() {
		if (this.effect == null) {
			throw new IllegalStateException();
		}
	}
	
	public String toString() {
		String str = "Item:\t" + this.getClass().getSimpleName() + "\n\t";
		str += "[" + (int)point.getX() + "," + (int)point.getY() + "]\n";
		return str + effect.toString();
	}

}
