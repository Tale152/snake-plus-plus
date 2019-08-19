package design.view.game;

import java.awt.Point;
import java.util.List;
import java.util.Map;

/**
 * A container that contains everything graphical that will be printed regarding the field.
 */
public interface GameField {

    /**
     * @return field's background
     */
    Background getBackground();

    /**
     * @return a map of item's sprite and their coordinates
     */
    Map<Point, Sprite> getItemSprites();

    /**
     * @param point desired point 
     * @return all sprite contained into specified point
     */
    List<Sprite> getCell(Point point);

    /**
     * @param point where to put wall's sprite
     * @param sprite representing a wall
     */
    void addWallSprite(Point point, Sprite sprite);

    /**
     * @return a map of wall's sprite and their coordinates
     */
    Map<Point, Sprite> getWallSprites();

    /**
     * @param point where to put the item's sprite
     * @param sprite representing an Item
     */
    void addItemSprite(Point point, Sprite sprite);

    /**
     * @param point where to find the searched item sprite
     * @param sprite the Item sprite to remove
     */
    void removeItemSprite(Point point, Sprite sprite);

    /**
     * @param playerNumber the desired snake
     * @return a map containing, for each point, all body part's sprite contained into that poin
     */
    Map<Point, List<Sprite>> getSnakeSprites(int playerNumber);

    /**
     * Initializes a new snake map containing all it's body part sprite. {@link #addBodyPart(int, Point, Sprite)} 
     * for adding sprite and finally {@link #endNewSnakeMap(int)} to end the list, so those sprite can be displayed.<p>
     * Initializing a new snake map holds the old map (to be printed) until {@link #endNewSnakeMap(int)}
     * @param playerNumber desired player 
     */
    void initNewSnakeMap(int playerNumber);

    /**
     * See {@link #initNewSnakeMap(int)}.
     * @param playerNumber desired player
     * @param point point to display the body part
     * @param sprite representing a body part
     */
    void addBodyPart(int playerNumber, Point point, Sprite sprite);

    /**
     * See {@link #initNewSnakeMap(int)}.
     * @param playerNumber desired player
     */
    void endNewSnakeMap(int playerNumber);

}
