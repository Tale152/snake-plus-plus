package design.model.game;

import java.awt.Point;

public interface Item {

	public Point getPoint();
	
	public void onCollision(Snake snake, long time);
	
	public long getDuration();
	
}
