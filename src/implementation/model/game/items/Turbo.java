package implementation.model.game.items;

import java.util.Optional;

import design.model.game.Field;
import design.model.game.Snake;

public class Turbo extends EffectAbstract{

	public static final double SPEED_MULTIPLICATOR = 2;
	private double delta;
	
	public Turbo(Optional<Long> dEffectDuration) {
		super(dEffectDuration);
		delta = 0;
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
		delta = snake.getProperties().getSpeedProperty().getSpeedMultiplier() / SPEED_MULTIPLICATOR;
		snake.getProperties().getSpeedProperty().applySpeedMultiplier(-delta);
	}

	@Override
	protected void behaviorOnLastingEffectEnd(Snake snake) {
		snake.getProperties().getSpeedProperty().applySpeedMultiplier(delta);
	}

}
