package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;
import design.model.game.Snake;

public class BadApple extends ItemAbstract{
	
	protected BadApple(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		
		super(point);
		setEffect(new EffectAbstract(expirationTime, effectDuration) {
			
			private int multiplier = 2;
			private Snake targetSnake;
			
			@Override
			public boolean incrementDuration(long time) {
				if (targetSnake == null) {
					throw new IllegalStateException();
				}
				++multiplier;
				behaviorOnEffectStart(targetSnake);
				return super.incrementDuration(time);
			}
			
			@Override
			protected void behaviorOnEffectStart(Snake target) {
				if (targetSnake == null) {
					targetSnake = target;
				}
				target.getProperties().getLength().shorten(target.getProperties().getLength().getLength() / 2);
				target.getPlayer().reduceScore(target.getPlayer().getScore()/2);
			}

			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				target.getProperties().getLength().lengthen((target.getProperties().getLength().getLength() * multiplier) - target.getProperties().getLength().getLength());
			}
			
		});
		
	}
}
