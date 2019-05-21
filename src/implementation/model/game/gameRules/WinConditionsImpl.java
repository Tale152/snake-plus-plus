package implementation.model.game.gameRules;

import java.util.List;
import java.util.Optional;
import design.model.game.Snake;
import design.model.game.WinConditions;

public class WinConditionsImpl implements WinConditions {

	private final Optional<Integer> snakeLength;
	private final Optional<Integer> scoreToReach;
	private final Optional<Long> timeToReach;
	private final boolean timeGoesForward;
	
	public WinConditionsImpl(Optional<Integer> snakeLength, Optional<Integer> score, Optional<Long> time, boolean timeGoesForward) {
		if (snakeLength == null || score == null || time == null) {
			throw new NullPointerException();
		}
		if (snakeLength.isPresent() && snakeLength.get() < 0) {
			throw new IllegalArgumentException("snakeLenght cannot be less than 0");
		}
		if (score.isPresent() && score.get() < 0) {
			throw new IllegalArgumentException("score cannot be less than 0");
		}
		if (time.isPresent() && time.get() < 0) {
			throw new IllegalArgumentException("time cannot be less than 0");
		}
		this.snakeLength = snakeLength;
		this.scoreToReach = score;
		this.timeToReach = time;
		this.timeGoesForward = timeGoesForward;
	}
	@Override
	public boolean checkSnakeLength(List<Snake> snakes) {
		if (snakeLength.isPresent()) {
			for (Snake s : snakes) {
				if (s.isAlive() && s.getBodyParts().size() >= snakeLength.get()){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkScore(List<Snake> snakes) {
		if (scoreToReach.isPresent()) {
			for (Snake s : snakes) {
				if (s.isAlive() && s.getPlayer().getScore() >= scoreToReach.get()){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkTime(Long time) {
		if (timeToReach.isPresent()) {
			if (timeGoesForward) {
				return timeToReach.get() <= time;
			}
			else {
				return timeToReach.get() >= time;
			}	
		}
		return false;
	}

}

