package implementation.model.game.items;

import java.util.Optional;
import design.model.game.Effect;
import design.model.game.Snake;

public abstract class EffectAbstract implements Effect {

	private Optional<Long> dEffectDuration;
	private Optional<Snake> attachedSnake;
	private int counter;
	
	public EffectAbstract(Optional<Long> dEffectDuration) {
		this.dEffectDuration = dEffectDuration;
		attachedSnake = Optional.empty();
		counter = 1;
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
		if (!attachedSnake.isPresent()) {
			throw new IllegalStateException();
		}
		behaviorOnLastingEffectStart(attachedSnake.get());
		try {
			Thread.sleep(this.getEffectDuration().get());
			//TODO
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		attachedSnake.get().removeEffect(this);
		behaviorOnLastingEffectEnd(attachedSnake.get());
	}
	
	@Override
	public void incrementDuration(long duration) {
		++counter;
		this.dEffectDuration = Optional.of(dEffectDuration.get() + duration);
	}
	
	@Override
	public int getComboCounter() {
		return counter;
	}
	
	protected abstract void behaviorOnLastingEffectStart(Snake snake);
	
	protected abstract void behaviorOnLastingEffectEnd(Snake snake);

}
