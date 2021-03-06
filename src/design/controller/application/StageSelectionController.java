package design.controller.application;

import java.io.IOException;
import java.nio.file.Path;

/** A common interface for controllers that allow the user to choose a level
 * to play. 
 * @see WordSelectionController
 * @see ClassicController
 */
public interface StageSelectionController {
    /**
     * Sets the skin that will be used for snakes, walls and items.
     * @param skinPackPath where to find the graphical resources to load
     */
    void setSkinPackPath(Path skinPackPath);

    /**
     * Load the main menu. Only to be called by FXML.
     * @throws IOException If the menu view file is somehow missing or an I/O error occurs,
     * despite it loading just fine beforehand.
     */
    void loadMainMenu() throws IOException;
}
