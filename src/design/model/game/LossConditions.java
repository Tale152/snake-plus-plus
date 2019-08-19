package design.model.game;

import java.util.List;

/**
 * The conditions to loose a game. Not necessarily all conditions are active.
 */
public interface LossConditions {

    /**
     * @param snakes list of every snake into the field
     * @return whether or not you have loose because all snake died
     */
    boolean checkSnakes(List<Snake> snakes);

    /**
     * @param time current game time (milliseconds)
     * @return whether or not you have loose because certain time is reached
     */
    boolean checkTime(Long time);

}
