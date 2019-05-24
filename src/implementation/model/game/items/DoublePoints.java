package implementation.model.game.items;

import java.util.Optional;
import design.model.game.Field;
import design.model.game.Snake;

public class DoublePoints extends EffectAbstract{

	public final static double MULTIPLIER = 2;
	
	public DoublePoints(Optional<Long> dEffectDuration) {
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
		snake.getPlayer().applyScoreMultiplier(snake.getPlayer().getScoreMultiplier() * MULTIPLIER);
	}

	@Override
	protected void behaviorOnLastingEffectEnd(Snake snake) {
		snake.getPlayer().applyScoreMultiplier(snake.getPlayer().getScoreMultiplier() / MULTIPLIER);
	}
	
}
