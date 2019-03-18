package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;

import design.model.game.Snake;

public class ScoreEarning extends ItemAbstract{

	protected ScoreEarning(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		super(point);
		setEffect(new EffectAbstract(expirationTime, effectDuration) {
			
			private int multiplier = 1;
			private Snake targetSnake;
			
			@Override
			public boolean incrementDuration(long time) {
				if (targetSnake == null) {
					throw new IllegalStateException();
				}
				multiplier *=2;
				behaviorOnEffectStart(targetSnake);
				return super.incrementDuration(time);
			}
			
			@Override
			protected void behaviorOnEffectStart(Snake target) {
				if (targetSnake == null) {
					targetSnake = target;
				}
				target.getPlayer().addScore(ItemFactory.SCORE * multiplier);
			}
			
			@Override
			protected void behaviorOnEffectEnd(Snake target) {
				/*This instance of effect will be removed from snake, so it's like resetting multiplier*/
			}
			
			@Override 
			public String toString() {
				return super.toString() + "\n\tMultiplier: " + multiplier;
			}
			
		});
	}

	
}
