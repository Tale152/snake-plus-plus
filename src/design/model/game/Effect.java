package design.model.game;

import java.util.Optional;

public interface Effect {
	
	void effectStart(Snake target, long collisionTime);
	
	void effectEnd(Snake target);
	
	void expirationEffect(Field field);
	
	Optional<Long> getEffectEndTime();
	
	Optional<Long> getExpirationTime();
	
	void incrementDuration(long time);

}
