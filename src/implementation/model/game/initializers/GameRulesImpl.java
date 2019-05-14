package implementation.model.game.initializers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import design.model.game.GameRules;
import implementation.model.game.items.BodyPart;

public class GameRulesImpl implements GameRules, Serializable{

	private static final long serialVersionUID = 4741863879306662326L;
	private final List<ItemRule> itemRules;
	private final long initialSnakeDelta;
	private final double initialSnakeMultiplier;
	private final boolean timeGoingForward;
	
	public GameRulesImpl(List<ItemRule> itemRules, long initialSnakeDelta, double initialSnakeMultiplier, boolean timeGoingForward) {
		check(itemRules, initialSnakeDelta);
		this.itemRules = itemRules;
		this.initialSnakeDelta = initialSnakeDelta;
		this.initialSnakeMultiplier = initialSnakeMultiplier;
		this.timeGoingForward = timeGoingForward;
	}
	
	@Override
	public List<ItemRule> getItemRules() {
		return new ArrayList<>(itemRules);
	}

	@Override
	public long getInitialSnakeDelta() {
		return initialSnakeDelta;
	}
	
	@Override
	public double getInitialSnakeMultiplier() {
		return initialSnakeMultiplier;
	}
	
	@Override
	public boolean isTimeGoingForward() {
		return timeGoingForward;
	}
	
	public String toString() {
		String res = "Starting snake delta: " + initialSnakeDelta + "\n";
		res += "Starting snake multiplier: " + initialSnakeMultiplier + "\n";
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
		Utils.throwIllegalState(!bodyPartPresent, "itemRules CANNOT contain BodyPart");
		for (ItemRule rule : itemRules) {
			ArrayList<ItemRule> tmp = new ArrayList<>(itemRules);
			tmp.remove(rule);
			boolean checkDuplicate = tmp.stream().anyMatch(r -> {return r.getItemClass().equals(rule.getItemClass());});
			Utils.throwIllegalState(checkDuplicate, "Two or more entries in itemRules are equals");
		}
	}

}
