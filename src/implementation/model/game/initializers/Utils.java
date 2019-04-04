package implementation.model.game.initializers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import design.model.game.Direction;

public class Utils {

	private Utils() {}
	
	public static boolean invalidDirection(List<Point> bodyPoints, Direction direction) {
		if (bodyPoints.size() > 1) {
			switch (direction) {
				case UP : if (!(bodyPoints.get(0).y - bodyPoints.get(1).y == 1)) {return true;} break;
				case DOWN : if (!(bodyPoints.get(0).y - bodyPoints.get(1).y == -1)) {return true;} break;
				case LEFT : if (!(bodyPoints.get(0).x - bodyPoints.get(1).x == 1)) {return true;} break;
				case RIGHT: if (!(bodyPoints.get(0).x - bodyPoints.get(1).x == -1)) {return true;} break;
			}
		}
		return false;
	}
	
	public static boolean notConsecutive(List<Point> bodyPoints) {
		for (int i = 0; i <= bodyPoints.size() - 2; ++i) {
			int dX = Math.abs(bodyPoints.get(i).x - bodyPoints.get(i+1).x);
			int dY = Math.abs(bodyPoints.get(i).y - bodyPoints.get(i+1).y);
			if (!((dX == 0 && dY == 1) || (dX == 1 && dY == 0))) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean invalidPoint(Point field, Point toCheck) {
		if (toCheck.x >= field.x || toCheck.x < 0) {
			return true;
		}
		else if (toCheck.y >= field.y || toCheck.y < 0) {
			return true;
		}
		return false;
	}
	
	public static boolean sameCoordinates(List<Point> points) {
		ArrayList<Point> tmp = new ArrayList<>(points);
		while (!tmp.isEmpty()) {
			Point toCheck = tmp.remove(0);
			for (Point p : tmp) {
				if (p.equals(toCheck)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void throwIllegalState(boolean cond, String msg) {
		if (cond) {
			throw new IllegalStateException(msg);
		}
	}
	
	public static void throwNullPointer(boolean cond, String msg) {
		if (cond) {
			throw new NullPointerException(msg);
		}
	}
	
}
