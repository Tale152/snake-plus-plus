package design.controller.game;

import design.controller.application.GameInterstice;

public interface GameController extends Runnable{

    void playerInput(InputEvent input);

    boolean isGameEnded();
    
    void setInterstice(GameInterstice interstice);

}
