package design.model.game;

import java.util.Optional;

public interface Effect extends Runnable{

	public void instantaneousEffect(Snake target);
	
	public void expirationEffect(Field field);
	
	public void attachSnake(Snake snake);
	
	public Optional<Snake> getAttachedSnake();
	
	public Optional<Long> getEffectDuration();
	
	public void incrementDuration(long duration);
	
	public int getComboCounter();
	
}
