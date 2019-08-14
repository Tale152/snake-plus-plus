package design.model.game;

/**
<<<<<<< HEAD
 * A player is an entity that is associated to a snake, used to recognize the different snake by the owner.
 * @author Elisa Tronetti
 * @author Alessandro Talmi
 * @author Nicola Orlando
 *
 */
public interface Player {

/**
 * @return the player number, which can be player1, player2...
 */
    PlayerNumber getPlayerNumber();

/**
 * @return the player name
 */
    String getName();

/**
 * @param score it is the number of points to add to the player score
 */
    void addScore(int score);

/**
 * @param score it is the number of point to subtract to the player score
 */
=======
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

/**
 * @param mult the score to add/subtract to the total score is multiplied by this value
 */
    void applyScoreMultiplier(double mult);

/**
 * @return the value of the multiplier
 */
    double getScoreMultiplier();

/**
 * @return the total score of the player
 */
    int getScore();

}
