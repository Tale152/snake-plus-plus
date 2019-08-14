package design.controller.game;

import design.model.game.GameModel;

public interface GameLoader {

    GameModel getGameModel();

    String getLevelName();

    String getLevelDescription();

    int getMaxPlayers();

}
