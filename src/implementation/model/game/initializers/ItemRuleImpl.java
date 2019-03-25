package implementation.model.game.initializers;

import java.util.Optional;
import design.model.game.GameRules.ItemRule;
import design.model.game.Item;

public class ItemRuleImpl implements ItemRule {

	private final Class<? extends Item> itemClass;
	private final long spawnDelta; 
	private final double spawnChance;
	private final int max;
	private final Optional<Long> itemDuration;
	private final Optional<Long> effectDuration;
	
	public ItemRuleImpl(Class<? extends Item> itemClass, long spawnDelta, double spawnChance, int max, Optional<Long> itemDuration, Optional<Long> effectDuration) {
		check(itemClass, spawnDelta, spawnChance, max, itemDuration, effectDuration);
		this.itemClass = itemClass;
		this.spawnDelta = spawnDelta;
		this.spawnChance = spawnChance;
		this.max = max;
		this.itemDuration = itemDuration;
		this.effectDuration = effectDuration;
	}
	
	@Override
	public Class<? extends Item> getItemClass() {
		return itemClass;
	}

	@Override
	public long getSpawnDelta() {
		return spawnDelta;
	}

	@Override
	public double getSpawnChance() {
		return spawnChance;
	}

	@Override
	public int getMax() {
		return max;
	}

	@Override
	public Optional<Long> getItemDuration() {
		return itemDuration;
	}

	@Override
	public Optional<Long> getEffectDuration() {
		return effectDuration;
	}
	
	public String toString() {
		String str = "Class:\t\t\t" + itemClass.getSimpleName() + "\n";
		str += "Max on field:\t\t" + max + "\n";
		str += "Spawn delta:\t\t" + spawnDelta + "\n";
		str += "Spawn chance:\t\t" + String.format("%.02f", spawnChance * 100) + "%\n";
		if (itemDuration.isPresent()){
			str += "Item duration:\t\t" + itemDuration.get() + "\n";
		}
		else {
			str += "Item duration:\t\tNO\n";
		}
		if (itemDuration.isPresent()){
			str += "Effect duration:\t" + effectDuration.get() + "\n";
		}
		else {
			str += "Effect duration:\tNO\n";
		}
		return str;
	}
	
	private void check(Class<? extends Item> itemClass, long spawnDelta, double spawnChance, int max, Optional<Long> itemDuration, Optional<Long> effectDuration){
		Utils.throwNullPointer(itemClass == null || itemDuration == null || effectDuration == null, "Null args");
		Utils.throwIllegalState(spawnDelta <= 0, "spawnDelta cannot be zero or less");
		Utils.throwIllegalState(spawnChance <= 0 || spawnChance >1 , "spawnChance must be a number greater or equal than zero and equal or less than one");
		Utils.throwIllegalState(max <= 0 , "max cannot be zero or negative");
		Utils.throwIllegalState(itemDuration.isPresent() && itemDuration.get() <= 0 , "if itemDuration is present it must be greater than zero");
		Utils.throwIllegalState(effectDuration.isPresent() && effectDuration.get() <= 0 , "if effecttDuration is present it must be greater than zero");
	}

}
