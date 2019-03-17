package design.model.game;

import java.awt.Point;
import java.util.Optional;

public interface Item {
	
	public Point getPoint();
	
	public void onCollision(Snake snake, long time);
	
	public Optional<Long> getDuration();
	
}
