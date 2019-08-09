package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.lang.reflect.*;
import java.util.*;

import org.junit.Test;

import design.model.game.Effect;
import design.model.game.Item;
import design.model.game.Snake;
import implementation.model.game.field.FieldImpl;

public class GeneralItemsTests {
	
	List<Class<? extends Effect>> classesList = new ArrayList<>(Arrays.asList(
			Apple.class,
			BadApple.class,
			Beer.class,
			DoublePoints.class,
			GhostMode.class,
			GodMode.class,
			Magnet.class,
			ScoreEarning.class,
			ScoreLoss.class,
			Slug.class,
			Spring.class,
			Turbo.class
			)) ;
	
	@Test
	public void testInit() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ItemFactory itemFactory = new ItemFactory(new FieldImpl(new Point(classesList.size(),1)));
		for (int i = 0; i < classesList.size(); ++i) {
			try {
				itemFactory.createItem(null, classesList.get(i), Optional.empty(), Optional.empty());
			} catch (NullPointerException e){
	        } catch (Exception e){
	            fail("wrong exception");
	        }
			try {
				itemFactory.createItem(new Point(i,0), classesList.get(i), null, Optional.empty());
			} catch (NullPointerException e){
	        } catch (Exception e){
	            fail("wrong exception");
	        }
			try {
				itemFactory.createItem(new Point(i,0), classesList.get(i), Optional.empty(), null);
			} catch (NullPointerException e){
	        } catch (Exception e){
	            fail("wrong exception");
	        }
		}
	}
	
	@Test
	public void testInstantaneousEffect() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (int i = 0; i < classesList.size(); ++i) {
			FieldImpl field = new FieldImpl(new Point(classesList.size(),1));
			ItemFactory itemFactory = new ItemFactory(field);
			Item item = itemFactory.createItem(new Point(i,0), classesList.get(i), Optional.empty(), Optional.empty());
			Snake snake = SnakeFactoryForTests.baseSnake(Arrays.asList(new Point(0,0)), field);
			AppleTest.collide(item, snake);
			if (snake.getEffects().size() != 0) {
				fail("failed at " + classesList.get(i).getSimpleName());
			}
		}
	}
	
	@Test
	public void testLastingEffect() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (int i = 0; i < classesList.size(); ++i) {
			FieldImpl field = new FieldImpl(new Point(classesList.size(),1));
			ItemFactory itemFactory = new ItemFactory(field);
			Item item = itemFactory.createItem(new Point(i,0), classesList.get(i), Optional.empty(), Optional.of(100L));
			Snake snake = SnakeFactoryForTests.baseSnake(Arrays.asList(new Point(0,0)), field);
			AppleTest.collide(item, snake);
			if (snake.getEffects().size() != 1) {
				fail("first effect attachment failed at " + classesList.get(i).getSimpleName());
			}
			assertEquals(Optional.of(100L),snake.getEffects().get(0).getEffectDuration());
			item = itemFactory.createItem(new Point(i,0), classesList.get(i), Optional.empty(), Optional.of(100L));
			AppleTest.collide(item, snake);
			if (snake.getEffects().size() != 1) {
				fail("second effect attachment failed at " + classesList.get(i).getSimpleName());
			}
			assertEquals(Optional.of(200L),snake.getEffects().get(0).getEffectDuration());
		}
	}
	
	@Test
	public void testOnGhost() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		classesList.remove(GhostMode.class);
		for (int i = 0; i < classesList.size(); ++i) {
			FieldImpl field = new FieldImpl(new Point(classesList.size(),1));
			ItemFactory itemFactory = new ItemFactory(field);
			Item item = itemFactory.createItem(new Point(i,0), classesList.get(i), Optional.empty(), Optional.of(100L));
			Snake snake = SnakeFactoryForTests.ghostSnake(Arrays.asList(new Point(0,0)), field);
			AppleTest.collide(item, snake);
			if (snake.getEffects().size() != 0) {
				fail("effect attachment failed at " + classesList.get(i).getSimpleName());
			}
		}
		classesList.add(GhostMode.class);
		FieldImpl field = new FieldImpl(new Point(classesList.size(),1));
		ItemFactory itemFactory = new ItemFactory(field);
		Item item = itemFactory.createItem(new Point(0,0), GhostMode.class, Optional.empty(), Optional.of(100L));
		Snake snake = SnakeFactoryForTests.ghostSnake(Arrays.asList(new Point(0,0)), field);
		AppleTest.collide(item, snake);
		if (snake.getEffects().size() != 1) {
			fail("effect attachment failed at GhostMode");
		}
	}
	
}
