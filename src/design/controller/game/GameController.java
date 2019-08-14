package design.controller.game;

public interface GameController extends Runnable{

    void playerInput(InputEvent input);

    boolean isGameEnded();

}
