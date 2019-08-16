package implementation.model.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import design.model.game.Field;

import design.model.game.GameModel;
import design.model.game.GameRules;
import design.model.game.ItemRule;
import design.model.game.LossConditions;
import design.model.game.WinConditions;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.gamerules.GameRulesImpl;
import implementation.model.game.gamerules.ItemRuleImpl;
import implementation.model.game.gamerules.LossConditionsImpl;
import implementation.model.game.gamerules.WinConditionsImpl;
import implementation.model.game.items.Apple;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

public class GameModelTest {

  GameModel gameModel;
  
  @Test
  public void testInit() {
    
    WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), 
        true);
    LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
    ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, 5, Optional.empty(),
        Optional.empty());
    List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));
    GameRules gameRules =  new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
    Field field = new FieldImpl(new Point(10,10));

    try {
      this.gameModel = new GameModelImpl(null, gameRules);
      fail("Field can not be null");
    } catch (NullPointerException e) {
    } catch (Exception e) {
      fail("wrong exception");
    }
    try {
      this.gameModel = new GameModelImpl(field, null);
      fail("Game rules can not be null");
    } catch (NullPointerException e) {
    } catch (Exception e) {
      fail("wrong exception");
    }
  }

  @Test
  public void testGetField() {

    WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), 
        true);
    LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
    ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, 5, Optional.empty(), 
        Optional.empty());
    List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));
    GameRules gameRules =  new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
    Field field = new FieldImpl(new Point(10,10));

    this.gameModel = new GameModelImpl(field, gameRules);
    assertEquals(this.gameModel.getField(), field);
  }

  @Test
  public void testGetGameRules() {

    WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), 
        true);
    LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
    ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, 5, Optional.empty(), 
        Optional.empty());
    List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));
    GameRules gameRules =  new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
    Field field = new FieldImpl(new Point(10,10));

    this.gameModel = new GameModelImpl(field, gameRules);
    assertEquals(this.gameModel.getGameRules(), gameRules);
  }

}
