package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;
import design.model.game.Snake;

public class Apple extends ItemAbstract{
	
	protected Apple(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		
		super(point);
		setEffect(new EffectAbstract(expirationTime, effectDuration) {
			
			@Override
			protected void behaviorOnEffectStart(Snake target) {
				target.getProperties().getLength().lengthen(ItemFactory.LENGHT_INCREMENT);
				target.getPlayer().addScore(ItemFactory.SCORE);
			}

			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				target.getProperties().getLength().shorten(target.getProperties().getLength().getLength() - 1);
				target.getPlayer().reduceScore(target.getPlayer().getScore());
			}

		});
		
	}
}
