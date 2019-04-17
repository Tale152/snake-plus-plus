package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;

import design.model.game.Snake;

public class Spring extends ItemAbstract{

	private static final long serialVersionUID = -2740373303428881843L;

	protected Spring(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		super(point);
		setEffect(new EffectAbstract(expirationTime, effectDuration) {

			private static final long serialVersionUID = -8772293319348362836L;

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
