package design.view.game;

import java.util.List;

/**
 * Contains every information that needs to be printed regarding a player's hud.
 * @see GameHud
 */
public interface PlayerHud {

    /**
     * @return player's name to display
     */
    String getName();

    /**
     * @param name to display into this player's hud
     */
    void setName(String name);

    /**
     * @return player's score to display
     */
    String getScore();

    /**
     * @param score to display into this player's hud
     */
    void setScore(String score);

    /**
     * @return if this player is still alive
     */
    boolean isAlive();

    /**
     * @param alive sets if this player is alive or not
     */
    void setAlive(boolean alive);

    /**
     * @param sprite representing this player's snake
     */
    void setSnakeSprite(Sprite sprite);

    /**
     * Initializes a new list of sprite representing every active effect onto this player.<p>
     * These sprite will be added by {@link #addEffectSprite(Sprite)} printed next to player's head sprite. <p>
     * This list will then be terminated by {@link #endEffectSpriteList()}
     */
    void newEffectSpriteList();

    /**
     * @param sprite representing a currently active effect
     */
    void addEffectSprite(Sprite sprite);

    /**
     * Terminates the list created into {@link #newEffectSpriteList()} and makes it available to be printed.
     */
    void endEffectSpriteList();

    /**
     * @return a list containing every sprite that needs to be printed into this player's hud
     */
    List<Sprite> getSpriteList();

}
