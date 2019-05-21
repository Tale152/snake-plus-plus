package implementation.model.game.gameRules;

import java.util.List;
import java.util.Optional;
import design.model.game.LossConditions;
import design.model.game.Snake;

public class LossConditionsImpl implements LossConditions {

	private final boolean checkAllSnakesDied;
	private Optional<Long> gameTime;
	private boolean timeGoesForward;
	
	public LossConditionsImpl(boolean checkAllSnakesDied, Optional<Long> gameTime, boolean timeGoesForward) {
		if (gameTime == null) {
			throw new NullPointerException();
		}
		if (gameTime.isPresent() && gameTime.get() < 0) {
			throw new IllegalArgumentException("gameTime cannot less than zero");
		}
		this.checkAllSnakesDied = checkAllSnakesDied;
		this.gameTime = gameTime;
		this.timeGoesForward = timeGoesForward;
	}
	
	@Override
	public boolean checkSnakes(List<Snake> snakes) {
		if (checkAllSnakesDied) {
			for (Snake s : snakes) {
				if (s.isAlive()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean checkTime(Long time) {
		if (gameTime.isPresent()) {
			if (timeGoesForward) {
				return gameTime.get() <= time;
			}
			else {
				return gameTime.get() >= time;
			}	
		}
		return false;
	}

}
