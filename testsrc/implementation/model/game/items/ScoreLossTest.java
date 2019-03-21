package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;

public class ScoreLossTest {

	private Item scoreLoss;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		scoreLoss = ItemFactory.createScoreLoss(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		testSnake.getPlayer().addScore(3*ItemFactory.SCORE);
		assertEquals(testSnake.getPlayer().getScore(), 3*ItemFactory.SCORE);
		scoreLoss.onCollision(testSnake, 0);
		assertEquals(testSnake.getPlayer().getScore(), (3*ItemFactory.SCORE) - (int)(testSnake.getPlayer().getScoreMultiplier() * ItemFactory.SCORE));
	}
	
	@Test
	public void testInstantaneousEffectOnGhost() {
		scoreLoss = ItemFactory.createScoreLoss(pointZero, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.ghostSnake();
		testSnake.getPlayer().addScore(3*ItemFactory.SCORE);
		assertEquals(testSnake.getPlayer().getScore(), 3*ItemFactory.SCORE);
		scoreLoss.onCollision(testSnake, 0);
		assertEquals(testSnake.getPlayer().getScore(), 3*ItemFactory.SCORE);
	}
	
	@Test 
	public void testLastingEffect() {
		scoreLoss = ItemFactory.createScoreLoss(pointZero, Optional.empty(), Optional.of(100L));
		Snake testSnake = SnakeFactoryForTests.baseSnake();
		testSnake.getPlayer().addScore(3*ItemFactory.SCORE);
		assertEquals(testSnake.getPlayer().getScore(), 3*ItemFactory.SCORE);
		scoreLoss.onCollision(testSnake, 1000L);
		assertEquals(testSnake.getPlayer().getScore(), (3*ItemFactory.SCORE) - (int)(testSnake.getPlayer().getScoreMultiplier() * ItemFactory.SCORE));
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime().get(), Optional.of(1100L));
		assertFalse(testSnake.getEffects().get(0).getExpirationTime().isPresent());
		scoreLoss = ItemFactory.createScoreLoss(pointZero, Optional.empty(), Optional.of(250L));
		scoreLoss.onCollision(testSnake, 1050L);
		assertEquals(testSnake.getPlayer().getScore(), 0);
		assertEquals(testSnake.getEffects().size(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectEndTime().get(), Optional.of(1350L));
		testSnake.getEffects().get(0).effectEnd(testSnake);
		assertEquals(testSnake.getPlayer().getScore(), 0);
	}
	
}
