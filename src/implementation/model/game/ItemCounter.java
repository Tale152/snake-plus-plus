package implementation.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import design.model.game.GameRules;
import design.model.game.GameRules.ItemRule;
import design.model.game.Item;

public class ItemCounter {
	
	private class AnItem {
		private final Class<? extends Item> itemClass;
		private int counter;
		private long lastSpawnAttempt;
		
		private AnItem(Class<? extends Item> itemClass) {
			this.itemClass = itemClass;
			counter = 0;
			lastSpawnAttempt = 0;
		}
	}
	
	private final List<AnItem> items;
	
	public ItemCounter(GameRules gameRules) {
		items = new ArrayList<>();
		for (ItemRule rule : gameRules.getItemRules()) {
			items.add(new AnItem(rule.getItemClass()));
		}
	}
	
	public int getClassCounter(Class<? extends Item> itemClass) {
		return findItem(itemClass).counter;
	}
	
	public void applyQuantity(Class<? extends Item> itemClass, int quantity) {
		AnItem item = findItem(itemClass);
		item.counter += quantity;
		if (item.counter < 0) {
			item.counter = 0;
		}
	}
	
	public long getLastSpawnAttempt(Class<? extends Item> itemClass) {
		return findItem(itemClass).lastSpawnAttempt;
	}
	
	public void setLastSpawnAttempt(Class<? extends Item> itemClass, long time) {
		AnItem item = findItem(itemClass);
		if (time < 0) {
			throw new IllegalArgumentException();
		}
		if (time < item.lastSpawnAttempt) {
			throw new IllegalStateException();
		}
		item.lastSpawnAttempt = time;
	}
	
	private AnItem findItem(Class<? extends Item> itemClass){
		if (itemClass == null) {
			throw new IllegalArgumentException();
		}
		Optional<AnItem> res = items.stream()
				.filter(i -> {return i.itemClass.equals(itemClass);})
				.findFirst();
		if (res.isPresent()) {
			return res.get();
		}
		throw new IllegalStateException();
	}
}
