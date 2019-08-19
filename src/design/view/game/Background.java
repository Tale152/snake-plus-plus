package design.view.game;

/**
 * This abstraction represents a graphical background to be printed, independent from used graphical API.
 */
public interface Background {

    /**
     * @return the background to be printed, needs to be casted to used graphical element of used API.
     */
    Object getBackground();

    /**
     * @return background's width
     */
    double getWidth();

    /**
     * @return background's height
     */
    double getHeight();

}
