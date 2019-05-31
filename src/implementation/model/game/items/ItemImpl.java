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
		field.addItem(this);
		this.field = field;
		this.effectClass = effectClass;
		this.dExpire = dExpire;
		this.dEffectDuration = dEffectDuration;
		eaten = false;
	}

	@Override
	public void onCollision(Snake collider) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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
				keepAlive();
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
	
	private synchronized void keepAlive() throws InterruptedException {
		long startingTime = System.currentTimeMillis();					//mi segno il momento in cui son partito
		long timeToWait = dExpire.get();								//e quanto tempo devo aspettare
		while(true) {
			wait(timeToWait);											//aspetto o un notify o il tempo 
			long deltaT = System.currentTimeMillis() - startingTime;	//quanto tempo è passato da quando mi ero addormentato
			if (deltaT >= timeToWait) {									//è effettivamente passato il tempo necessario
				break;													//adiòs
			}
			else {														//prima che io scadessi mi hanno mandato il notify, ergo hanno chiesto di entrare in pausa
				wait();													//quindi aspetto che mi diano segnale che la pausa è finita
				startingTime = System.currentTimeMillis();				//affinchè il confronto di prima funzioni questo momento è il nuovo starting time
				timeToWait -= deltaT;									//e il tempo da aspettare quindi cala del tempo già passato prima
			}		
		}
	}

	@Override
	public void setEaten() {
		eaten = true;
	}

	@Override
	public Class<? extends Effect> getEffectClass() {
		return effectClass;
	}

}
