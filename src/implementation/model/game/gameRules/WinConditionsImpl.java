package implementation.model.game.gameRules;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import design.model.game.Snake;
import design.model.game.WinConditions;

/**
 * @see WinConditions
 * @author Alessandro Talmi
 */
public class WinConditionsImpl implements WinConditions {

    @JsonProperty("snakeLength")
    private final Optional<Integer> snakeLength;

    @JsonProperty("scoreGoal")
    private final Optional<Integer> scoreToReach;

    @JsonProperty("timeGoal")
    private final Optional<Long> timeToReach;

    @JsonProperty("timeForward")
    private final boolean timeGoesForward;

    /**
     * @param snakeLength whether or not check if any snake reached certain size
     * @param score whether or not check if any snake reached certain score
     * @param time whether or not check if game reached specified time
     * @param timeGoesForward defines behavior when checking if you reached specified time
     */
    public WinConditionsImpl(final Optional<Integer> snakeLength, final Optional<Integer> score,
            final Optional<Long> time, final boolean timeGoesForward) {
        if (snakeLength.isPresent() && snakeLength.get() < 0) {
            throw new IllegalArgumentException("snakeLenght cannot be less than 0");
        }
        if (score.isPresent() && score.get() < 0) {
            throw new IllegalArgumentException("score cannot be less than 0");
        }
        if (time.isPresent() && time.get() < 0) {
            throw new IllegalArgumentException("time cannot be less than 0");
        }
        this.snakeLength = snakeLength;
        this.scoreToReach = score;
        this.timeToReach = time;
        this.timeGoesForward = timeGoesForward;
    }
    @Override
    public final boolean checkSnakeLength(final List<Snake> snakes) {
        if (snakeLength.isPresent()) {
            for (Snake s : snakes) {
                if (s.isAlive() && s.getBodyParts().size() >= snakeLength.get()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public final boolean checkScore(final List<Snake> snakes) {
        if (scoreToReach.isPresent()) {
            for (Snake s : snakes) {
                if (s.isAlive() && s.getPlayer().getScore() >= scoreToReach.get()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public final boolean checkTime(final Long time) {
        if (timeToReach.isPresent()) {
            if (timeGoesForward) {
                return timeToReach.get() <= time;
            } else {
                return timeToReach.get() >= time;
            }
        }
        return false;
    }

}

