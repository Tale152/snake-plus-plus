package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;

import design.model.game.Snake;

public class GodMode extends ItemAbstract{

	private static final long serialVersionUID = 6593915879725449276L;

	protected GodMode(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		super(point);
		setEffect(new EffectAbstract(expirationTime, effectDuration) {

			private static final long serialVersionUID = 7337119138033105869L;

			@Override
			protected void behaviorOnEffectStart(Snake target) {
				if (effectDuration.isPresent()) {
					target.getProperties().getCollision().setInvincibility(true);
				}
			}
			
			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				target.getProperties().getCollision().setInvincibility(false);
			}
			
		});
	}
	
}
