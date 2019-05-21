package implementation.model.game.items;

import java.util.Optional;
import design.model.game.Effect;
import design.model.game.Snake;

public abstract class EffectAbstract implements Effect {

	private Optional<Long> dEffectDuration;
	private Optional<Snake> attachedSnake;
	
	public EffectAbstract(Optional<Long> dEffectDuration) {
		this.dEffectDuration = dEffectDuration;
		attachedSnake = Optional.empty();
	}

	@Override
	public void attachSnake(Snake snake) {
		attachedSnake = Optional.of(snake);
	}
	
	@Override
	public Optional<Snake> getAttachedSnake(){
		return attachedSnake;
	}
	
	@Override
	public Optional<Long> getEffectDuration(){
		return dEffectDuration;
	}
	
	@Override
	public void run() {
		behaviorOnLastingEffectStart();
		try {
			Thread.sleep(this.getEffectDuration().get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		attachedSnake.get().removeEffect(this);
		behaviorOnLastingEffectEnd();
	}
	
	@Override
	public void incrementDuration(long duration) {
		if (dEffectDuration.isPresent()) {
			dEffectDuration = Optional.of(dEffectDuration.get() + duration);
		}
	}
	
	protected abstract void behaviorOnLastingEffectStart();
	
	protected abstract void behaviorOnLastingEffectEnd();

}
