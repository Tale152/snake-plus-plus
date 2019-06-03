package implementation.model.game.items;

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import design.model.game.*;


public class ItemImpl extends CollidableAbstract implements Item  {

	private boolean eaten;
	private final Field field;
	private final Optional<Long> dExpire;
	private final Optional<Long> dEffectDuration;
	private final Class<? extends Effect> effectClass;
	
	protected ItemImpl(Field field, Point point, Class<? extends Effect> effectClass, Optional<Long> dExpire, Optional<Long> dEffectDuration) {
		super(point);
		if(dExpire == null) {
			System.out.println("CAAZOOOOOO \n\n");
		}
		field.addItem(this);
		this.field = field;
		this.effectClass = effectClass;
		this.dExpire = dExpire;
		this.dEffectDuration = dEffectDuration;
		eaten = false;
	}

	@Override
	public void onCollision(Snake collider) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(!collider.getProperties().getCollisionProperty().getIntangibility()) {
			eaten = true;
		}
		Constructor<? extends Effect> constructor = effectClass.getConstructor(Optional.class);
		Effect effect = constructor.newInstance(dEffectDuration);
		effect.instantaneousEffect(collider);
		if (dEffectDuration.isPresent()) {
			collider.addEffect(effect);
		}
		field.removeItem(this);
	}

	@Override
	public void run() {
		if (dExpire.isPresent()) {
			try {
				Thread.sleep(dExpire.get());
				if (!eaten) {
					field.removeItem(this);
					Constructor<? extends Effect> constructor = effectClass.getConstructor(Optional.class);
					Effect effect = constructor.newInstance(dEffectDuration);
					effect.expirationEffect(field);
				}
			} catch (InterruptedException | InstantiationException | IllegalAccessException | IllegalArgumentException | 
					InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public Class<? extends Effect> getEffectClass() {
		return effectClass;
	}

}
