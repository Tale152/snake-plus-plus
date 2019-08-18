package design.controller.game;

import design.model.game.GameModel;

/**
 * Loads a game, generating a model and some information about the level.
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface GameLoader {

    /**
     * @return A model of the loaded game, ready to be started.
     */
    GameModel getGameModel();

    /**
     * @return The name of the level.
     */
    String getLevelName();

    /**
     * @return The description of the level.
     */
    String getLevelDescription();

    /**
     * @return The maximum amount of players in this level.
     */
    int getMaxPlayers();

}
