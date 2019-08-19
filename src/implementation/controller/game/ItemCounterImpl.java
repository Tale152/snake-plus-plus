package implementation.controller.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import design.controller.game.ItemCounter;
import design.model.game.Effect;
import design.model.game.Field;
import design.model.game.GameRules;
import design.model.game.Item;
import design.model.game.ItemRule;

/**
 * @see ItemCounter
 */
public class ItemCounterImpl implements ItemCounter {

    private final List<Countable> allElements;

    private final class Countable {
        private final Class<? extends Effect> effectClass;
        private int current;
        private final int max;
        private long lastSpawnAttempt;

        private Countable(final Class<? extends Effect> effectClass, final int max) {
            this.effectClass = effectClass;
            current = 0;
            this.max = max;
            lastSpawnAttempt = System.currentTimeMillis();
        }
    }

    /**
     * @param field containing elements to check
     * @param gameRules rules that determinate how to handle every type of item to spawn
     */
    public ItemCounterImpl(final Field field, final GameRules gameRules) {
        allElements = new ArrayList<>();
        for (final ItemRule ir : gameRules.getItemRules()) {
            allElements.add(new Countable(ir.getEffectClass(), ir.getMax()));
        }
        for (final Item item : field.getItems()) {
            increase(item.getEffectClass());
        }
    }

    private Optional<Countable> getElement(final Class<? extends Effect> effect) {
        return allElements.stream().filter(e -> {
            return e.effectClass.equals(effect); }).findFirst();
    }

    @Override
    public final boolean increase(final Class<? extends Effect> effect) {
        Countable elem = getElement(effect).get();
        if (elem.current < elem.max) {
            ++elem.current;
            return true;
        }
        return false;
    }

    @Override
    public final boolean decrease(final Class<? extends Effect> effect) {
        Countable elem = getElement(effect).get();
        if (elem.current > 0) {
            --elem.current;
            return true;
        }
        return false;
    }

    @Override
    public final int getCurrent(final Class<? extends Effect> effect) {
        return getElement(effect).get().current;
    }

    @Override
    public final int getMax(final Class<? extends Effect> effect) {
        return getElement(effect).get().max;
    }

    @Override
    public final boolean isMax(final Class<? extends Effect> effect) {
        return getElement(effect).get().current >= getElement(effect).get().max;
    }

    @Override
    public final long getLastSpawnAttempt(final Class<? extends Effect> effect) {
        return getElement(effect).get().lastSpawnAttempt;
    }

    @Override
    public final void setLastSpawnAttempt(final Class<? extends Effect> effect, final long time) {
        getElement(effect).get().lastSpawnAttempt = time;
    }

}
