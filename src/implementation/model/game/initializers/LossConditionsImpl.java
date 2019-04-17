package implementation.model.game.initializers;

import java.io.Serializable;
import java.util.Optional;
import design.model.game.Game;
import design.model.game.LossConditions;
import design.model.game.Snake;

public class LossConditionsImpl implements LossConditions, Serializable {

	private static final long serialVersionUID = 6779258257820890214L;
	private final boolean checkAllSnakesDied;
	private SerializableOptional<Long> gameTime;
	
	public LossConditionsImpl(boolean checkAllSnakesDied, Optional<Long> gameTime) {
		if (gameTime == null) {
			throw new NullPointerException();
		}
		if (gameTime.isPresent() && gameTime.get() < 0) {
			throw new IllegalArgumentException("gameTime cannot less than zero");
		}
		this.checkAllSnakesDied = checkAllSnakesDied;
		this.gameTime = SerializableOptional.fromOptional(gameTime);
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
		if (gameTime.asOptional().isPresent()) {
			return gameTime.asOptional().get() <= game.getGameTime();
		}
		return false;
	}

}
