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
}
