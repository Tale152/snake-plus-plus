package implementation.model.game.rules;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import design.model.game.LossConditions;
import design.model.game.Snake;

/**
 * @see LossConditions
 * @author Alessandro Talmi
 */
public class LossConditionsImpl implements LossConditions {

    @JsonProperty("checkAllSnakesDied")
    private final boolean checkAllSnakesDied;

    @JsonProperty("gameTime")
    private final Optional<Long> gameTime;

    @JsonProperty("timeForward")
    private final boolean timeGoesForward;

    /**
     * @param checkAllSnakesDied whether or not check if all snake died 
     * @param gameTime whether or not check if you reached specified time (milliseconds)
     * @param timeGoesForward defines behavior when checking if you reached specified time
     */
    public LossConditionsImpl(
            final boolean checkAllSnakesDied, final Optional<Long> gameTime, final boolean timeGoesForward) {
        if (gameTime.isPresent() && gameTime.get() < 0) {
            throw new IllegalArgumentException("gameTime cannot less than zero");
        }
        this.checkAllSnakesDied = checkAllSnakesDied;
        this.gameTime = gameTime;
        this.timeGoesForward = timeGoesForward;
    }

    @Override
    public final boolean checkSnakes(final List<Snake> snakes) {
        if (checkAllSnakesDied) {
            for (final Snake s : snakes) {
                if (s.isAlive()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public final boolean checkTime(final Long time) {
        if (gameTime.isPresent()) {
            if (timeGoesForward) {
                return gameTime.get() <= time;
            } else {
                return gameTime.get() >= time;
            }
        }
        return false;
    }

}
