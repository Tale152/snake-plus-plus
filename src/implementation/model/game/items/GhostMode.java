package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;

import design.model.game.Snake;

public class GhostMode extends ItemAbstract{

	private static final long serialVersionUID = 3720634634887705769L;

	protected GhostMode(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		super(point);
		setEffect(new EffectAbstract(expirationTime, effectDuration) {

			private static final long serialVersionUID = 5109973095473765491L;

			@Override
			protected void behaviorOnEffectStart(Snake target) {
				if (effectDuration.isPresent()) {
					target.getProperties().getCollision().setIntangibility(true);
				}
			}
			
			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				target.getProperties().getCollision().setIntangibility(false);
			}
			
		});
	}
	
}
