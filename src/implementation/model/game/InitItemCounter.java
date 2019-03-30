package implementation.model.game;

import java.util.Map;
import design.model.game.*;
import implementation.model.game.items.BodyPart;
import design.model.game.GameRules.ItemRule;
import design.model.game.InitialGameState.InitialPlayerState;

public class InitItemCounter {
	
	private InitItemCounter() {}
	
	public static void initItemCounter(Map<Class<? extends Item>, Integer> itemCounter, GameRules gameRules, InitialGameState initialGameState) {
		extractPossibleItems(itemCounter, gameRules);
		countBodyParts(itemCounter, initialGameState);
		countOtherItems(itemCounter, initialGameState);
	}

	private static void extractPossibleItems(Map<Class<? extends Item>, Integer> itemCounter, GameRules gameRules) {
		for (ItemRule ir : gameRules.getItemRules()) {
			itemCounter.put(ir.getItemClass(), 0);
		}
	}
	
	private static void countBodyParts(Map<Class<? extends Item>, Integer> itemCounter, InitialGameState initialGameState) {
		int count = 0;
		for (InitialPlayerState ps : initialGameState.getInitialPlayerState()) {
			count += ps.getBodyPoints().size();
		}
		itemCounter.replace(BodyPart.class, count);
	}
	
	private static void countOtherItems(Map<Class<? extends Item>, Integer> itemCounter, InitialGameState initialGameState) {
		for (Item item : initialGameState.getFieldItems()) {
			if (itemCounter.containsKey(item.getClass())) {
				itemCounter.replace(item.getClass(), itemCounter.get(item.getClass()) + 1);
			}
			else {
				throw new IllegalStateException();
			}
		}
	}
}
