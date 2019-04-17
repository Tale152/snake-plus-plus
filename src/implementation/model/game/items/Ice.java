package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;
import design.model.game.Snake;

public class Ice extends ItemAbstract{

	private static final long serialVersionUID = 8445094309313391134L;
	private double originalMultiplier;
	
	protected Ice(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		super(point);
		setEffect(new EffectAbstract(expirationTime, effectDuration) {
			
			private static final long serialVersionUID = 7014042636548924708L;

			@Override
			protected void behaviorOnEffectStart(Snake target) {
				if (effectDuration.isPresent()) {
					originalMultiplier = target.getProperties().getSpeed().getSpeedMultiplier();
					target.getProperties().getSpeed().applySpeedMultiplier(-originalMultiplier); 
				}
			}
			
			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				target.getProperties().getSpeed().applySpeedMultiplier(originalMultiplier);
			}
			
		});
	}

}
