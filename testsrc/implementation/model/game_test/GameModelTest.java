package implementation.model.game_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import design.model.game.Field;

import design.model.game.GameModel;
import design.model.game.GameRules;
import design.model.game.ItemRule;
import design.model.game.LossConditions;
import design.model.game.WinConditions;
import implementation.model.game.GameModelImpl;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.rules.GameRulesImpl;
import implementation.model.game.rules.ItemRuleImpl;
import implementation.model.game.rules.LossConditionsImpl;
import implementation.model.game.rules.WinConditionsImpl;
import implementation.model.game.items.Apple;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

/**
 * Test regarding the game model, where it is tested if it is initialize correctly.
 */
public class GameModelTest {

    private static final int MAX_PRESENCE = 5;

    private GameModel gameModel;

    /**
     * Used to test if a game model is initialized correctly.
     */
    @Test
    public void testInit() {
        final WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), true);
        final LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
        final ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, MAX_PRESENCE, Optional.empty(), Optional.empty());
        final List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));
        final GameRules gameRules =  new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
        final Field field = new FieldImpl(new Point(10, 10));

        this.gameModel = new GameModelImpl(field, gameRules);
        assertNotNull("Check if game model has been initialized correctly", this.gameModel);
  }

    /**
     * Used to test if the game model return his field correctly.
     */
    @Test
    public void testGetField() {

        final WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), true);
        final LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
        final ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, MAX_PRESENCE, Optional.empty(), Optional.empty());
        final List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));
        final GameRules gameRules =  new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
        final Field field = new FieldImpl(new Point(10, 10));

        this.gameModel = new GameModelImpl(field, gameRules);
        assertEquals("Check if the field of the game model is the same as the field set", this.gameModel.getField(), field);
  }

    /**
     * Used to test if the game model return his game rules correctly.
     */
    @Test
    public void testGetGameRules() {
        final WinConditions winC = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), true);
        final LossConditions lossC = new LossConditionsImpl(true, Optional.empty(), true);
        final ItemRule itemR = new ItemRuleImpl(Apple.class, 100L, 0.5, MAX_PRESENCE, Optional.empty(), Optional.empty());
        final List<ItemRule> itemRules = new ArrayList<>(Arrays.asList(itemR));
        final GameRules gameRules =  new GameRulesImpl(winC, lossC, itemRules, 100L, 1.0, 10L, true);
        final Field field = new FieldImpl(new Point(10, 10));

        this.gameModel = new GameModelImpl(field, gameRules);
        assertEquals("Check if the game rules of the game model is the same as the game rules set", this.gameModel.getGameRules(), gameRules);
    }

}
