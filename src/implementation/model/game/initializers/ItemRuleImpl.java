package implementation.model.game.initializers;

import java.util.Optional;
import design.model.game.GameRules.ItemRule;
import design.model.game.Item;

public class ItemRuleImpl implements ItemRule {

	private final Class<Item> itemClass;
	private final long spawnDelta; 
	private final double spawnChance;
	private final int max;
	private final Optional<Long> itemDuration;
	private final Optional<Long> effectDuration;
	
	public ItemRuleImpl(Class<Item> itemClass, long spawnDelta, double spawnChance, int max, Optional<Long> itemDuration, Optional<Long> effectDuration) {
		if (itemClass == null || spawnDelta <= 0 || spawnChance <= 0 || spawnChance >1 
				|| max <= 0 || itemDuration == null || effectDuration == null) {
			throw new IllegalArgumentException();
		}
		if (itemDuration.isPresent() && itemDuration.get() <= 0) {
			throw new IllegalArgumentException();
		}
		if (effectDuration.isPresent() && effectDuration.get() <= 0) {
			throw new IllegalArgumentException();
		}
		
		this.itemClass = itemClass;
		this.spawnDelta = spawnDelta;
		this.spawnChance = spawnChance;
		this.max = max;
		this.itemDuration = itemDuration;
		this.effectDuration = effectDuration;
	}
	
	@Override
	public Class<Item> getItemClass() {
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

}
