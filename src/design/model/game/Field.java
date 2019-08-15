package design.model.game;

import java.awt.Point;
import java.util.List;

/**
 * Field is the container to hold everything in game, such as snakes, items and walls.
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface Field {

    /**
     * Starts all game threads.
     */
    void begin();

    /**
     * @return Returns all eliminated items from field. It flushes after using it.
     */
    List<Item> getEliminatedItems();

    /**
     * @return field's width (number of cells)
     */
    int getWidth();

    /**
     * @return field's height (number of cells)
     */
    int getHeight();

    /**
     * @param point desired cell to look
     * @return everything contained into that cell
     */
    List<Collidable> getCell(Point point);

    /**
     * @return all items currently on field
     */
    List<Item> getItems();

    /**
     * @param item the item to add into the field
     * @return if item was added successfully
     * @throws IllegalStateException if item's point out of field size
     */
    boolean addItem(Item item) throws IllegalStateException;

    /**
     * @param item item to remove
     * @return if item was successfully removed or not
     */
    boolean removeItem(Item item);

    /**
     * @return list containing every Wall into the field
     */
    List<Wall> getWalls();

    /**
     * @param wall the wall to add to the field
     * @return if wall was successfully added or not
     * @throws IllegalStateException if wall's point is out of field size
     */
    boolean addWall(Wall wall) throws IllegalStateException;

    /**
     * @return a list containing all snake's body parts
     */
    List<BodyPart> getBodyParts();

    /**
     * @param bodyPart to add to the field
     * @return if successfully added or not
     * @throws IllegalStateException if body part's point is out of field's dimension
     */
    boolean addBodyPart(BodyPart bodyPart) throws IllegalStateException;

    /**
     * @param bodyPart body part to remove from field
     * @return if successfully removed or not
     */
    boolean removeBodyPart(BodyPart bodyPart);

    /**
     * @return list containing all snakes
     */
    List<Snake> getSnakes();

    /**
     * @param snake snake to add into the field
     * @return if successfully added or not
     */
    boolean addSnake(Snake snake);

    /**
     * @param i index in snake's list
     * @return snake removed from field's snake list
     */
    Snake removeSnake(int i);

}
