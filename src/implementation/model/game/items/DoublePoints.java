package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;

import design.model.game.Snake;

public class DoublePoints extends ItemAbstract{

	private static final long serialVersionUID = 5588362246153582098L;
	private static final int BASE_MULTIPLIER = 1;
	private static final int APPLY_MULTIPLIER = 2;
	
	protected DoublePoints(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		super(point);
		setEffect(new EffectAbstract(expirationTime, effectDuration) {
			
			private static final long serialVersionUID = 3332563718337702L;

			@Override
			protected void behaviorOnEffectStart(Snake target) {
				if (effectDuration.isPresent()) {
					target.getPlayer().applyScoreMultiplier(APPLY_MULTIPLIER);
				}
			}
			
			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				target.getPlayer().applyScoreMultiplier(BASE_MULTIPLIER);
			}
			
		});
	}
}
