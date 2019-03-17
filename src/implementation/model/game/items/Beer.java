package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;
import design.model.game.Snake;

public class Beer extends ItemAbstract{

	protected Beer(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		super(point);
		if (!effectDuration.isPresent()) {
			throw new IllegalStateException();
		}
		setEffect(new EffectAbstract(expirationTime, effectDuration) {
			
			@Override
			protected void behaviorOnEffectStart(Snake target) {
				target.getProperties().getDirection().setReverseDirection(true);
			}
			
			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				target.getProperties().getDirection().setReverseDirection(false);
			}
			
		});
	}

}
