package implementation.model.game.initializers;

import java.util.ArrayList;
import java.util.List;
import design.model.game.GameRules;
import implementation.model.game.items.BodyPart;

public class GameRulesImpl implements GameRules{

	private final List<ItemRule> itemRules;
	private final long initialSnakeDelta;
	
	public GameRulesImpl(List<ItemRule> itemRules, long initialSnakeDelta) {
		check(itemRules, initialSnakeDelta);
		this.itemRules = itemRules;
		this.initialSnakeDelta = initialSnakeDelta;
	}
	
	@Override
	public List<ItemRule> getItemRules() {
		return new ArrayList<>(itemRules);
	}

	@Override
	public long getInitialSnakeDelta() {
		return initialSnakeDelta;
	}
	
	public String toString() {
		String res = "Starting snake delta: " + initialSnakeDelta + "\n";
		res += "Item rules: " + itemRules.size() + "\n";
		for (ItemRule rule : itemRules) {
			res += "--------\n" + rule.toString();
		}
		return res;
	}
	
	private void check(List<ItemRule> itemRules, long initialSnakeDelta) {
		Utils.throwNullPointer(itemRules == null, "null argument");
		Utils.throwIllegalState(initialSnakeDelta <= 0, "initialSnakeDelta cannot be negative");
		boolean bodyPartPresent = itemRules.stream().noneMatch(i -> {return i.getItemClass().equals(BodyPart.class);});
		Utils.throwIllegalState(bodyPartPresent, "itemRules MUST contains BodyPart");
		for (ItemRule rule : this.itemRules) {
			ArrayList<ItemRule> tmp = new ArrayList<>(this.itemRules);
			tmp.remove(rule);
			boolean checkDuplicate = tmp.stream().anyMatch(r -> {return r.getItemClass().equals(rule.getItemClass());});
			Utils.throwIllegalState(checkDuplicate, "Two or more entries in itemRules are equals");
		}
	}

}
