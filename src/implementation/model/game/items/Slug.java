package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;
import design.model.game.Snake;

public class Slug extends ItemAbstract{

	private static final long serialVersionUID = 7480298169975835312L;
	private final static double DIVIDE = 0.5;
	private double originalMultiplier;
	
	protected Slug(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		super(point);
		setEffect(new EffectAbstract(expirationTime, effectDuration) {
			
			private static final long serialVersionUID = -2499782939669027472L;

			@Override
			protected void behaviorOnEffectStart(Snake target) {
				if (effectDuration.isPresent() && target.getEffects().contains(this)) {
					originalMultiplier = target.getProperties().getSpeed().getSpeedMultiplier();
					target.getProperties().getSpeed().applySpeedMultiplier(-(originalMultiplier * DIVIDE)); 
				}
			}
			
			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				target.getProperties().getSpeed().applySpeedMultiplier(originalMultiplier * DIVIDE);
			}
			
		});
	}

}