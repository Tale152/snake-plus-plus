package implementation.model.game.gameRules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import design.model.game.Field;
import design.model.game.Snake;
import design.model.game.WinConditions;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.gamerules.WinConditionsImpl;
import implementation.model.game.items.SnakeFactoryForTestsUtils;

/**
 * Tests regarding win condition.
 */
public class WinConditionsTest {

    private static final long TIME = 100000L;

    private static WinConditions win;

    private static void assertException(final Optional<Integer> snakeLength, final Optional<Integer> score, final Optional<Long> time, 
            final boolean timeGoesForward, final Class<? extends Exception> expectedException) {
        try {
            win = new WinConditionsImpl(snakeLength, score, time, timeGoesForward);
            fail(expectedException.getSimpleName() + " expected");
        } catch (Exception e) {
            if (!e.getClass().equals(expectedException)) {
                e.printStackTrace();
                fail("wrong exception");
            }
        }
    }

    /**
     * Used to test that win condition are initialized correctly.
     */
    @Test
    public void testInit() {
        assertException(null, Optional.of(0), Optional.empty(), true, NullPointerException.class);
        assertException(Optional.empty(), null, Optional.empty(), true, NullPointerException.class);
        assertException(Optional.empty(), Optional.empty(), null, true, NullPointerException.class);
        assertException(Optional.of(-1), Optional.of(0), Optional.empty(), true, IllegalArgumentException.class);
        assertException(Optional.empty(), Optional.of(-1), Optional.empty(), true, IllegalArgumentException.class);
        assertException(Optional.empty(), Optional.empty(), Optional.of(-1L), true, IllegalArgumentException.class);
        win = new WinConditionsImpl(Optional.of(10), Optional.of(1000), Optional.of(TIME), true);
    }

    /**
     * Used to test if a win condition based on the length can be reached correctly.
     */
    @Test
    public void testLengthConditions() {
        win = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), true);
        final int lenghtToReach = 10;
        for (int i = 1; i <= lenghtToReach; ++i) {
            final Field field = new FieldImpl(new Point(100, 100));
            final List<Point> points = new ArrayList<>();
            for (int j = 0; j < i; ++j) {
                points.add(new Point(j, 0));
            }
            final Snake snake = SnakeFactoryForTestsUtils.baseSnake(points, field);
            if (i < lenghtToReach) {
                assertFalse("Check if the win condition is not reached if the length is lower",
                        win.checkSnakeLength(new ArrayList<Snake>(Arrays.asList(snake))));
            } else {
                assertTrue("Check if the win condition has been reached when the length is the expected one",
                        win.checkSnakeLength(new ArrayList<Snake>(Arrays.asList(snake))));
            }
        }
    }

    /**
     * Used to test if a win condition based on the score can be reached correctly.
     */
    @Test
    public void testScoreConditions() {
        win = new WinConditionsImpl(Optional.empty(), Optional.of(10), Optional.empty(), true);
        final Field field = new FieldImpl(new Point(100, 100));
        final Snake snake = SnakeFactoryForTestsUtils.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0, 0))), field);
        final List<Snake> snakeList = new ArrayList<Snake>(Arrays.asList(snake));
        final int scoreToReach = 10;
        for (int i = 1; i <= scoreToReach; ++i) {
            snake.getPlayer().addScore(1);
            if (i < scoreToReach) {
                assertFalse("Check if the win condition is not reached if the score is lower", win.checkScore(snakeList));
            } else {
                assertTrue("Check if the win condition has been reached when the score is the expected one", 
                        win.checkScore(snakeList));
            }
        }
    }

    /**
     * Used to test if a win condition based on the time can be reached correctly.
     */
    @Test
    public void testTimeConditions() {
        final long timeToReach = 10L;
        win = new WinConditionsImpl(Optional.empty(), Optional.empty(), Optional.of(timeToReach), true);
        for (long i = 0; i < timeToReach; ++i) {
            assertFalse("Check if the win condition is not reached if the time is lower and the time goes forward",
                    win.checkTime(i));
        }
        assertTrue("Check if the win condition has been reached when the time is the expected one and the time goes forward",
                win.checkTime(timeToReach));
        final long startTime = 10L;
        win = new WinConditionsImpl(Optional.empty(), Optional.empty(), Optional.of(0L), false);
        for (long i = startTime; i > 0; --i) {
            assertFalse("Check if the win condition is not reached if the time is bigger and the time goes backward", win.checkTime(i));
        }
        assertTrue("Check if the win condition has been reached when the time is the expected one and the time goes backward", win.checkTime(0L));
    }

}
