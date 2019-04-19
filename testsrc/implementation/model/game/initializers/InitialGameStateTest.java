package implementation.model.game.initializers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import design.model.game.Direction;
import design.model.game.InitialGameState;
import design.model.game.InitialGameState.InitialPlayerState;
import design.model.game.Item;
import design.model.game.PlayerNumber;
import implementation.model.game.items.ItemFactory;
import implementation.model.game.snake.SnakeImpl;

public class InitialGameStateTest {
	
	InitialGameState game;
	Item apple = ItemFactory.createApple(new Point(4,4), Optional.empty(), Optional.empty());
	List<Point> pos = new ArrayList<>(Arrays.asList(new Point(6,6)));
	InitialPlayerState p = new InitialPlayerStateImpl("name", pos, Direction.UP, 0);
	List<Item> item = new ArrayList<>(Arrays.asList(apple));
	List<InitialPlayerState> player = new ArrayList<>(Arrays.asList(p));
	
	@Test
	public void testInit() {
		
		try{
			game = new InitialGameStateImpl(null, new Point(10,10), player);
            fail("list of items can not be null");
        } catch (NullPointerException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			game = new InitialGameStateImpl(item, null, player);
            fail("field size can not be null");
        } catch (NullPointerException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			game = new InitialGameStateImpl(item, new Point(10,10), null);
            fail("list of initial player state can not be null");
        } catch (NullPointerException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			List<Item> item = new ArrayList<>(Arrays.asList(ItemFactory.createApple(new Point(4,4), Optional.empty(), Optional.empty())));
			item.add(null);
			game = new InitialGameStateImpl(item, new Point(10,10), player);
            fail("Any element of the list of items can be null");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			List<Item> item = new ArrayList<>(Arrays.asList(ItemFactory.createApple(new Point(4,4), Optional.empty(), Optional.empty())));
			game = new InitialGameStateImpl(item, new Point(0,0), player);
            fail("Field size can not be 0,0");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			game = new InitialGameStateImpl(item, new Point(10,-1), player);
            fail("Field size can not be negative");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception");
        }try{
			game = new InitialGameStateImpl(item, new Point(0,0), player);
            fail("Field size can not be 0,0");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception");
        }
        try{
        	List<Item> item = new ArrayList<>(Arrays.asList(ItemFactory.createApple(new Point(8,8), Optional.empty(), Optional.empty()))); 
			game = new InitialGameStateImpl(item, new Point(7,7), player);
            fail("An item can not be place outside field borders");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception");
        }
        try{
			game = new InitialGameStateImpl(item, new Point(5,5), player);
            fail("A player initial place can not be outside field borders");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception");
        }
        try{
        	List<Item> item = new ArrayList<>(Arrays.asList(ItemFactory.createApple(new Point(4,4), Optional.empty(), Optional.empty())));
        	item.add(ItemFactory.createBodyPart(new Point(2,2), new SnakeImpl(pos, PlayerNumber.PLAYER1, "p", Direction.UP, 100L, 1.0, 0L)));
			game = new InitialGameStateImpl(item, new Point(10,10), player);
            fail("items cannot contain BodyParts");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception");
        }
        try{
        	List<InitialPlayerState> player = new ArrayList<>();
        	player.add(null);
			game = new InitialGameStateImpl(item, new Point(5,5), player);
            fail("List of player can not contain null");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception");
        }
        try{
        	List<InitialPlayerState> player = new ArrayList<>();
			game = new InitialGameStateImpl(item, new Point(5,5), player);
            fail("List of player can not be empty");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception");
        }
        try{
        	InitialPlayerState p = new InitialPlayerStateImpl("name", pos, Direction.UP, 0);
        	List<InitialPlayerState> player = new ArrayList<>(Arrays.asList(p));
        	List<Point> pos1 = new ArrayList<>(Arrays.asList(new Point(1,1)));
        	InitialPlayerState p1 = new InitialPlayerStateImpl("name", pos1, Direction.UP, 0);
        	List<Point> pos2 = new ArrayList<>(Arrays.asList(new Point(2,2)));
        	InitialPlayerState p2 = new InitialPlayerStateImpl("name", pos2, Direction.UP, 0);
        	List<Point> pos3 = new ArrayList<>(Arrays.asList(new Point(3,3)));
        	InitialPlayerState p3 = new InitialPlayerStateImpl("name", pos3, Direction.UP, 0);
        	List<Point> pos4 = new ArrayList<>(Arrays.asList(new Point(4,4)));
        	InitialPlayerState p4 = new InitialPlayerStateImpl("name", pos4, Direction.UP, 0);
        	player.add(p1);
        	player.add(p2);
        	player.add(p3);
        	player.add(p4);
			game = new InitialGameStateImpl(item, new Point(5,5), player);
            fail("There can not be more than 4 players");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception");
        }
        try{
        	List<Point> pos = new ArrayList<>(Arrays.asList(new Point(6,6)));
        	InitialPlayerState p1 = new InitialPlayerStateImpl("name", pos, Direction.UP, 0);
        	InitialPlayerState p2 = new InitialPlayerStateImpl("name", pos, Direction.UP, 0);
        	List<InitialPlayerState> player = new ArrayList<>(Arrays.asList(p1, p2));
			game = new InitialGameStateImpl(item, new Point(5,5), player);
            fail("Two player can not spawn in the same coords");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception");
        }
        
        game = new InitialGameStateImpl(item, new Point(10,10), player);
        assertTrue(item.size() == 1);
        assertTrue(item.contains(apple));
        assertEquals(new Point(10,10), game.getFieldSize());
        assertTrue(player.size() == 1);
        assertTrue(player.contains(p));
	}
	
