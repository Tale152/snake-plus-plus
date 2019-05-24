package implementation.model.game.items;

import java.util.Optional;

import design.model.game.Field;
import design.model.game.Snake;

public class BadApple extends EffectAbstract{

	public final static int SHORTEN_DENOMINATOR = 2;
	
	public BadApple(Optional<Long> dEffectDuration) {
		super(dEffectDuration);
	}

	@Override
	public void instantaneousEffect(Snake target) {
		target.getProperties().getLengthProperty().shorten(target.getProperties().getLengthProperty().getLength() / SHORTEN_DENOMINATOR);
		target.getPlayer().reduceScore(target.getPlayer().getScore() / SHORTEN_DENOMINATOR);
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
		int length = snake.getProperties().getLengthProperty().getLength();
		snake.getProperties().getLengthProperty().lengthen((length * getComboCounter()) - length);
	}
	
	
}
