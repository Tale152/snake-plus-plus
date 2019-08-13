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
    * @throws NoSuchMethodException la
    * @throws SecurityException la
    * @throws InstantiationException la
    * @throws IllegalAccessException la
    * @throws IllegalArgumentException la
    * @throws InvocationTargetException la
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
