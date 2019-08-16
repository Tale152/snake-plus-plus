package implementation.model.game.snake;

import design.model.game.Player;
import design.model.game.PlayerNumber;
/**
 * This class is part of the snake properties, here there are the method to use
 * to set all the player properties, such as the player number, name, current score
 * and the score multiplier.
 * @author Elisa Tronetti
 */
public class PlayerImpl implements Player {

    private static final int MULTIPLIER = 1;
    private static final int SCORE = 0;

    private final PlayerNumber playerNumber;
    private final String playerName;
    private int score;
    private double scoreMultiplier;

    /**
     * Set all the default player information, like player number, 
     * score, name and multiplier.
     * @param playerNumber is the number of the player
     * @param playerName is the name of the player
     */
    public PlayerImpl(final PlayerNumber playerNumber, final String playerName) {
        checkPlayerNumber(playerNumber);
        this.playerNumber = playerNumber;
        checkName(playerName);
        this.playerName = playerName;
        this.score = SCORE;
        this.scoreMultiplier = MULTIPLIER;
    }

    @Override
    public final PlayerNumber getPlayerNumber() {
        return this.playerNumber;
    }

    @Override
    public final String getName() {
        return this.playerName;
    }

    @Override
    public final void addScore(final int score) {
        checkScore(score);
        this.score += (score * this.scoreMultiplier);
    }

    @Override
    public final void reduceScore(final int score) {
        checkScore(score);
        if (this.score - (score * this.scoreMultiplier) > 0) {
            this.score -= (score * this.scoreMultiplier);
        } else {
            this.score = 0;
        }
    }

    @Override
    public final void applyScoreMultiplier(final double mult) {
        this.scoreMultiplier = mult;
    }

    @Override
    public final double getScoreMultiplier() {
        return this.scoreMultiplier;
    }

    @Override
    public final int getScore() {
        return this.score;
    }

    private void checkScore(final int score) {
        if (score < 0) {
            throw new IllegalArgumentException();
        }
    }

    private void checkPlayerNumber(final PlayerNumber p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
    }

    private void checkName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return a string that contains all the player info, such as
     * the player name, number, the current score and the score multiplier.
     */
    public String toString() {
        return "Player number: " + this.playerNumber + "\n" 
                + "Player name: " + this.playerName + "\n"
                + "Player score: " + this.score + "\n"
                + "Player multiplier: " + this.scoreMultiplier + "\n";
    }

}
