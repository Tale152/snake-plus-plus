package implementation.model.game.initializers;

import java.io.Serializable;
import java.util.Optional;
import design.model.game.Game;
import design.model.game.Snake;
import design.model.game.WinConditions;

public class WinConditionsImpl implements WinConditions, Serializable {

	private static final long serialVersionUID = 8527691853486844174L;
	private final SerializableOptional<Integer> snakeLength;
	private final SerializableOptional<Integer> score;
	private final SerializableOptional<Long> time;
	
	public WinConditionsImpl(Optional<Integer> snakeLength, Optional<Integer> score, Optional<Long> time) {
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
		this.snakeLength = SerializableOptional.fromOptional(snakeLength);
		this.score = SerializableOptional.fromOptional(score);
		this.time = SerializableOptional.fromOptional(time);
	}
	@Override
	public boolean checkSnakeLength(Game game) {
		if (snakeLength.asOptional().isPresent()) {
			for (Snake s : game.getSnakes()) {
				if (s.isAlive() && s.getBodyParts().size() >= snakeLength.asOptional().get()){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkScore(Game game) {
		if (score.asOptional().isPresent()) {
			for (Snake s : game.getSnakes()) {
				if (s.isAlive() && s.getPlayer().getScore() >= score.asOptional().get()){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkTime(Game game) {
		if (time.asOptional().isPresent()) {
			return game.getGameTime() >= time.asOptional().get();
		}
		return false;
	}

}

