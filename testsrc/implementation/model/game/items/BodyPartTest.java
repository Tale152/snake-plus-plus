package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import design.model.game.*;
import implementation.model.game.field.FieldImpl;

public class BodyPartTest {

	private BodyPart bodyPart;
	private Point pointZero = new Point(0,0);
	
	@Test
	public void testInitBodyPart() {
		
		try{
            bodyPart = new BodyPartImpl(pointZero, null);
            fail("Body part's owner snake cannot be null");
        } catch (NullPointerException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
		
		try{
			Field field = new FieldImpl(new Point(10,10));
            bodyPart = new BodyPartImpl(null, 
            		SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field));
            fail("Body part's point cannot ben null");
        } catch (NullPointerException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
		
		Field field = new FieldImpl(new Point(10,10));
		Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
        bodyPart = new BodyPartImpl(pointZero, testSnake);
		assertTrue(bodyPart.getPoint().equals(pointZero));
		assertFalse(bodyPart.isHead());
		assertFalse(bodyPart.isBody());
		assertFalse(bodyPart.isTail());
		assertFalse(bodyPart.isCombinedOnTop());
		assertFalse(bodyPart.isCombinedOnRight());
		assertFalse(bodyPart.isCombinedOnBottom());
		assertFalse(bodyPart.isCombinedOnLeft());
		try {
			bodyPart.onCollision(testSnake);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		assertEquals(testSnake.getEffects().size(), 0);
		
	}
	
}
