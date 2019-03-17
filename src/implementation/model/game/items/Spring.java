package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;

import design.model.game.Snake;

public class Spring extends ItemAbstract{

	protected Spring(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		super(point);
		setEffect(new EffectAbstract(expirationTime, effectDuration) {
			
			@Override
			protected void behaviorOnEffectStart(Snake target) {
				if (effectDuration.isPresent()) {
					target.getProperties().getCollision().setSpring(true);
				}
				target.reverse();
			}
			
			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				target.getProperties().getCollision().setSpring(false);
			}
			
		});
	}

}
