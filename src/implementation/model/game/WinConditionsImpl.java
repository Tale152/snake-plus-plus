package implementation.model.game;

import java.util.Optional;

import design.model.game.Game;
import design.model.game.Snake;
import design.model.game.WinConditions;

public class WinConditionsImpl implements WinConditions {

	private final Optional<Integer> snakeLength;
	private final Optional<Integer> score;
	private final Optional<Long> time;
	
	public WinConditionsImpl(Optional<Integer> snakeLength, Optional<Integer> score, Optional<Long> time) {
		//i check non si possono mettere in un metodo a parte perchè sarebbe un nuovo metodo chiamato dalla reflection
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
		this.score = score;
		this.time = time;
	}
	@Override
	public boolean checkSnakeLength(Game game) {
		if (snakeLength.isPresent()) {
			for (Snake s : game.getSnakes()) {
				if (s.isAlive() && s.getBodyParts().size() >= snakeLength.get()){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkScore(Game game) {
		if (score.isPresent()) {
			for (Snake s : game.getSnakes()) {
				if (s.isAlive() && s.getPlayer().getScore() >= score.get()){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkTime(Game game) {
		if (time.isPresent()) {
			return game.getGameTime() >= time.get();
		}
		return false;
	}

}
