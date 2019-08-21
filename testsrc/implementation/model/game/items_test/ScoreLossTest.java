package implementation.model.game.items_test;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;

import design.model.game.Field;
import design.model.game.Item;
import design.model.game.Snake;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.items.ItemFactory;
import implementation.model.game.items.ScoreEarning;
import implementation.model.game.items.ScoreLoss;

/**
 * Test regarding Item holding ScoreLoss effect.
 * @see Item
 * @see ScoreLoss
 * @see Effect
 */
public class ScoreLossTest {

    private Item scoreLoss;
    private final Point pointZero = new Point(0, 0);

    /**
     * Test score loss's instantaneous effect.
     */
    @Test
    public void testInstantaneousEffect() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        scoreLoss = itemFactory.createItem(pointZero, ScoreLoss.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        testSnake.getPlayer().addScore(ScoreEarning.SCORE_INCREMENT);
        assertEquals("checks that score is equal to ScoreEarining.SCORE_INCREMENT",
                testSnake.getPlayer().getScore(), ScoreEarning.SCORE_INCREMENT);
        assertEquals("check that length is equal to one",
                testSnake.getProperties().getLengthProperty().getLength(), 1);
        AppleTest.collide(scoreLoss, testSnake);
        assertEquals("check that length is still equal to one",
                testSnake.getProperties().getLengthProperty().getLength(), 1);
        assertEquals("checks that score has been reduced by ScoreEarning.SCORE_INCREMENT, therefore is zero",
                testSnake.getPlayer().getScore(), 0);
    }

    /**
     * Test score loss's instantaneous effect while snake has intangible property active.
     */
    @Test
    public void testInstantaneousEffectOnGhost() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        scoreLoss = itemFactory.createItem(pointZero, ScoreLoss.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        testSnake.getPlayer().addScore(ScoreEarning.SCORE_INCREMENT);
        assertEquals("checks that score is equal to ScoreEarining.SCORE_INCREMENT",
                testSnake.getPlayer().getScore(), ScoreEarning.SCORE_INCREMENT);
        assertEquals("check that length is equal to one",
                testSnake.getProperties().getLengthProperty().getLength(), 1);
        AppleTest.collide(scoreLoss, testSnake);
        assertEquals("checks that score is still equal to ScoreEarining.SCORE_INCREMENT",
                testSnake.getPlayer().getScore(), ScoreEarning.SCORE_INCREMENT);
        assertEquals("check that length is still equal to one",
                testSnake.getProperties().getLengthProperty().getLength(), 1);
    }

}
