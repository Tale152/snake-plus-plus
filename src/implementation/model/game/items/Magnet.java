package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;

import design.model.game.Snake;

public class Magnet extends ItemAbstract{

	private static final long serialVersionUID = 8592819098486480431L;
	private final static int EXTENDED_RADIUS = 2;
	private final static int NORMAL_RADIUS = 1;
	
	protected Magnet(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		super(point);
		setEffect(new EffectAbstract(expirationTime, effectDuration) {
			
			private static final long serialVersionUID = 5272402271763112307L;

			@Override
			protected void behaviorOnEffectStart(Snake target) {
				if (effectDuration.isPresent()) {
					target.getProperties().getPickup().setPickupRadius(EXTENDED_RADIUS);
				}
			}
			
			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				target.getProperties().getPickup().setPickupRadius(NORMAL_RADIUS);
			}
			
		});
	}

}
