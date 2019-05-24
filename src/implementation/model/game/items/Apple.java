package implementation.model.game.items;

import java.util.Optional;
import design.model.game.Field;
import design.model.game.Snake;

public class Apple extends EffectAbstract{

	public final static int LENGTH_INCREMENT = 1;
	public final static int SCORE_INCREMENT = 10;
	
	public Apple(Optional<Long> dEffectDuration) {
		super(dEffectDuration);
	}

	@Override
	public void instantaneousEffect(Snake target) {
		target.getProperties().getLengthProperty().lengthen(LENGTH_INCREMENT);
		target.getPlayer().addScore(SCORE_INCREMENT);
	}

	@Override
	public void expirationEffect(Field field) {
		//does nothing
	}

	@Override
	protected void behaviorOnLastingEffectStart(Snake snake) {
		//does nothing
	}

	@Override
	protected void behaviorOnLastingEffectEnd(Snake snake) {
		snake.getProperties().getLengthProperty().shorten(snake.getProperties().getLengthProperty().getLength() - 1);
		snake.getPlayer().reduceScore(snake.getPlayer().getScore());
	}
}
