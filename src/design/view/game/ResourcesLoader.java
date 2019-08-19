package design.view.game;

/**
 * Class used to load resources used to display graphical elements used 
 * while playing the game.
 */
public interface ResourcesLoader {

    /**
     * @param name desired item sprite's name
     * @return requested item Sprite
     */
    Sprite getItem(String name);

    /**
     * @param name desired wall sprite's name
     * @return requested wall Sprite
     */
    Sprite getWall(String name);

    /**
     * @param name desired body part sprite's name
     * @return requested body part Sprite
     */
    Sprite getBodyPart(String name); 

    /**
     * @return field's background
     */
    Background getFieldBackground();

    /**
     * @return hud's background
     */
    Background getHudBackground();

    /**
     * @return the sprite that appears over a player's name when his snake dies
     */
    Sprite getDeadPlayerIndicator();

}

