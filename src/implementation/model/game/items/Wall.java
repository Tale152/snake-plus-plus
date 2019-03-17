package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;

import design.model.game.Snake;

public class Wall extends ItemAbstract{

	protected Wall(Point point, Optional<Long> expirationTime) {
		super(point);
		setEffect(new EffectAbstract(expirationTime, Optional.empty()) {
			
			@Override
			protected void behaviorOnEffectStart(Snake target) {
				if (!target.getProperties().getCollision().getIntangibility()){
					if (target.getProperties().getCollision().getSpring()) {
						target.reverse();
					}
					else if (!target.getProperties().getCollision().getInvincibility()) {
						target.kill();
					}
				}
			}
			
			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				/*does nothing*/
			}
			
		});
	}
}
