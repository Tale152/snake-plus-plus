package implementation.model.game.gameRules;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.*;
import org.junit.Test;
import design.model.game.*;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.items.SnakeFactoryForTests;

public class WinConditionsTest {

	private static WinConditions win;
	
	private static void assertException(Optional<Integer> snakeLength, Optional<Integer> score, Optional<Long> time, 
			boolean timeGoesForward, Class<? extends Exception> expectedException) {
		try {
			win = new WinConditionsImpl(snakeLength, score, time, timeGoesForward);
			fail(expectedException.getSimpleName() + " expected");
		} catch (Exception e) {
			if (!e.getClass().equals(expectedException)){
				e.printStackTrace();
				fail("wrong exception");
			}
		}
	}
	
	@Test
	public void testInit() {
		assertException(null, Optional.of(0), Optional.empty(), true, NullPointerException.class);
		assertException(Optional.empty(), null, Optional.empty(), true, NullPointerException.class);
		assertException(Optional.empty(), Optional.empty(), null, true, NullPointerException.class);
		assertException(Optional.of(-1), Optional.of(0), Optional.empty(), true, IllegalArgumentException.class);
		assertException(Optional.empty(), Optional.of(-1), Optional.empty(), true, IllegalArgumentException.class);
		assertException(Optional.empty(), Optional.empty(), Optional.of(-1L), true, IllegalArgumentException.class);
		win = new WinConditionsImpl(Optional.of(10), Optional.of(1000), Optional.of(100000L), true);
	}
	
	@Test
	public void testLengthConditions() {
		win = new WinConditionsImpl(Optional.of(10), Optional.empty(), Optional.empty(), true);
		int lenghtToReach = 10;
		for (int i = 1; i <= lenghtToReach; ++i) {
			Field field = new FieldImpl(new Point(100,100));
			List<Point> points = new ArrayList<>();
			for (int j = 0; j < i; ++j) {
				points.add(new Point(j,0));
			}
			Snake snake = SnakeFactoryForTests.baseSnake(points, field);
			if (i < lenghtToReach) {
				assertFalse(win.checkSnakeLength(new ArrayList<Snake>(Arrays.asList(snake))));
			} else {
				assertTrue(win.checkSnakeLength(new ArrayList<Snake>(Arrays.asList(snake))));
			}
		}
	}
	
  @Test
  public void testScoreConditions() {
    win = new WinConditionsImpl(Optional.empty(), Optional.of(10), Optional.empty(), true);
    Field field = new FieldImpl(new Point(100,100));
    Snake snake = SnakeFactoryForTests.baseSnake(
        new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
    List<Snake> snakeList = new ArrayList<Snake>(Arrays.asList(snake));
    int scoreToReach = 10;
    for (int i = 1; i <= scoreToReach; ++i) {
      snake.getPlayer().addScore(1);
      if (i < scoreToReach) {
        assertFalse(win.checkScore(snakeList));
      } else {
        assertTrue(win.checkScore(snakeList));
      }
    }
  }
	
	@Test
	public void testTimeConditions() {
		long timeToReach = 10L;
		win = new WinConditionsImpl(Optional.empty(), Optional.empty(), Optional.of(timeToReach), true);
		for(long i = 0; i < timeToReach; ++i) {
			assertFalse(win.checkTime(i));
		}
		assertTrue(win.checkTime(timeToReach));
		long startTime = 10L;
		win = new WinConditionsImpl(Optional.empty(), Optional.empty(), Optional.of(0L), false);
		for(long i = startTime; i > 0; --i) {
			assertFalse(win.checkTime(i));
		}
		assertTrue(win.checkTime(0L));
	}
}
