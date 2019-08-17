package implementation.model.game.rules_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Optional;
import org.junit.Test;

import design.model.game.Effect;
import design.model.game.ItemRule;
import implementation.model.game.rules.ItemRuleImpl;
import implementation.model.game.items.Apple;

/**
 * Tests regarding item rules.
 */
public class ItemRuleTest {

    private static ItemRule ir;

    private static void assertException(final Class<? extends Effect> effectClass, final long spawnDelta, final double spawnChance, 
            final int max, final Optional<Long> itemDuration, final Optional<Long> effectDuration, final Class<? extends Exception> expectedException) {
        try {
            ir = new ItemRuleImpl(effectClass, spawnDelta, spawnChance, max, itemDuration, effectDuration);
            fail(expectedException.getSimpleName() + " expected");
        } catch (Exception e) {
            if (!e.getClass().equals(expectedException)) {
                e.printStackTrace();
                fail("wrong exception");
            }
        }
    }

    /**
     * Used to test if an item rule can be initialized correctly.
     */
    @Test
    public void testInit() {
        assertException(Apple.class, -1, 1.0, 1, Optional.empty(), Optional.empty(), IllegalArgumentException.class);
        assertException(Apple.class, 1000, 0, 1, Optional.empty(), Optional.empty(), IllegalArgumentException.class);
        assertException(Apple.class, 1000, 1.0, 0, Optional.empty(), Optional.empty(), IllegalArgumentException.class);
        assertException(Apple.class, 1000, 1.0, 1, Optional.of(0L), Optional.empty(), IllegalArgumentException.class);
        assertException(Apple.class, 1000, 1.0, 1, Optional.empty(), Optional.of(0L), IllegalArgumentException.class);
        ir = new ItemRuleImpl(Apple.class, 1000, 1.0, 1, Optional.empty(), Optional.empty());
        assertEquals("Check if the item rule effect class is the same of the item",
                Apple.class, ir.getEffectClass());
        assertEquals("Check if the spawn delta is 1000", 1000, ir.getSpawnDelta());
        assertEquals("Check if the spawn chance is 1.0", ir.getSpawnChance(), 1.0, 0);
        assertEquals("Check if the max number of item is 1", 1, ir.getMax());
        assertEquals("Check if the item duration is empty", Optional.empty(), ir.getItemDuration());
        assertEquals("Check it the effect duration is empty", Optional.empty(), ir.getEffectDuration());
    }

}
