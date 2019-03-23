package implementation.model.game.initializers;

import java.util.ArrayList;
import java.util.List;
import design.model.game.GameRules;
import implementation.model.game.items.BodyPart;

public class GameRulesImpl implements GameRules{

	private final List<ItemRule> itemRules;
	private final long initialSnakeDelta;
	
	public GameRulesImpl(List<ItemRule> itemRules, long initialSnakeDelta) {
		if (initialSnakeDelta <= 0 || itemRules == null || itemRules.stream().noneMatch(i -> {return i.getItemClass().equals(BodyPart.class);})) {
			throw new IllegalArgumentException();
		}
		this.itemRules = itemRules;
		this.initialSnakeDelta = initialSnakeDelta;
		for (ItemRule rule : this.itemRules) {
			ArrayList<ItemRule> tmp = new ArrayList<>(this.itemRules);
			tmp.remove(rule);
			if (tmp.stream().anyMatch(r -> {return r.getItemClass().equals(rule.getItemClass());})) {
				throw new IllegalStateException();
			}
		}
	}
	
	@Override
	public List<ItemRule> getItemRules() {
		return new ArrayList<>(itemRules);
	}

	@Override
	public long getInitialSnakeDelta() {
		return initialSnakeDelta;
	}

}
