package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;

import design.model.game.Snake;

public class GhostMode extends ItemAbstract{

	protected GhostMode(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		super(point);
		setEffect(new EffectAbstract(expirationTime, effectDuration) {
			
			@Override
			protected void behaviorOnEffectStart(Snake target) {
				target.getProperties().getCollision().setIntangibility(true);;
			}
			
			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				target.getProperties().getCollision().setIntangibility(false);
			}
			
		});
	}
	
}
