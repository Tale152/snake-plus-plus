package implementation.model.game.initializers;

import java.awt.Point;
import design.model.game.Direction;
import design.model.game.InitialGameState.InitialPlayerState;

public class InitialPlayerStateImpl implements InitialPlayerState {

	private final String name;
	private final Point position;
	private final Direction direction;
	private final int score;
	
	public InitialPlayerStateImpl(String name, Point position, Direction direction, int score) {
		if (name == null || position == null || direction == null || score < 0) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.position = position;
		this.direction = direction;
		this.score = score;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Point getPosition() {
		return new Point(position.x, position.y);
	}

	@Override
	public Direction getDirection() {
		return direction;
	}

	@Override
	public int getScore() {
		return score;
	}

}
