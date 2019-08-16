package implementation.model.game;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import org.junit.Test;

import implementation.model.game.field.FieldTest;
import implementation.model.game.gameRules.GameRulesTest;
import implementation.model.game.gameRules.ItemRuleTest;
import implementation.model.game.gameRules.LossConditionsTest;
import implementation.model.game.gameRules.WinConditionsTest;
import implementation.model.game.items.*;
import implementation.model.game.snake.*;

/**
 * Used to run all the test of the field, game rules, items and snake together.
 */
public class TestEntireGame {

    /**
     * Used to run all the snake tests.
     */
    @Test
    public void testSnake() {
        new PlayerTest().testPlayer();
        new CollisionPropertyTest().testCollisionProperty();
        new DirectionPropertyTest().testDirectionProperty(); 
        new LengthPropertyTest().testLengthProperty();
        new PickupPropertyTest().testPickupProperty();
        new SpeedPropertyTest().testSpeedProperty();
        new PropertiesTest().testProperties();
        new SnakeTest().testInit();
        new SnakeTest().testEffect();
        new SnakeTest().testKill();
        new SnakeTest().testNormalMove();
        new SnakeTest().testLenghtenMove();
        new SnakeTest().testShortenMove();
        new SnakeTest().testReverse();
    }

    /**
     * Used to run all the item tests.
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws InvocationTargetException 
     */
    @Test
    public void testItems() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        new GeneralItemsTests().testInstantaneousEffect();
        new GeneralItemsTests().testLastingEffect();
        new GeneralItemsTests().testOnGhost();
        new BodyPartTest().testInitBodyPart();
        new WallTest().testInitWall();
        new WallTest().testCollision();
        new AppleTest().testInstantaneousEffect();
        new AppleTest().testInstantaneousEffectOnGhost();
        new AppleTest().testLastingEffect();
        new BadAppleTest().testInstantaneousEffect();
        new BadAppleTest().testInstantaneousEffectOnGhost();
        new BadAppleTest().testLastingEffect();
        new BeerTest().testInstantaneousEffect();
        new BeerTest().testInstantaneousEffectOnGhost();
        new BeerTest().testLastingEffect();
        new DoublePointsTest().testInstantaneousEffect();
        new DoublePointsTest().testLastingEffect();
        new GhostModeTest().testInstantaneousEffect();
        new GhostModeTest().testLastingEffect();
        new GodModeTest().testInstantaneousEffect();
        new GodModeTest().testLastingEffect();
        new MagnetTest().testInstantaneousEffect();
        new MagnetTest().testLastingEffect();
        new ScoreEarningTest().testInstantaneousEffect();
        new ScoreEarningTest().testInstantaneousEffectOnGhost();
        new ScoreLossTest().testInstantaneousEffect();
        new ScoreLossTest().testInstantaneousEffectOnGhost();
        new SlugTest().testInstantaneousEffect();
        new SlugTest().testLastingEffect(); 
        new SpringTest().testInstantaneousEffect();
        new SpringTest().testInstantaneousEffectOnGhost();
        new SpringTest().testLastingEffect();
        new TurboTest().testInstantaneousEffect();
        new TurboTest().testLastingEffect();
    }

    /**
     * Used to run all field tests.
     */
    @Test
        public void testField() {
        new FieldTest().testInit();
        new FieldTest().testAddItems();
        new FieldTest().testGetCell();
        new FieldTest().testGetItem();
        new FieldTest().testRemoveItem();
    }

    /**
     * Used to run all win condition tests.
     */
    @Test
    public void testWinConditions() {
        new WinConditionsTest().testInit();
        new WinConditionsTest().testLengthConditions();
        new WinConditionsTest().testScoreConditions();
        new WinConditionsTest().testTimeConditions();
    }

    /**
     * Used to run all loss condition tests.
     */
    @Test
    public void testLossConditions() {
        new LossConditionsTest().testInit();
        new LossConditionsTest().testAllSnakeDiedContitions();
        new LossConditionsTest().testTimeConditions();
    }

    /**
     * Used to run all the item rule tests.
     */
    @Test
    public void testItemRule() {
        new ItemRuleTest().testInit();
    }

    /**
     * Used to run all game model tests.
     */
    @Test
    public void testGameModel() {
        new GameModelTest().testInit();
        new GameModelTest().testGetField();
        new GameModelTest().testGetGameRules();
    }

    /**
     * Used to run all game rules tests.
     */
    @Test
    public void testGameRules() {
        new GameRulesTest().testInit();
        new GameRulesTest().testGetWinConditions();
        new GameRulesTest().testGetLossConditions();
        new GameRulesTest().testGetItemRules();
        new GameRulesTest().testGetInitialSnakeDelta();
        new GameRulesTest().testGetInitialSnakeMultiplier();
        new GameRulesTest().testGetInitialTime();
        new GameRulesTest().testIsTimeGoingForward();
    }

}
