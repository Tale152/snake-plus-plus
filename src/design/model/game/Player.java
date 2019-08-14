package design.model.game;

public interface Player {

    PlayerNumber getPlayerNumber();

    String getName();

    void addScore(int score);

    void reduceScore(int score);

    void applyScoreMultiplier(double mult);

    double getScoreMultiplier();

    int getScore();

}
