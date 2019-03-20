package implementation.model.game.snake;

import static org.junit.Assert.*;
import org.junit.Test;
import design.model.game.*;

public class SnakeTest {
	
	@Test
	public void testPlayer() {
		Player player;
		
		try{
			player = SnakeComponentsFactoryForTest.createPlayer(null, "Ale", 0, 1);
            fail("Player number cannot be null");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		
		try{
			player = SnakeComponentsFactoryForTest.createPlayer(PlayerNumber.PLAYER1, null, 0, 1);
            fail("Player name cannot be null");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		
		try{
			player = SnakeComponentsFactoryForTest.createPlayer(PlayerNumber.PLAYER1, "Ale", -1, 1);
            fail("Initial score cannot be negative");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		
		try{
			player = SnakeComponentsFactoryForTest.createPlayer(PlayerNumber.PLAYER1, "Ale", 0, -1);
            fail("Initial multiplier cannot be negative");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		
		player = SnakeComponentsFactoryForTest.createPlayer(PlayerNumber.PLAYER1, "Ale", 0, 1);
		
		assertEquals(player.getPlayerNumber(), PlayerNumber.PLAYER1);
		assertEquals(player.getName(), "Ale");
		assertEquals(player.getScore(), 0);
		assertTrue(player.getScoreMultiplier() == 1);
		
		player.addScore(100);
		assertEquals(player.getScore(), 100);
		player.applyScoreMultiplier(2);
		assertTrue(player.getScoreMultiplier() == 2);
		player.addScore(100);
		assertEquals(player.getScore(), 300);
		player.reduceScore(100);
		assertEquals(player.getScore(), 100);
		player.applyScoreMultiplier(1);
		player.reduceScore(100);
		assertEquals(player.getScore(), 0);
		player.reduceScore(100);
		assertEquals(player.getScore(), 0);
		
	}
	
	@Test
	public void testLengthProperty() {
		LengthProperty length;
		
		try{
			length = SnakeComponentsFactoryForTest.createLengthProperty(0);
            fail("Length cannot be zero");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		
		try{
			length = SnakeComponentsFactoryForTest.createLengthProperty(-1);
            fail("Length cannot be negative");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		
		length = SnakeComponentsFactoryForTest.createLengthProperty(5);
		assertEquals(length.getLength(), 5);
		length.lengthen(1);
		assertEquals(length.getLength(), 6);
		length.lengthen(0);
		assertEquals(length.getLength(), 6);
		try{
			length.lengthen(-1);
            fail("lengthen arg cannot be negative");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		length.shorten(0);
		assertEquals(length.getLength(), 6);
		try{
			length.shorten(-1);
            fail("shorten arg cannot be negative");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		length.shorten(1);
		assertEquals(length.getLength(), 5);
		length.shorten(3);
		assertEquals(length.getLength(), 2);
		length.shorten(3);
		assertEquals(length.getLength(), 1);
		
	}
	
	private boolean standardDirectionTestSupport(Direction current, Direction opposite, Direction possible1, Direction possible2) {
		DirectionProperty direction = SnakeComponentsFactoryForTest.createDirectionProperty(current, false);
		direction.setDirection(current);
		if (!direction.getDirection().equals(current)) {
			return false;
		}
		direction.setDirection(opposite);
		if (!direction.getDirection().equals(current)) {
			return false;
		}
		direction.setDirection(possible1);
		if (!direction.getDirection().equals(possible1)) {
			return false;
		}
		direction = SnakeComponentsFactoryForTest.createDirectionProperty(current, false);
		direction.setDirection(possible2);
		if (!direction.getDirection().equals(possible2)) {
			return false;
		}
		return true;
	}
	
	private boolean oppositeDirectionTestSupport(Direction current, Direction opposite, Direction possible1, Direction possible2) {
		DirectionProperty direction = SnakeComponentsFactoryForTest.createDirectionProperty(current, true);
		direction.setDirection(opposite);
		if (!direction.getDirection().equals(current)) {
			return false;
		}
		direction.setDirection(current);
		if (!direction.getDirection().equals(current)) {
			return false;
		}
		direction.setDirection(possible1);
		if (!direction.getDirection().equals(possible2)) {
			return false;
		}
		direction = SnakeComponentsFactoryForTest.createDirectionProperty(current, true);
		direction.setDirection(possible2);
		if (!direction.getDirection().equals(possible1)) {
			return false;
		}
		return true;
	}
	
	@Test
	public void testDirectionProperty() {
		DirectionProperty direction;
		try{
			direction = SnakeComponentsFactoryForTest.createDirectionProperty(null, false);
            fail("initial direction cannot be null");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		
		direction = SnakeComponentsFactoryForTest.createDirectionProperty(null, false);
		assertFalse(direction.getReverseDirection());
		direction.setReverseDirection(true);
		assertTrue(direction.getReverseDirection());
		assertTrue(standardDirectionTestSupport(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));
		assertTrue(standardDirectionTestSupport(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT));
		assertTrue(standardDirectionTestSupport(Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN));
		assertTrue(standardDirectionTestSupport(Direction.RIGHT, Direction.LEFT, Direction.UP, Direction.DOWN));
		assertTrue(oppositeDirectionTestSupport(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));
		assertTrue(oppositeDirectionTestSupport(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT));
		assertTrue(oppositeDirectionTestSupport(Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN));
		assertTrue(oppositeDirectionTestSupport(Direction.RIGHT, Direction.LEFT, Direction.UP, Direction.DOWN));
		
	}
}
