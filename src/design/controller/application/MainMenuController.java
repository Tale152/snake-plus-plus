package design.controller.application;

import java.io.IOException;

/**
 * The controller of the Main Menu, containing methods to access all the game modes.
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface MainMenuController {

    /**
     * Loads the Classic Mode menu.
     * @throws IOException if levels not found
     */
    void goToClassicMode() throws IOException;

    /**
     * Loads the Level Mode menu.
     * @throws IOException if levels not found
     */
    void goToLevelMode() throws IOException;

}
