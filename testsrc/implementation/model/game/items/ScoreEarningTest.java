package implementation.model.game.items;

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

/**
 * Test regarding Item holding ScoreEarning effect.
 * @see Item
 * @see ScoreEarning
 * @see Effect
 */
public class ScoreEarningTest {

    private Item scoreEarning;
    private final Point pointZero = new Point(0, 0);

    /**
     * Test score earning's instantaneous effect.
     */
    @Test
    public void testInstantaneousEffect() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        scoreEarning = itemFactory.createItem(pointZero, ScoreEarning.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertEquals("checking that snake's score is zero",
                testSnake.getPlayer().getScore(), 0);
        assertEquals("checking that snake's length is one",
                testSnake.getProperties().getLengthProperty().getLength(), 1);
        AppleTest.collide(scoreEarning, testSnake);
        assertEquals("checking that snake's length is still one",
                testSnake.getProperties().getLengthProperty().getLength(), 1);
        assertEquals("checking that score is increased accordingly to ScoreEaring.SCORE_INCREMENT",
                testSnake.getPlayer().getScore(), ScoreEarning.SCORE_INCREMENT);
    }

    /**
     * Test score earning's instantaneous effect while snake has intangible property active.
     */
    @Test
    public void testInstantaneousEffectOnGhost() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        scoreEarning = itemFactory.createItem(pointZero, ScoreEarning.class, Optional.empty(), Optional.empty());
        final Snake testSnake = SnakeFactoryForTestsUtils.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        assertEquals("checking that score is zero",
                testSnake.getPlayer().getScore(), 0);
        assertEquals("checking that length is one",
                testSnake.getProperties().getLengthProperty().getLength(), 1);
        AppleTest.collide(scoreEarning, testSnake);
        assertEquals("checking that score is still zero",
                testSnake.getPlayer().getScore(), 0);
        assertEquals("checking that length is still one",
                testSnake.getProperties().getLengthProperty().getLength(), 1);
    }

    //TODO test lasting effect

}
