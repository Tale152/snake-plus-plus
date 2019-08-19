package implementation.view.game;

import java.util.ArrayList;
import java.util.List;

import design.view.game.PlayerHud;
import design.view.game.Sprite;

/**
 * @see PlayerHud
 */
public class PlayerHudImpl implements PlayerHud {

    private String name;
    private String score;
    private boolean alive;
    private final List<Sprite> actualSprites;
    private final List<Sprite> currentSprites;
    private Sprite playerSprite;

    /**
     * PlayerHudImpl's constructor.
     */
    public PlayerHudImpl() {
        name = "";
        score = "";
        alive = true;
        actualSprites = new ArrayList<>();
        currentSprites = new ArrayList<>();
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final void setName(final String name) {
        this.name = name;
    }

    @Override
    public final String getScore() {
        return score;
    }

    @Override
    public final void setScore(final String score) {
        this.score = score;
    }

    @Override
    public final boolean isAlive() {
        return alive;
    }

    @Override
    public final void setAlive(final boolean alive) {
        this.alive = alive;
    }

    @Override
    public final void setSnakeSprite(final Sprite sprite) {
        playerSprite = sprite;
    }

    @Override
    public final void addEffectSprite(final Sprite sprite) {
        currentSprites.add(sprite);
    }

    @Override
    public final List<Sprite> getSpriteList() {
        final List<Sprite> res = new ArrayList<Sprite>();
        if (playerSprite != null) {
            res.add(playerSprite);
        }
        res.addAll(actualSprites);
        return res;
    }

    @Override
    public final void newEffectSpriteList() {
        currentSprites.clear();
    }

    @Override
    public final void endEffectSpriteList() {
        actualSprites.clear();
        actualSprites.addAll(currentSprites);
    }

}
