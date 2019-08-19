package implementation.view.game;

import design.view.game.Sprite;
import javafx.scene.image.Image;

/**
 * @see Sprite
 */
public class SpriteImpl implements Sprite {

    private final String name;
    private final Image sprite;

    /**
     * @param name of the sprite
     * @param sprite the actual sprite Image
     */
    public SpriteImpl(final String name, final Image sprite) {
        this.name = name;
        this.sprite = sprite;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final Image getSprite() {
        return sprite;
    }
}
