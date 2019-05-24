package implementation.model.game.items;

import java.util.Optional;
import design.model.game.Field;
import design.model.game.Snake;

public class GhostMode extends EffectAbstract{

	public GhostMode(Optional<Long> dEffectDuration) {
		super(dEffectDuration);
	}

	@Override
	public void instantaneousEffect(Snake target) {
		//does nothing
	}

	@Override
	public void expirationEffect(Field field) {
		//does nothing
	}

	@Override
	protected void behaviorOnLastingEffectStart(Snake snake) {
		snake.getProperties().getCollisionProperty().setIntangibility(true);
	}

	@Override
	protected void behaviorOnLastingEffectEnd(Snake snake) {
		snake.getProperties().getCollisionProperty().setIntangibility(false);
	}
	
}