	@Test
	public void testGetFieldItems() {
		Item apple = ItemFactory.createApple(new Point(4,4), Optional.empty(), Optional.empty());
		List<Point> pos = new ArrayList<>(Arrays.asList(new Point(6,6)));
		InitialPlayerState p = new InitialPlayerStateImpl("name", pos, Direction.UP, 0);
		List<Item> item = new ArrayList<>(Arrays.asList(apple));
		List<InitialPlayerState> player = new ArrayList<>(Arrays.asList(p));
		
		game = new InitialGameStateImpl(item, new Point(10,10), player);
		assertTrue(game.getFieldItems().size() == 1);
		assertTrue(game.getFieldItems().contains(apple));
		
		Item badApple = ItemFactory.createBadApple(new Point(4,4), Optional.empty(), Optional.empty());
		item.add(badApple);
		assertTrue(game.getFieldItems().size() == 2);
		assertTrue(game.getFieldItems().contains(apple));
		assertTrue(game.getFieldItems().contains(badApple));
		
		assertTrue(game.getFieldItems() != game.getFieldItems());
	}
		
	@Test
	public void testGetFieldSize() {
		game = new InitialGameStateImpl(item, new Point(10,10), player);
		assertTrue(game.getFieldSize().equals(new Point (10,10)));
		assertTrue(game.getFieldSize() != game.getFieldSize());
	}
	
	@Test
	public void testGetInitialPlayerState() {
		game = new InitialGameStateImpl(item, new Point(10,10), player);
		assertTrue(game.getInitialPlayerState().size() == 1);
		assertTrue(game.getInitialPlayerState().contains(p));
		InitialPlayerState p1 = new InitialPlayerStateImpl("name", pos, Direction.UP, 0);
		player.add(p1);
		assertTrue(game.getInitialPlayerState().size() == 2);
		assertTrue(game.getInitialPlayerState().contains(p));
		assertTrue(game.getInitialPlayerState().contains(p1));
		
		assertTrue(game.getInitialPlayerState() != game.getInitialPlayerState());
	}
	
	
	
	
}
