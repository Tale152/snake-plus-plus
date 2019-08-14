package implementation.model.game.gameRules;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
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
import implementation.model.game.items.Apple;
import implementation.model.game.items.BadApple;

public class GameRulesTest {
  
  GameRules gameRules;
    
  @Test
  public void testInit() {
    
    WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), 
        true);
    LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
    ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, 5, Optional.empty(),
        Optional.empty());
    List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));
    
    try {
      gameRules = new GameRulesImpl(null, lossC, itemRules, 100L, 1.0, 10L, true);
      fail("Win conditions can not be null");
    } catch (NullPointerException e) {
    } catch (Exception e) {
      fail("wrong exception");
    }
    try {
      gameRules = new GameRulesImpl(winC, null, itemRules, 100L, 1.0, 10L, true);
      fail("Loss conditions can not be null");
    } catch (NullPointerException e) {
    } catch (Exception e) {
      fail("wrong exception");
    }
    try {
      gameRules = new GameRulesImpl(winC, lossC, null, 100L, 1.0, 10L, true);
      fail("Item rules can not be null");
    } catch (NullPointerException e) {
    } catch (Exception e) {
      fail("wrong exception");
    }
    try {
      gameRules = new GameRulesImpl(winC, lossC, itemRules, 0L, 1.0, 10L, true);
      fail("Initial snake delta can not be 0");
    } catch (IllegalArgumentException e) {
    } catch (Exception e) {
      fail("wrong exception");
    }
    try {
      gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, -1, 10L, true);
      fail("Initial snake multiplier can not be negative");
    } catch (IllegalArgumentException e) {
    } catch (Exception e) {
      fail("wrong exception");
    } 
  }
 
  @Test
  public void testGetWinConditions() {
    GameRules gameRules;
    WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), 
        true);
    LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
    ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, 5, Optional.empty(),
        Optional.empty());
    List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));
    gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
    
    assertEquals(gameRules.getWinConditions(), winC);
  }
  
  @Test
  public void testGetLossConditions() {
    GameRules gameRules;
    WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), 
        true);
    LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
    ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, 5, Optional.empty(),
        Optional.empty());
    List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));
    gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
    
    assertEquals(gameRules.getLossConditions(), lossC);
  }
  
  @Test
  public void testGetItemRules() {
    GameRules gameRules;
    WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), 
        true);
    LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
    ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, 5, Optional.empty(),
        Optional.empty());
    ItemRule itemR1 = new ItemRuleImpl(BadApple.class, 100L, 0.5, 5, Optional.empty(),
        Optional.empty());
    List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));
   
    gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
    assertTrue(gameRules.getItemRules().contains(itemRules.get(0)));
    
    itemRules.add(itemR1);
    gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
    assertEquals(gameRules.getItemRules().size(), 2);
    assertTrue(gameRules.getItemRules().contains(itemRules.get(0)));
    assertTrue(gameRules.getItemRules().contains(itemRules.get(1)));
    
    assertTrue(gameRules.getItemRules() != gameRules.getItemRules());
  }
  
  @Test
  public void testGetInitialSnakeDelta() {
    GameRules gameRules;
    WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), 
        true);
    LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
    ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, 5, Optional.empty(),
        Optional.empty());
    List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));
   
    gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
    assertEquals(gameRules.getInitialSnakeDelta(),100L);
  }
  
  @Test
  public void testGetInitialSnakeMultiplier() {
    GameRules gameRules;
    WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), 
        true);
    LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
    ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, 5, Optional.empty(),
        Optional.empty());
    List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));
   
    gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
    assertTrue(gameRules.getInitialSnakeMultiplier() == 1.0);
  }
  
  @Test
  public void testGetInitialTime() {
    GameRules gameRules;
    WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), 
        true);
    LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
    ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, 5, Optional.empty(),
        Optional.empty());
    List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));
   
    gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
    assertEquals(gameRules.getInitialTime(),10L);
  }
  
  @Test
  public void testIsTimeGoingForward() {
    GameRules gameRules;
    WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), 
        true);
    LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
    ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, 5, Optional.empty(),
        Optional.empty());
    List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));
   
    gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
    assertTrue(gameRules.isTimeGoingForward());
    
    gameRules = new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, false);
    assertFalse(gameRules.isTimeGoingForward());
  } 
}
