package design.controller.application;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The classic controller handles method used into the Classic view. <p>
 * Here you can select "classic levels". In other words when a level ends if you win
 * no other level will start, contrary to the "word levels".
 * @see WordSelectionController
 * @see GameView
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface ClassicController {
    /**
     * Selects the previous level.
     * @throws FileNotFoundException if level file not found
     * @throws IOException if level file not found
     */
    void selectPrev() throws FileNotFoundException, IOException;

    /**
     * Selects the next level.
     * @throws FileNotFoundException if level file not found
     * @throws IOException if level file not found
     */
    void selectNext() throws FileNotFoundException, IOException;

    /**
     * Used when user reduces number of playing players.
     */
    void removePlayer();

    /**
     * Used when user increases number of playing players.
     */
    void addPlayer();

    /**
     * Starts the level giving command to the GameView.
     * @see GameView
     * @throws IOException if level file not found
     */
    void startSelectedLevel() throws IOException;

    /**
     * Sets the skin that will be used for snakes, walls and items.
     * @param path where to find the graphical resources to load
     */
    void setSkinPackPath(String path);
}
