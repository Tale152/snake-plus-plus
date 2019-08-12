package implementation.model.game.gameRules;

import java.util.Optional;
import design.model.game.Effect;
import design.model.game.ItemRule;

public class ItemRuleImpl implements ItemRule {

	private final Class<? extends Effect> effectClass;
	private final long spawnDelta;
	private final double spawnChance;
	private final int max;
	private final Optional<Long> itemDuration;
	private final Optional<Long> effectDuration;
	
	private void checkArguments(Class<? extends Effect> effectClass, long spawnDelta, double spawnChance, 
			int max, Optional<Long> itemDuration, Optional<Long> effectDuration) {
		if (effectClass == null || itemDuration == null || effectDuration == null) {
			throw new NullPointerException();
		}
		if (spawnDelta <= 0L || spawnChance <= 0 || max <= 0) {
			throw new IllegalArgumentException();
		}
		if (itemDuration.isPresent() && itemDuration.get() <= 0) {
			throw new IllegalArgumentException();
		}
		if (effectDuration.isPresent() && effectDuration.get() <= 0) {
			throw new IllegalArgumentException();
		}
	}
	
	public ItemRuleImpl(Class<? extends Effect> effectClass, long spawnDelta, double spawnChance, 
			int max, Optional<Long> itemDuration, Optional<Long> effectDuration) {
		checkArguments(effectClass, spawnDelta, spawnChance, max, itemDuration, effectDuration);
		this.effectClass = effectClass;
		this.spawnDelta = spawnDelta;
		this.spawnChance = spawnChance;
		this.max = max;
		this.itemDuration = itemDuration;
		this.effectDuration = effectDuration;
	}
	
	@Override
	public Class<? extends Effect> getEffectClass() {
		return effectClass;
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
