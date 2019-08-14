package design.view.game;

import design.controller.game.GameController;

public interface GameView {

    GameHud getHUD();

    GameField getField();

    void startRendering();

    void stopRendering();

    GameController getGameController();

}
