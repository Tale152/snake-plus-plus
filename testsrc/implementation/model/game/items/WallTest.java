package implementation.model.game.items;

import static org.junit.Assert.*;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import design.model.game.*;
import implementation.model.game.field.FieldImpl;

public class WallTest {

    private Wall wall;
    private Point pointZero = new Point(0,0);
    
    @Test
    public void testInitWall() {
        Field field = new FieldImpl(new Point(10,10));
        try{
            wall = new WallImpl(null);
            fail("Wall's point cannot be null");
        } catch (NullPointerException e){
        } catch (Exception e){
            fail("wrong exception thrown");
        }
        
        wall = new WallImpl(pointZero);
        
        Snake testSnake = SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field);
        try {
            wall.onCollision(testSnake);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        assertEquals(testSnake.getEffects().size(),0);
        
    }
    
    @Test
    public void testCollision(){
        Field field = new FieldImpl(new Point(10,10));
        assertFalse(survives(SnakeFactoryForTests.baseSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field)));
        assertTrue(survives(SnakeFactoryForTests.godSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field)));
        assertTrue(survives(SnakeFactoryForTests.ghostSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field)));
        assertTrue(survives(SnakeFactoryForTests.springSnake(new ArrayList<Point>(Arrays.asList(new Point(0,0))), field)));
    }
    
    private boolean survives(Snake snake) {
        wall = new WallImpl(pointZero);
        try {
            wall.onCollision(snake);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return snake.isAlive();
    }
    
}
