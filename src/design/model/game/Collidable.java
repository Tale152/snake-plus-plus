package design.model.game;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

public interface Collidable {

	public void onCollision(Snake collider) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	public Point getPoint();
	
	public void setPoint(Point point);
	
}
