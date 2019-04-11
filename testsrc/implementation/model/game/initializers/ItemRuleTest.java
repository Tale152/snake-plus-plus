package implementation.model.game.initializers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

import design.model.game.GameRules.ItemRule;
import implementation.model.game.items.BadApple;

public class ItemRuleTest {
	
	private ItemRule itemRule;
	
	@Test
	public void testGetItemClass() {
		assertEquals(BadApple.class, itemRule.getItemClass());
	}
	
	@Test
	public void testGetSpawnDelta() {
		assertEquals((long) 1e10, itemRule.getSpawnDelta());
	}
	
	@Test
	public void testGetSpawnChance() {
		assertEquals(0.1, itemRule.getSpawnChance(), 0);
	}
	
	@Test
	public void testGetMax() {
		assertEquals(3, itemRule.getMax());
	}
	
	@Test
	public void testGetItemDuration() {
		assertTrue(!(itemRule.getItemDuration().isPresent()));
		itemRule = new ItemRuleImpl(BadApple.class, (long) 1e10, 0.1, 3, Optional.of((long) 1e11), Optional.empty());
		assertTrue(itemRule.getItemDuration().isPresent());
		assertEquals((long) itemRule.getItemDuration().get(), (long) 1e11);
	}
	
	@Test
	public void testGetEffectDuration() {
		assertTrue(!(itemRule.getEffectDuration().isPresent()));
		itemRule = new ItemRuleImpl(BadApple.class, (long) 1e10, 0.1, 3, Optional.empty(), Optional.of((long) 1e11));
		assertTrue(itemRule.getEffectDuration().isPresent());
		assertEquals((long) itemRule.getEffectDuration().get(), (long) 1e11);
	}
	
	public ItemRuleTest() {
		itemRule = new ItemRuleImpl(BadApple.class, (long) 1e10, 0.1, 3, Optional.empty(), Optional.empty());
	}

}
