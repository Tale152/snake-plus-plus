package design.model.game;

/**
 * A set of properties regarding the player behind snake, this includes his name and score.
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface Player {

    /**
     * @return the number identifying this player
     */
    PlayerNumber getPlayerNumber();

    /**
     * @return the name associated with this snake
     */
    String getName();

    /**
     * @param score to be added to current player's score
     */
    void addScore(int score);

    /**
     * @param score to be removed from current player's score
     */
    void reduceScore(int score);

    void applyScoreMultiplier(double mult);

    double getScoreMultiplier();

    /**
     * @return current score
     */
    int getScore();

}
