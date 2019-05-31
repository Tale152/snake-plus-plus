package implementation.controller.game;

import design.controller.game.ItemCounter;
import design.model.game.*;
import java.util.*;

public class ItemCounterImpl implements ItemCounter {
	
	private class Countable {
		private final Class<? extends Effect> effectClass;
		private int current;
		private final int max;
		private long lastSpawnAttempt;
		
		private Countable(Class<? extends Effect> effectClass, int max) {
			this.effectClass = effectClass;
			current = 0;
			this.max = max;
			lastSpawnAttempt = System.currentTimeMillis();
		}
		
	}
	
	private final List<Countable> allElements;
	
	public ItemCounterImpl(Field field, GameRules gameRules) {
		allElements = new ArrayList<>();
		for (ItemRule ir : gameRules.getItemRules()) {
			allElements.add(new Countable(ir.getEffectClass(), ir.getMax()));
		}
		for (Item item : field.getItems()) {
			increase(item.getEffectClass());
		}
	}
	
	private Optional<Countable> getElement(Class<? extends Effect> effect){
		return allElements.stream().filter(e -> {return e.effectClass.equals(effect);}).findFirst();
	}
	
	@Override
	public boolean increase(Class<? extends Effect> effect) {
		Countable elem = getElement(effect).get();
		if (elem.current < elem.max) {
			++elem.current;
			return true;
		}
		return false;
	}

	@Override
	public boolean decrease(Class<? extends Effect> effect) {
		Countable elem = getElement(effect).get();
		if (elem.current > 0) {
			--elem.current;
			return true;
		}
		return false;
	}

	@Override
	public int getCurrent(Class<? extends Effect> effect) {
		return getElement(effect).get().current;
	}

	@Override
	public int getMax(Class<? extends Effect> effect) {
		return getElement(effect).get().max;
	}

	@Override
	public boolean isMax(Class<? extends Effect> effect) {
		Countable elem = getElement(effect).get();
		return elem.current >= elem.max;
	}

	@Override
	public long getLastSpawnAttempt(Class<? extends Effect> effect) {
		Countable elem = getElement(effect).get();
		return elem.lastSpawnAttempt;
	}

	@Override
	public void setLastSpawnAttempt(Class<? extends Effect> effect, long time) {
		getElement(effect).get().lastSpawnAttempt = time;
	}

}
