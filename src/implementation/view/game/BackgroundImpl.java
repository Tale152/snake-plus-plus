package implementation.view.game;

import design.view.game.Background;
import javafx.scene.image.Image;

/**
 * @see Background
 */
public class BackgroundImpl implements Background {

    private final Image bg;
    private final double width;
    private final double height;

    /**
     * @param bg background image
     * @param width background image's width
     * @param height background image's height
     */
    public BackgroundImpl(final Image bg, final double width, final double height) {
        this.bg = bg;
        this.width = width;
        this.height = height;
    }

    @Override
    public final Image getBackground() {
        return bg;
    }

    @Override
    public final double getWidth() {
        return width;
    }

    @Override
    public final double getHeight() {
        return height;
    }

}
