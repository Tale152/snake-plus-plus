package implementation.model.game.initializers;

import java.util.Optional;
import design.model.game.Game;
import design.model.game.LossConditions;
import design.model.game.Snake;

public class LossConditionsImpl implements LossConditions {

	private final boolean checkAllSnakesDied;
	private Optional<Long> gameTime;
	
	public LossConditionsImpl(boolean checkAllSnakesDied, Optional<Long> gameTime) {
		//i check non si possono mettere in un metodo a parte perchè sarebbe un nuovo metodo chiamato dalla reflection
		if (gameTime == null) {
			throw new NullPointerException();
		}
		if (gameTime.isPresent() && gameTime.get() < 0) {
			throw new IllegalArgumentException("gameTime cannot less than zero");
		}
		this.checkAllSnakesDied = checkAllSnakesDied;
		this.gameTime = gameTime;
	}
	
	@Override
	public boolean checkSnakes(Game game) {
		if (checkAllSnakesDied) {
			for (Snake s : game.getSnakes()) {
				if (s.isAlive()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean checkTime(Game game) {
		if (gameTime.isPresent()) {
			return gameTime.get() <= game.getGameTime();
		}
		return false;
	}

}
