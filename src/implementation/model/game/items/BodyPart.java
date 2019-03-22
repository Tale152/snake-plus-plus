package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;

import design.model.game.Snake;

public class BodyPart extends ItemAbstract{
	
	protected BodyPart(Point point, Snake owner) {
		super(point);
		if (owner == null) {
			throw new IllegalArgumentException();
		}
		setEffect(new EffectAbstract(Optional.empty(), Optional.empty()) {
			
			@Override
			protected void behaviorOnEffectStart(Snake target) {
				//if at least one is intangible do nothing
				if (!owner.getProperties().getCollision().getIntangibility() && !target.getProperties().getCollision().getIntangibility()) {
					if (owner.getProperties().getCollision().getInvincibility()) {
						//if owner is invincible but the target is not than kill the target
						if (!target.getProperties().getCollision().getInvincibility()) {
							target.kill();
						}
					}
					else {
						//if owner is not invincible but the target it is than kill the owner
						if (target.getProperties().getCollision().getInvincibility()) {
							owner.kill();
						}
						//otherwise kill the target
						else {
							target.kill();
						}
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
