package implementation.model.game.initializers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import design.model.game.GameRules;
import design.model.game.GameRules.ItemRule;
import implementation.model.game.items.BodyPart;

public class GameRulesTest {

		private GameRules rules;
		private List<ItemRule> itemRules;
		
		@Test
		public void testSnakeDelta() {
			rules = new GameRulesImpl(itemRules, 500004000L, 1);
			assertEquals(rules.getInitialSnakeDelta(), 500004000L);
		}
		
		@Test
		public void testSnakeMultiplier() {
			rules = new GameRulesImpl(itemRules, 5000000000L, 2.3);
			assertEquals(rules.getInitialSnakeMultiplier(), 2.3, 0);
		}
		
		@Test
		public void testItemRuleList() {
			rules = new GameRulesImpl(itemRules, 1, 1);
			assertTrue(itemRules != rules.getItemRules());
			assertEquals(itemRules, rules.getItemRules());
		}
		
		public GameRulesTest() {
			itemRules = new ArrayList<ItemRule>();
			itemRules.add(new ItemRuleImpl(BodyPart.class, 1000000000L, 0.1, 9999, Optional.empty(), Optional.empty()));
		}
}
