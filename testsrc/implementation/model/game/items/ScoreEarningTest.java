package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;

public class ScoreEarningTest {

	private Item scoreEarning;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		scoreEarning = ItemFactory.createScoreEarning(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		assertEquals(testSnake.getPlayer().getScore(), 0);
		scoreEarning.onCollision(testSnake, 0);
		assertEquals(testSnake.getPlayer().getScore(), (int)(testSnake.getPlayer().getScoreMultiplier() * ItemFactory.SCORE));
	}
	
	@Test
	public void testInstantaneousEffectOnGhost() {
		scoreEarning = ItemFactory.createScoreEarning(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		assertEquals(testSnake.getPlayer().getScore(), 0);
		scoreEarning.onCollision(testSnake, 0);
		assertEquals(testSnake.getPlayer().getScore(), 0);
	}
	
	@Test 
	public void testLastingEffect() {
		scoreEarning = ItemFactory.createScoreEarning(pointZero, Optional.empty(), Optional.of(100L));
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		assertEquals(testSnake.getPlayer().getScore(), 0);
		scoreEarning.onCollision(testSnake, 1000L);
		assertEquals(testSnake.getPlayer().getScore(), (int)(testSnake.getPlayer().getScoreMultiplier() * ItemFactory.SCORE));
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime(), Optional.of(1100L));
		assertFalse(testSnake.getEffects().get(0).getExpirationTime().isPresent());
		scoreEarning = ItemFactory.createScoreEarning(pointZero, Optional.empty(), Optional.of(250L));
		scoreEarning.onCollision(testSnake, 1050L);
		assertEquals(testSnake.getPlayer().getScore(), (int)(testSnake.getPlayer().getScoreMultiplier() * ItemFactory.SCORE) * 3);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime(), Optional.of(1350L));
		testSnake.getEffects().get(0).effectEnd(testSnake);
		assertEquals(testSnake.getPlayer().getScore(), (int)(testSnake.getPlayer().getScoreMultiplier() * ItemFactory.SCORE) * 3);
	}
	
}