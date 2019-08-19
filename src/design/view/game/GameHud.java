package design.view.game;

import java.util.List;

/**
 * This class contains all the info regarding the general hud and individual player's hud.
 * @see PlayerHud
 */
public interface GameHud {

    /**
     * @return hud's background
     */
    Background getHudBackground();

    /**
     * @param time (milliseconds) that will be displayed (needs to be converted into minutes : seconds)
     */
    void setTime(String time);

    /**
     * @return setted time (milliseconds) that will be displayed (needs to be converted into minutes : seconds)
     */
    String getTime();

    /**
     * @return all player's hud
     */
    List<PlayerHud> getPlayerHUDs();

}
