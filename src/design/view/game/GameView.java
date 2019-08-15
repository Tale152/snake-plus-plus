package design.view.game;

import design.controller.game.GameController;

/**
 * Contains everything needed to display a game.
 * @see GameHud
 * @see GameField
 * @see GameController
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface GameView {

    /**
     * @return the game's hud
     */
    GameHud getHUD();

    /**
     * @return the graphical game's field
     */
    GameField getField();

    /**
     * Starts printing.
     */
    void startRendering();

    /**
     * Stops printing.
     */
    void stopRendering();

    /**
     * @return the game controller controlling this level
     */
    GameController getGameController();

}
