package implementation.model.game;

import java.util.HashMap;
import java.util.Map;
import design.model.game.GameRules;
import design.model.game.GameRules.ItemRule;
import design.model.game.Item;

public class ItemCounter {
	
	private final Map<Class<? extends Item>, Integer> map;
	
	public ItemCounter(GameRules gameRules) {
		map = new HashMap<>();
		for (ItemRule item : gameRules.getItemRules()) {
			map.put(item.getItemClass(), 0);
		}
	}
	
	public void applyQuantity(Class<? extends Item> itemClass, int quantity) {
		checkItemClassIsPresent(itemClass);
		int updatedCount = map.get(itemClass) + quantity;
		if (updatedCount < 0) {
			updatedCount = 0;
		}
		map.replace(itemClass, updatedCount);
	}
	
	public int getClassCounter(Class<? extends Item> itemClass) {
		checkItemClassIsPresent(itemClass);
		return map.get(itemClass);
	}
	
	private void checkItemClassIsPresent(Class<? extends Item> itemClass) {
		if (!map.containsKey(itemClass)) {
			throw new IllegalStateException();
		}
	}
}
