package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;
import design.model.game.Snake;

public class Slug extends ItemAbstract{

	private final static double DIVIDE = 0.5;
	private double originalMultiplier;
	
	protected Slug(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		super(point);
		setEffect(new EffectAbstract(expirationTime, effectDuration) {
			
			@Override
			protected void behaviorOnEffectStart(Snake target) {
				if (effectDuration.isPresent()) {
					originalMultiplier = target.getProperties().getSpeed().getSpeedMultiplier();
					target.getProperties().getSpeed().applySpeedMultiplier(originalMultiplier * DIVIDE); 
				}
			}
			
			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				target.getProperties().getSpeed().applySpeedMultiplier(originalMultiplier);
			}
			
		});
	}

}