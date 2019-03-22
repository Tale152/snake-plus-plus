package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.lang.reflect.*;
import java.util.*;

import org.junit.Test;

import design.model.game.Item;
import design.model.game.Snake;

public class GeneralItemsTests {
	
	List<String> classesList = new ArrayList<>(Arrays.asList(
			"Apple",
			"BadApple",
			"Beer",
			"DoublePoints",
			"GhostMode",
			"GodMode",
			"Ice",
			"Magnet",
			"ScoreEarning",
			"ScoreLoss",
			"Slug",
			//"Spring", TODO does not work because reverse into snake does not work
			"Turbo"
			)) ;
	private Item item;
	private String itemFactoryPath = "implementation.model.game.items.ItemFactory";
	
	@Test
	public void testInit() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		for (String s : classesList) {
			Method method = Class.forName(itemFactoryPath).getMethod("create" + s, Point.class, Optional.class, Optional.class);  
			
			try{
	            item = (Item)method.invoke(null, null, Optional.empty(), Optional.empty());
	            fail(s + "'s point cannot be null");
	        } catch (IllegalArgumentException e){
	        } catch (Exception e){
				System.out.println(e.toString());
	            fail("wrong exception thrown at " + s);
	        }
			
			try{
	            item = (Item)method.invoke(null, new Point(0,0), null, Optional.empty());
	            fail(s + "'s expiration time cannot be null");
	        } catch (IllegalArgumentException e){
	        } catch (Exception e){
	            fail("wrong exception thrown at " + s);
	        }
			
			try{
				item = (Item)method.invoke(null, new Point(0,0), Optional.empty(), null);
				fail(s + "'s effect duration cannot be null");
	        } catch (IllegalArgumentException e){
	        } catch (Exception e){
	            fail("wrong exception thrown at " + s);
	        }
			
			item = (Item)method.invoke(null, new Point(0,0), Optional.empty(), Optional.empty());
			assertTrue(item.getPoint().equals(new Point(0,0)));
			assertTrue(item.getDuration().equals(Optional.empty()));
			
			item = (Item)method.invoke(null, new Point(0,0), Optional.empty(), Optional.of(0L));
			assertTrue(item.getDuration().equals(Optional.of(0L)));
		}

	}
	
	@Test
	public void testInstantaneousEffect() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Snake testSnake;
		for (String s : classesList) {
			Method method = Class.forName(itemFactoryPath).getMethod("create" + s, Point.class, Optional.class, Optional.class);  
			item = (Item)method.invoke(null, new Point(0,0), Optional.empty(), Optional.empty());
			testSnake = SnakeFactoryForTests.baseSnake();
			item.onCollision(testSnake, 0L);
			if(testSnake.getEffects().size()!=0) {
				fail("failed at " + s);
			}
		}
	}
	
	@Test
	public void testLastingEffect() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Snake testSnake;
		for (String s : classesList) {
			Method method = Class.forName(itemFactoryPath).getMethod("create" + s, Point.class, Optional.class, Optional.class);  
			item = (Item)method.invoke(null, new Point(0,0), Optional.empty(), Optional.of(100L));
			testSnake = SnakeFactoryForTests.baseSnake();
			item.onCollision(testSnake, 0L);
			if(testSnake.getEffects().size()!=1) {
				fail("failed effect association at " + s);
			}
			if (!testSnake.getEffects().get(0).getEffectEndTime().equals(Optional.of(100L))) {
				fail("failed first time registration at " + s);
			}
			item = (Item)method.invoke(null, new Point(0,0), Optional.empty(), Optional.of(200L));
			item.onCollision(testSnake, 0L);
			if(testSnake.getEffects().size()!=1) {
				fail("failed second effect association at " + s);
			}
			if (!testSnake.getEffects().get(0).getEffectEndTime().equals(Optional.of(300L))) {
				fail("failed second time registration at " + s);
			}
		}
	}
	
	@Test
	public void testOnGhost() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Snake testSnake;
		classesList.remove("GhostMode");
		for (String s : classesList) {
			Method method = Class.forName(itemFactoryPath).getMethod("create" + s, Point.class, Optional.class, Optional.class);  
			item = (Item)method.invoke(null, new Point(0,0), Optional.empty(), Optional.of(100L));
			testSnake = SnakeFactoryForTests.ghostSnake();
			item.onCollision(testSnake, 0L);
			if(testSnake.getEffects().size()!=0) {
				fail("failed effect association at " + s);
			}
		}
		
		item = ItemFactory.createGhostMode(new Point(0,0), Optional.empty(), Optional.of(100L));
		testSnake = SnakeFactoryForTests.ghostSnake();
		item.onCollision(testSnake, 0L);
		assertEquals(testSnake.getEffects().size(), 1);
		
		classesList.add("GhostMode");
	}
	
}
