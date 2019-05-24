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
	
	public ItemRuleImpl(Class<? extends Effect> effectClass, long spawnDelta, double spawnChance, 
			int max, Optional<Long> itemDuration, Optional<Long> effectDuration) {
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
