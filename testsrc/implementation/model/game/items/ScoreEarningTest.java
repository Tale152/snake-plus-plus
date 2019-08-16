package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;
import implementation.model.game.field.FieldImpl;

public class ScoreEarningTest {

    private Item scoreEarning;
    private Point pointZero = new Point(0,0);
    
    @Test
    public void testInstantaneousEffect() {
        Field field = new FieldImpl(new Point(10,10));
        ItemFactory itemFactory = new ItemFactory(field);
        scoreEarning = itemFactory.createItem(pointZero, ScoreEarning.class, Optional.empty(), Optional.empty());
        Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
        assertEquals(testSnake.getPlayer().getScore(), 0);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1);
        AppleTest.collide(scoreEarning, testSnake);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1);
        assertEquals(testSnake.getPlayer().getScore(), (int)(testSnake.getPlayer().getScoreMultiplier() * 10));
    }
    
    @Test
    public void testInstantaneousEffectOnGhost() {
        Field field = new FieldImpl(new Point(10,10));
        ItemFactory itemFactory = new ItemFactory(field);
        scoreEarning = itemFactory.createItem(pointZero, ScoreEarning.class, Optional.empty(), Optional.empty());
        Snake testSnake = SnakeFactoryForTests.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
        assertEquals(testSnake.getPlayer().getScore(), 0);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1);
        AppleTest.collide(scoreEarning, testSnake);
        assertEquals(testSnake.getProperties().getLengthProperty().getLength(), 1);
        assertEquals(testSnake.getPlayer().getScore(), 0);
    }
    
    /*no need to test lasting effect, does nothing*/
    
}