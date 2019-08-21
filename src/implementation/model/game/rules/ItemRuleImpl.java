package implementation.model.game.rules;

import java.util.Optional;
import design.model.game.Effect;
import design.model.game.ItemRule;

/**
 * @see ItemRule
 * @see GameRules
 * @see Item
 * @see Effect
 */
public class ItemRuleImpl implements ItemRule {

    private final Class<? extends Effect> effectClass;
    private final long spawnDelta;
    private final double spawnChance;
    private final int max;
    private final Optional<Long> itemDuration;
    private final Optional<Long> effectDuration;

    private void checkArguments(final long spawnDelta, final double spawnChance, final int max, 
            final Optional<Long> itemDuration, final Optional<Long> effectDuration) {
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

    /**
     * @param effectClass that defines the behavior of this item
     * @param spawnDelta milliseconds to wait before trying again to spawn this item
     * @param spawnChance chance (between 0 and 1) of spawning this item every spawnDelta
     * @param max number of max Item of this effect class that can spawn simultaneously
     * @param itemDuration milliseconds that define how long this item will stay on field before disappearing
     * (empty if you don't want it to disappear)
     * @param effectDuration milliseconds that define how long this item's effect lasting effect will last
     * (empty if you don't want to activate lasting effect)
     */
    public ItemRuleImpl(final Class<? extends Effect> effectClass, final long spawnDelta, final double spawnChance, 
            final int max, final Optional<Long> itemDuration, final Optional<Long> effectDuration) {
        checkArguments(spawnDelta, spawnChance, max, itemDuration, effectDuration);
        this.effectClass = effectClass;
        this.spawnDelta = spawnDelta;
        this.spawnChance = spawnChance;
        this.max = max;
        this.itemDuration = itemDuration;
        this.effectDuration = effectDuration;
    }

    @Override
    public final Class<? extends Effect> getEffectClass() {
        return effectClass;
    }

    @Override
    public final long getSpawnDelta() {
        return spawnDelta;
    }

    @Override
    public final double getSpawnChance() {
        return spawnChance;
    }

    @Override
    public final int getMax() {
        return max;
    }

    @Override
    public final Optional<Long> getItemDuration() {
        return itemDuration;
    }

    @Override
    public final Optional<Long> getEffectDuration() {
        return effectDuration;
    }

}
