package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;
import design.model.game.Snake;

public class Turbo extends ItemAbstract{

	private static final long serialVersionUID = 7295742469693235763L;
	private final static double MULTIPLY = 1.5;
	private double originalMultiplier;
	
	protected Turbo(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		super(point);
		setEffect(new EffectAbstract(expirationTime, effectDuration) {
			
			private static final long serialVersionUID = 8736312974229187554L;

			@Override
			protected void behaviorOnEffectStart(Snake target) {
				if (effectDuration.isPresent() && target.getEffects().contains(this)) {
					originalMultiplier = target.getProperties().getSpeed().getSpeedMultiplier();
					target.getProperties().getSpeed().applySpeedMultiplier((originalMultiplier * MULTIPLY)-originalMultiplier); 
				}
			}
			
			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				target.getProperties().getSpeed().applySpeedMultiplier(-((originalMultiplier * MULTIPLY)-originalMultiplier));
			}
			
		});
	}

}
