package implementation.model.game.snake;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import design.model.game.LengthProperty;

public class LengthPropertyTest {
	
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
