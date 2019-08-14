package design.view.game;

import java.util.List;

public interface PlayerHud {

    String getName();

    void setName(String name);

    String getScore();

    void setScore(String score);

    boolean isAlive();

    void setAlive(boolean alive);

    void setSnakeSprite(Sprite sprite);

    void newEffectSpriteList();

    void addEffectSprite(Sprite sprite);

    void endEffectSpriteList();

    List<Sprite> getSpriteList();

}
