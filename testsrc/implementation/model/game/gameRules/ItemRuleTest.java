package implementation.model.game.gameRules;

import static org.junit.Assert.*;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;
import implementation.model.game.gamerules.ItemRuleImpl;
import implementation.model.game.items.Apple;

public class ItemRuleTest {

	private static ItemRule ir;
	
	private static void assertException(Class<? extends Effect> effectClass, long spawnDelta, double spawnChance, 
			int max, Optional<Long> itemDuration, Optional<Long> effectDuration, Class<? extends Exception> expectedException) {
		try {
			ir = new ItemRuleImpl(effectClass, spawnDelta, spawnChance, max, itemDuration, effectDuration);
			fail(expectedException.getSimpleName() + " expected");
		} catch (Exception e) {
			if (!e.getClass().equals(expectedException)){
				e.printStackTrace();
				fail("wrong exception");
			}
		}
	}
	
	@Test
	public void testInit() {
		assertException(null, 1000, 1.0, 1, Optional.empty(), Optional.empty(), NullPointerException.class);
		assertException(Apple.class, 1000, 1.0, 1, null, Optional.empty(), NullPointerException.class);
		assertException(Apple.class, 1000, 1.0, 1, Optional.empty(), null, NullPointerException.class);
		assertException(Apple.class, -1, 1.0, 1, Optional.empty(), Optional.empty(), IllegalArgumentException.class);
		assertException(Apple.class, 1000, 0, 1, Optional.empty(), Optional.empty(), IllegalArgumentException.class);
		assertException(Apple.class, 1000, 1.0, 0, Optional.empty(), Optional.empty(), IllegalArgumentException.class);
		assertException(Apple.class, 1000, 1.0, 1, Optional.of(0L), Optional.empty(), IllegalArgumentException.class);
		assertException(Apple.class, 1000, 1.0, 1, Optional.empty(), Optional.of(0L), IllegalArgumentException.class);
		ir = new ItemRuleImpl(Apple.class, 1000, 1.0, 1, Optional.empty(), Optional.empty());
		assertEquals(Apple.class, ir.getEffectClass());
		assertEquals(1000, ir.getSpawnDelta());
		assertTrue(1.0==ir.getSpawnChance());
		assertEquals(1, ir.getMax());
		assertEquals(Optional.empty(), ir.getItemDuration());
		assertEquals(Optional.empty(), ir.getEffectDuration());
	}
}
