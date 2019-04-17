package implementation.model.game.items;

import java.io.Serializable;
import java.util.Optional;
import design.model.game.*;
import implementation.model.game.initializers.SerializableOptional;

public abstract class EffectAbstract implements Effect, Serializable {

	private static final long serialVersionUID = 6357063406516445592L;
	private SerializableOptional<Long> effectEndTime;
	private final SerializableOptional<Long> expirationTime;
	private final SerializableOptional<Long> effectDuration;
	
	public EffectAbstract( Optional<Long> expirationTime, Optional<Long> effectDuration) {
		if (expirationTime == null || effectDuration == null) {
			throw new IllegalArgumentException();
		}
		this.effectEndTime = SerializableOptional.empty();
		this.expirationTime = SerializableOptional.fromOptional(expirationTime);
		this.effectDuration = SerializableOptional.fromOptional(effectDuration);
	}
	
	@Override
	public Optional<Long> getEffectEndTime() {
		return effectEndTime.asOptional();
	}

	@Override
	public Optional<Long> getExpirationTime() {
		return expirationTime.asOptional();
	}
	
	protected abstract void behaviorOnEffectStart(Snake target);

	@Override
	public void effectStart(Snake target, long collisionTime) {
		if (effectDuration.asOptional().isPresent()) {
			Optional<Effect> activeEffect = 
					target
						.getEffects()
						.stream()
						.filter(f -> {return this.getClass() == f.getClass();})
						.findFirst();
			if (activeEffect.isPresent()) {
				activeEffect.get().incrementDuration(effectDuration.asOptional().get());
			}
			else {
				effectEndTime = SerializableOptional.fromOptional(Optional.of(collisionTime + effectDuration.asOptional().get()));
				target.addEffect(this);
			}
		}
		behaviorOnEffectStart(target);
	}
	
	protected abstract void behaviorOnEffectEnd(Snake target);
	
	@Override
	public void effectEnd(Snake target) {
		if (effectEndTime.asOptional().isPresent()) {
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
		if (effectEndTime.asOptional().isPresent()) {
			effectEndTime = SerializableOptional.fromOptional(Optional.of(effectEndTime.asOptional().get() + time));
			return true;
		}
		return false;
	}
	
	public String toString() {
		String name = this.getClass().getName().replaceFirst(this.getClass().getPackage().getName() + ".", "");
		String res = "Effect:\t" + name + "\n\tExpires at: ";
		res += expirationTime.asOptional().isPresent() ? expirationTime.asOptional().get() : "NO";
		res += "\n\tDuration: ";
		res += effectDuration.asOptional().isPresent() ? effectDuration.asOptional().get() : "NO";
		res += "\n\tEffect ends at: ";
		res += effectEndTime.asOptional().isPresent() ? effectEndTime.asOptional().get() : "NO";
		return res;
	}

}