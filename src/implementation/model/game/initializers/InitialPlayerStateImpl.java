package implementation.model.game.initializers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import design.model.game.Direction;
import design.model.game.InitialGameState.InitialPlayerState;

public class InitialPlayerStateImpl implements InitialPlayerState {

	private final String name;
	private final List<Point> bodyPoints;
	private final Direction direction;
	private final int score;
	
	public InitialPlayerStateImpl(String name, List<Point> bodyPoints, Direction direction, int score) {
		check(name, bodyPoints, direction, score);
		this.name = name;
		this.bodyPoints = bodyPoints;
		this.direction = direction;
		this.score = score;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public List<Point> getBodyPoints() {
		ArrayList<Point> res = new ArrayList<>();
		for (Point p : bodyPoints) {
			res.add(new Point(p.x, p.y));
		}
		return res;
	}

	@Override
	public Direction getDirection() {
		return direction;
	}

	@Override
	public int getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		String res = "Name:\t" + name + "\n";
		res += "Score:\t" + score + "\n";
		res += "Dir:\t" + direction + "\n";
		res += "Coord:";
		int i = 0;
		for (Point point : bodyPoints) {
			res += "\t(" + i++ + ")[" + point.x + "," + point.y + "]\n";
		}
		return res;
	}
	
	private boolean invalidDirection(List<Point> bodyPoints, Direction direction) {
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
	
	private boolean sameCoordinates(List<Point> bodyPoints) {
		ArrayList<Point> tmp = new ArrayList<>(bodyPoints);
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
	
	private boolean notConsecutive(List<Point> bodyPoints) {
		//TODO
		return false;
	}
	
	private void check(String name, List<Point> bodyPoints, Direction direction, int score) {
		if (name == null) {
			throw new NullPointerException("Argument name into InitialPlayerStateImpl's constructor cannot be null");
		}
		else if (direction == null) {
			throw new NullPointerException("Argument direction into InitialPlayerStateImpl's constructor cannot be null");
		}
		else if (score < 0) {
			throw new IllegalArgumentException("Argument score into InitialPlayerStateImpl's constructor cannot be negative");
		}
		else if (bodyPoints == null) {
			throw new NullPointerException("Argument bodyPoints into InitialPlayerStateImpl's constructor cannot be null");
		}
		else if (bodyPoints.isEmpty()) {
			throw new IllegalStateException("List bodyPoints into InitialPlayerStateImpl's constructor cannot be empty");
		}
		else if (bodyPoints.contains(null)) {
			throw new IllegalStateException("Some entries into list bodyPoints are null");
		}
		else if (bodyPoints.stream().anyMatch(b -> {return b.x < 0 || b.y < 0;})) {
			throw new IllegalStateException("Some entries into list bodyPoints have negative coordinates");
		}
		else if (sameCoordinates(bodyPoints)) {
			throw new IllegalStateException(""); //TODO
		}
		else if (notConsecutive(bodyPoints)) {
			throw new IllegalStateException(""); //TODO
		}
		else if (invalidDirection(bodyPoints, direction)) {
			throw new IllegalStateException(""); //TODO
		}
	}

}
