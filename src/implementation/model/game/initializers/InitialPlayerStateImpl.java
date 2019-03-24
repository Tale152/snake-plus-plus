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
		res += "Len:\t" + bodyPoints.size() + "\n";
		res += "Coord:";
		int i = 0;
		for (Point point : bodyPoints) {
			res += "\t(" + i++ + ")[" + point.x + "," + point.y + "]\n";
		}
		return res;
	}
	
	private void check(String name, List<Point> bodyPoints, Direction direction, int score) {
		Utils.throwNullPointer(name == null || direction == null || bodyPoints == null, "Null args");
		Utils.throwIllegalState(score < 0, "Argument score into InitialPlayerStateImpl's constructor cannot be negative");
		Utils.throwIllegalState(bodyPoints.isEmpty(), "List bodyPoints into InitialPlayerStateImpl's constructor cannot be empty");
		Utils.throwIllegalState(bodyPoints.contains(null), "Some entries into list bodyPoints are null");
		Utils.throwIllegalState(bodyPoints.stream().anyMatch(b -> {return b.x < 0 || b.y < 0;}), "Some entries into list bodyPoints have negative coordinates");
		Utils.throwIllegalState(Utils.sameCoordinates(bodyPoints), "Some entries into list bodyPoints have same coordinates");
		Utils.throwIllegalState(Utils.notConsecutive(bodyPoints), "Some entries into list bodyPoints are not consecutive");
		Utils.throwIllegalState(Utils.invalidDirection(bodyPoints, direction), "Direction of the head cannot go to towards the next point");
	}

}
