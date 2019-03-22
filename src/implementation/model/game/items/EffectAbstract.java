package implementation.model.game.items;

import java.util.Optional;
import design.model.game.*;

public abstract class EffectAbstract implements Effect {

	private Optional<Long> effectEndTime;
	private final Optional<Long> expirationTime;
	private final Optional<Long> effectDuration;
	
	public EffectAbstract( Optional<Long> expirationTime, Optional<Long> effectDuration) {
		if (expirationTime == null || effectDuration == null) {
			throw new IllegalArgumentException();
		}
		this.effectEndTime = Optional.empty();
		this.expirationTime = expirationTime;
		this.effectDuration = effectDuration;
	}
	
	@Override
	public Optional<Long> getEffectEndTime() {
		return effectEndTime;
	}

	@Override
	public Optional<Long> getExpirationTime() {
		return expirationTime;
	}
	
	protected abstract void behaviorOnEffectStart(Snake target);

	@Override
	public void effectStart(Snake target, long collisionTime) {
		if (effectDuration.isPresent()) {
			Optional<Effect> activeEffect = 
					target
						.getEffects()
						.stream()
						.filter(f -> {return this.getClass() == f.getClass();})
						.findFirst();
			if (activeEffect.isPresent()) {
				activeEffect.get().incrementDuration(effectDuration.get());
			}
			else {
				effectEndTime = Optional.of(collisionTime + effectDuration.get());
				target.addEffect(this);
			}
		}
		behaviorOnEffectStart(target);
	}
	
	protected abstract void behaviorOnEffectEnd(Snake target);
	
	@Override
	public void effectEnd(Snake target) {
		if (effectEndTime.isPresent()) {
			behaviorOnEffectEnd(target);
		}
		else {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public void expirationEffect(Field field){
	  //TODO
	}
	
	@Override
	public boolean incrementDuration(long time) {
		if (effectEndTime.isPresent()) {
			effectEndTime = Optional.of(effectEndTime.get() + time);
			return true;
		}
		return false;
	}
	
	public String toString() {
		String name = this.getClass().getName().replaceFirst(this.getClass().getPackage().getName() + ".", "");
		String res = "Effect:\t" + name + "\n\tExpires at: ";
		res += expirationTime.isPresent() ? expirationTime.get() : "NO";
		res += "\n\tDuration: ";
		res += effectDuration.isPresent() ? effectDuration.get() : "NO";
		res += "\n\tEffect ends at: ";
		res += effectEndTime.isPresent() ? effectEndTime.get() : "NO";
		return res;
	}

}