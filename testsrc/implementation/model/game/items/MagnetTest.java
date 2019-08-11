package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;
import design.model.game.*;
import implementation.model.game.field.FieldImpl;

public class MagnetTest {

	private Item magnet;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInstantaneousEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		magnet = itemFactory.createItem(pointZero, Magnet.class, Optional.empty(), Optional.empty());
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		assertEquals(testSnake.getProperties().getPickupProperty().getPickupRadius(),1);
		AppleTest.collide(magnet, testSnake);
		assertEquals(testSnake.getProperties().getPickupProperty().getPickupRadius(),1);
	}
	
	/*no need to test instantaneous effect on ghost, already does nothing if previous test succeeded*/
	
	@SuppressWarnings("deprecation")
	@Test 
	public void testLastingEffect() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		magnet = itemFactory.createItem(pointZero, Magnet.class, Optional.empty(), Optional.of(10L));
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
		AppleTest.collide(magnet, testSnake);
		assertEquals(testSnake.getProperties().getPickupProperty().getPickupRadius(),1);
		assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(10L));
		magnet = itemFactory.createItem(pointZero, Magnet.class, Optional.empty(), Optional.of(10L));
		AppleTest.collide(magnet, testSnake);
		assertEquals(testSnake.getEffects().get(0).getEffectDuration(), Optional.of(20L));
		assertEquals(testSnake.getProperties().getPickupProperty().getPickupRadius(),1);
		Thread t = new Thread(testSnake);
		t.start();
		try {
			Thread.sleep(10L);
			assertEquals(testSnake.getProperties().getPickupProperty().getPickupRadius(),2);
			Thread.sleep(20L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.stop();
		assertEquals(testSnake.getProperties().getPickupProperty().getPickupRadius(),1);
	}
	
}