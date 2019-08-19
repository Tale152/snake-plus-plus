package design.view.game;

/**
 * A sprite is an abstract idea representing the graphical resources
 * that will be displayed trying to isolate View implementation 
 * from used API to better suit MVC philosophy.
 */
public interface Sprite {

    /**
     * @return Sprite's name
     */
    String getName();

    /**
     * @return the actual object used to print, depending on used API
     */
    Object getSprite();

}
