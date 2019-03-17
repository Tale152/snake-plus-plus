package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;

import design.model.game.Snake;

public class GodMode extends ItemAbstract{

	protected GodMode(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		super(point);
		if (!effectDuration.isPresent()) {
			throw new IllegalStateException();
		}
		setEffect(new EffectAbstract(expirationTime, effectDuration) {
			@Override
			protected void behaviorOnEffectStart(Snake target) {
				target.getProperties().getCollision().setInvincibility(true);
			}
			
			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				target.getProperties().getCollision().setInvincibility(false);
			}
			
		});
	}
	
}
