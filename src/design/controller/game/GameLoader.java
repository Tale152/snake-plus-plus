package design.controller.game;

import design.model.game.GameModel;

public interface GameLoader {

    public GameModel getGameModel();

    public String getLevelName();

    public String getLevelDescription();

    public int getMaxPlayers();

}
