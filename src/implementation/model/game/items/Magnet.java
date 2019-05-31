package implementation.model.game.items;

import java.util.Optional;
import design.model.game.Field;
import design.model.game.Snake;

public class Magnet extends EffectAbstract{

	public final static int MAGNET_RADIOUS_MULTIPLIER = 2;
	
	
	public Magnet(Optional<Long> dEffectDuration) {
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
		snake.getProperties().getPickupProperty().setPickupRadius(snake.getProperties().getPickupProperty().getPickupRadius() * MAGNET_RADIOUS_MULTIPLIER);
	}

	@Override
	protected void behaviorOnLastingEffectEnd(Snake snake) {
		snake.getProperties().getPickupProperty().setPickupRadius(snake.getProperties().getPickupProperty().getPickupRadius() / MAGNET_RADIOUS_MULTIPLIER);
	}

}
