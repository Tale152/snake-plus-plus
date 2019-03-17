package design.model.game;

import java.util.Optional;

public interface Effect {
	
	public void effectStart(Snake target, long collisionTime);
	
	public void effectEnd(Snake target);
	
	public void expirationEffect(Field field);
	
	public Optional<Long> getEffectEndTime();
	
	public Optional<Long> getExpirationTime();
	
	public boolean incrementDuration(long time);

}
