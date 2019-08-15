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

public class ItemCounterImpl implements ItemCounter {

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

    private final List<Countable> allElements;

    public ItemCounterImpl(final Field field,final GameRules gameRules) {
        allElements = new ArrayList<>();
        for (ItemRule ir : gameRules.getItemRules()) {
            allElements.add(new Countable(ir.getEffectClass(), ir.getMax()));
        }
        for (Item item : field.getItems()) {
            increase(item.getEffectClass());
        }
    }

    private Optional<Countable> getElement(final Class<? extends Effect> effect) {
        return allElements.stream().filter(e -> {
            return e.effectClass.equals(effect); }).findFirst();
    }

    @Override
    public boolean increase(final Class<? extends Effect> effect) {
        Countable elem = getElement(effect).get();
        if (elem.current < elem.max) {
            ++elem.current;
            return true;
        }
        return false;
    }

    @Override
    public boolean decrease(final Class<? extends Effect> effect) {
        Countable elem = getElement(effect).get();
        if (elem.current > 0) {
            --elem.current;
            return true;
        }
        return false;
    }

    @Override
    public int getCurrent(final Class<? extends Effect> effect) {
        return getElement(effect).get().current;
    }

    @Override
    public int getMax(final Class<? extends Effect> effect) {
        return getElement(effect).get().max;
    }

    @Override
    public boolean isMax(final Class<? extends Effect> effect) {
        Countable elem = getElement(effect).get();
        return elem.current >= elem.max;
    }

    @Override
    public long getLastSpawnAttempt(final Class<? extends Effect> effect) {
        Countable elem = getElement(effect).get();
        return elem.lastSpawnAttempt;
    }

    @Override
    public void setLastSpawnAttempt(final Class<? extends Effect> effect, final long time) {
        getElement(effect).get().lastSpawnAttempt = time;
    }

}
