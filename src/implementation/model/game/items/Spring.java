package implementation.model.game.items;

import java.util.Optional;

import design.model.game.Field;
import design.model.game.Snake;

public class Spring extends EffectAbstract{

	public Spring(Optional<Long> dEffectDuration) {
		super(dEffectDuration);
	}

	@Override
	public void instantaneousEffect(Snake target) {
		target.reverse();
	}

	@Override
	public void expirationEffect(Field field) {
		//does nothing
	}

	@Override
	protected void behaviorOnLastingEffectStart(Snake snake) {
		snake.getProperties().getCollisionProperty().setSpring(true);
	}

	@Override
	protected void behaviorOnLastingEffectEnd(Snake snake) {
		snake.getProperties().getCollisionProperty().setSpring(false);
	}

}
