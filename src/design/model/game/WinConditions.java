package design.model.game;

import java.util.List;

/**
 * The conditions to win a game. Not necessarily all conditions are active.
 */
public interface WinConditions {

    /**
     * @param snakes a list of every snake into the field
     * @return whether or not you won because one of the snakes reached a certain length
     */
    boolean checkSnakeLength(List<Snake> snakes);

    /**
     * @param snakes a list of every snake into the field
     * @return whether or not you won because one of the snakes reached a certain score
     */
    boolean checkScore(List<Snake> snakes);

    /**
     * @param time current game time (milliseconds)
     * @return whether or not you won because game time reached a certain value
     */
    boolean checkTime(Long time);

}
