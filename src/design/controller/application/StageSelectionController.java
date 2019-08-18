package design.controller.application;

/** A common interface for controllers that allow the user to choose a level
 * to play. 
 * @see WordSelectionController
 * @see ClassicController
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface StageSelectionController {
    /**
     * Sets the skin that will be used for snakes, walls and items.
     * @param path where to find the graphical resources to load
     */
    void setSkinPackPath(String path);
}
