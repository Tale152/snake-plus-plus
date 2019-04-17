package implementation.model.game.initializers;

import java.io.Serializable;
import java.util.Optional;
import design.model.game.GameRules.ItemRule;
import implementation.model.game.items.BodyPart;
import design.model.game.Item;

public class ItemRuleImpl implements ItemRule, Serializable {

	private static final long serialVersionUID = 9159991708762970796L;
	private final Class<? extends Item> itemClass;
	private final long spawnDelta; 
	private final double spawnChance;
	private final int max;
	private final SerializableOptional<Long> itemDuration;
	private final SerializableOptional<Long> effectDuration;
	
	public ItemRuleImpl(Class<? extends Item> itemClass, long spawnDelta, double spawnChance, int max, Optional<Long> itemDuration, Optional<Long> effectDuration) {
		check(itemClass, spawnDelta, spawnChance, max, itemDuration, effectDuration);
		this.itemClass = itemClass;
		this.spawnDelta = spawnDelta;
		this.spawnChance = spawnChance;
		this.max = max;
		this.itemDuration = SerializableOptional.fromOptional(itemDuration);
		this.effectDuration = SerializableOptional.fromOptional(effectDuration);
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
		return itemDuration.asOptional();
	}

	@Override
	public Optional<Long> getEffectDuration() {
		return effectDuration.asOptional();
	}
	
	public String toString() {
		String str = "Class:\t\t\t" + itemClass.getSimpleName() + "\n";
		str += "Max on field:\t\t" + max + "\n";
		str += "Spawn delta:\t\t" + spawnDelta + "\n";
		str += "Spawn chance:\t\t" + String.format("%.02f", spawnChance * 100) + "%\n";
		if (itemDuration.asOptional().isPresent()){
			str += "Item duration:\t\t" + itemDuration.asOptional().get() + "\n";
		}
		else {
			str += "Item duration:\t\tNO\n";
		}
		if (itemDuration.asOptional().isPresent()){
			str += "Effect duration:\t" + effectDuration.asOptional().get() + "\n";
		}
		else {
			str += "Effect duration:\tNO\n";
		}
		return str;
	}
	
	private void check(Class<? extends Item> itemClass, long spawnDelta, double spawnChance, int max, Optional<Long> itemDuration, Optional<Long> effectDuration){
		Utils.throwNullPointer(itemClass == null || itemDuration == null || effectDuration == null, "Null args");
		Utils.throwIllegalState(itemClass.equals(BodyPart.class), "An ItemRule cannot define a rule for a BodyPart");
		Utils.throwIllegalState(spawnDelta <= 0, "spawnDelta cannot be zero or less");
		Utils.throwIllegalState(spawnChance <= 0 || spawnChance >1 , "spawnChance must be a number greater or equal than zero and equal or less than one");
		Utils.throwIllegalState(max <= 0 , "max cannot be zero or negative");
		Utils.throwIllegalState(itemDuration.isPresent() && itemDuration.get() <= 0 , "if itemDuration is present it must be greater than zero");
		Utils.throwIllegalState(effectDuration.isPresent() && effectDuration.get() <= 0 , "if effecttDuration is present it must be greater than zero");
	}

}
