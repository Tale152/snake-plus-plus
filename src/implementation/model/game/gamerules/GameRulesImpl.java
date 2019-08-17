package implementation.model.game.gamerules;

import java.util.ArrayList;
import java.util.List;

import design.model.game.GameRules;
import design.model.game.ItemRule;
import design.model.game.LossConditions;
import design.model.game.WinConditions;

/**
 * @see GameRules
 * @author Alessandro Talmi
 */
public class GameRulesImpl implements GameRules {

    private final WinConditions win;
    private final LossConditions loss;
    private final List<ItemRule> itemRules;
    private final long initialSnakeDelta;
    private final double initialSnakeMultiplier;
    private final long initialTime;
    private final boolean timeGoingForward;

    /**
     * @param win this game rule's win conditions
     * @param loss this game rules's loss conditions
     * @param itemRules this game rules's list of item rules
     * @param initialSnakeDelta how much initial snake delta (milliseconds) will be
     * @param initialSnakeMultiplier how much initial snake score multiplier will be
     * @param initialTime starting game time (milliseconds)
     * @param timeGoingForward whether or not time goes forward or backward
     */
    public GameRulesImpl(final WinConditions win, final LossConditions loss, final List<ItemRule> itemRules, 
            final long initialSnakeDelta, final double initialSnakeMultiplier, 
            final long initialTime, final boolean timeGoingForward) {
        this.win = win;
        this.loss = loss;
        this.itemRules = itemRules;
        this.initialSnakeDelta = initialSnakeDelta;
        this.initialSnakeMultiplier = initialSnakeMultiplier;
        this.initialTime = initialTime;
        this.timeGoingForward = timeGoingForward;
    }

    @Override
    public final WinConditions getWinConditions() {
        return win;
    }

    @Override
    public final LossConditions getLossConditions() {
        return loss;
    }

    @Override
    public final List<ItemRule> getItemRules() {
        return new ArrayList<>(itemRules);
    }

    @Override
    public final long getInitialSnakeDelta() {
        return initialSnakeDelta;
    }

    @Override
    public final double getInitialSnakeMultiplier() {
        return initialSnakeMultiplier;
    }

    @Override
    public final long getInitialTime() {
        return initialTime;
    }

    @Override
    public final boolean isTimeGoingForward() {
        return timeGoingForward;
    }

}
