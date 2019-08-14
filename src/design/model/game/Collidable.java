package design.model.game;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

/**
*A collidable is the root of everything that stands in a cell into the field and that you can collide with.
*
* @author Alessandro Talmi
* @author Elisa Tronetti
* @author Nicola Orlando
*
*/
public interface Collidable {

    /**
    * Specifies what happens when a snake collides with this collidable.
    * @param collider the snake that collides with this collidable
    * @throws NoSuchMethodException if not valid Effect class inside
    * @throws SecurityException if not valid Effect class inside
    * @throws InstantiationException if not valid Effect class inside
    * @throws IllegalAccessException if not valid Effect class inside
    * @throws IllegalArgumentException if not valid Effect class inside
    * @throws InvocationTargetException if not valid Effect class inside
    */
    void onCollision(Snake collider) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;

    /**
    * Return the current point representing the coordinates of this collidable into the field.
    * @return coordinates of this collidable
    */
    Point getPoint();

    /**
    * Sets a new Point representing the coordinates of this collidable into the field.
    * @param point representing where this collidable now stands into the field
    */
    void setPoint(Point point);

}
