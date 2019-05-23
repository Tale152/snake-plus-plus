package implementation.model.game.items;

import java.util.Optional;

import design.model.game.Effect;
import design.model.game.Field;
import design.model.game.Snake;

public class ScoreEarning extends EffectAbstract{

	public ScoreEarning(Optional<Long> dEffectDuration) {
		super(dEffectDuration);
	}

	@Override
	public void instantaneousEffect(Snake target) {
		Optional<Effect> active = target.getEffects().stream().filter(e -> {return e.getClass().equals(this.getClass());}).findFirst();
		if (active.isPresent()) {
			target.getPlayer().addScore(Apple.SCORE_INCREMENT * (active.get().getComboCounter() + 1));
		}
		else {
			target.getPlayer().addScore(Apple.SCORE_INCREMENT);
		}
	}

	@Override
	public void expirationEffect(Field field) {
		// does nothing
	}

	@Override
	protected void behaviorOnLastingEffectStart(Snake snake) {
		// does nothing
	}

	@Override
	protected void behaviorOnLastingEffectEnd(Snake snake) {
		// does nothing
	}
	
}
