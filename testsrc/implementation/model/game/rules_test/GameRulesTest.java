package implementation.model.game.rules_test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import design.model.game.GameRules;
import design.model.game.ItemRule;
import design.model.game.LossConditions;
import design.model.game.WinConditions;
import implementation.model.game.rules.GameRulesImpl;
import implementation.model.game.rules.ItemRuleImpl;
import implementation.model.game.rules.LossConditionsImpl;
import implementation.model.game.rules.WinConditionsImpl;
import implementation.model.game.items.Apple;
import implementation.model.game.items.BadApple;

/**
 * Tests regarding game rules. 
 */
public class GameRulesTest {

    private static final int MAX_PRESENCE = 5;

    private GameRules gameRules;
    private final WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), 
            true);
    private final LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
    private final ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, MAX_PRESENCE, Optional.empty(), Optional.empty());
    private final List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));

    /**
     * Used to test if a game rule is initialized correctly.
     */
    @Test
    public void testInit() {
        this.gameRules = new GameRulesImpl(winC, lossC, itemRules, 10L, 1.0, 0L, true);
        assertNotNull("Check if the game rule is initialized", this.gameRules);
    }

    /**
     * Used to test if the game rule return a win condition correctly.
     */
    @Test
    public void testGetWinConditions() {
        gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
        assertEquals("Check if the game rule return correctly a win condition",
                gameRules.getWinConditions(), winC);
    }

    /**
     * Used to test if the game rule return a loss condition correctly.
     */
    @Test
    public void testGetLossConditions() {
        gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
        assertEquals("Check if the loss condition of the game rule is the right one",
                gameRules.getLossConditions(), lossC);
    }

    /**
     * Used to test if the game rule return a item rule correctly.
     */
    @Test
    public void testGetItemRules() {
        GameRules gameRules;
        final ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, MAX_PRESENCE, Optional.empty(), Optional.empty());
        final ItemRule itemR1 = new ItemRuleImpl(BadApple.class, 100L, 0.5, MAX_PRESENCE, Optional.empty(), Optional.empty());
        final List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));

        gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
        assertTrue("Check if the game rule contains the item rule just added",
                gameRules.getItemRules().contains(itemRules.get(0)));

        itemRules.add(itemR1);
        gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
        assertEquals("Check if the game rule contains 2 item rules", gameRules.getItemRules().size(), 2);
        assertTrue("Check if the game rule contains the first item rule added",
                gameRules.getItemRules().contains(itemRules.get(0)));
        assertTrue("Check if the game rule contains the second item rule added",
                gameRules.getItemRules().contains(itemRules.get(1)));
        assertNotSame("Check if the item rule of the game rule is a safe copy of the internal list",
                gameRules.getItemRules(), gameRules.getItemRules());
    }

    /**
     * Used to test if a game rule return the delta correctly.
     */
    @Test
    public void testGetInitialSnakeDelta() {
        gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
        assertEquals("Check if the delta is 100L", gameRules.getInitialSnakeDelta(), 100L);
    }

    /**
     * Used to test if a game rule return the snake multiplier correctly.
     */
    @Test
    public void testGetInitialSnakeMultiplier() {
    gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
    assertEquals("Check if the snake multiplier is 1.0", gameRules.getInitialSnakeMultiplier(), 1.0, 0);
  }

    /**
     * Used to test if a game rule return the initial time correctly.
     */
  @Test
  public void testGetInitialTime() {
    gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
    assertEquals("Check if the intial time is 10L", gameRules.getInitialTime(), 10L);
  }

  /**
   * Used to test if the time is going forward or backward correctly.
   */
  @Test
  public void testIsTimeGoingForward() {
    GameRules gameRules;

    gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
    assertTrue("Check if the time is actually going forward", gameRules.isTimeGoingForward());

    gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, false);
    assertFalse("Check if the time is actually going barckward", gameRules.isTimeGoingForward());
  } 

}
