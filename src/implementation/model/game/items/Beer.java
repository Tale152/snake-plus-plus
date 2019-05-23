package implementation.model.game.items;

import java.util.Optional;
import java.util.Random;

import design.model.game.Direction;
import design.model.game.Field;
import design.model.game.Snake;

public class Beer extends EffectAbstract{

	public Beer(Optional<Long> dEffectDuration) {
		super(dEffectDuration);
	}

	@Override
	public void instantaneousEffect(Snake target) {
		Direction direction = target.getProperties().getDirectionProperty().getDirection();
		if (direction == Direction.UP || direction == Direction.DOWN) {
			if (new Random().nextBoolean()) {
				target.getProperties().getDirectionProperty().forceDirection(Direction.LEFT);
			}
			else {
				target.getProperties().getDirectionProperty().forceDirection(Direction.RIGHT);
			}
		}
		else {
			if (new Random().nextBoolean()) {
				target.getProperties().getDirectionProperty().forceDirection(Direction.UP);
			}
			else {
				target.getProperties().getDirectionProperty().forceDirection(Direction.DOWN);
			}
		}
	}

	@Override
	public void expirationEffect(Field field) {
		//does nothing
	}

	@Override
	protected void behaviorOnLastingEffectStart(Snake snake) {
		snake.getProperties().getDirectionProperty().setReverseDirection(true);
		
	}

	@Override
	protected void behaviorOnLastingEffectEnd(Snake snake) {
		snake.getProperties().getDirectionProperty().setReverseDirection(false);
	}

	
}
