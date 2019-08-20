package design.controller.game;

import design.controller.application.GameInterstice;
/**
 * The game controller handles a game while running.
 * It is the main loop of the game and it has to communicate with the view
 * and the model. 
 * Here are captured the player inputs and everything in the field is updated, 
 * visually and in the game logic. 
 */
public interface GameController extends Runnable {

    /**
     * Method used to take a player input and connect it with an actual action
     * in the field.
     * The input correspond to an action only if the action exists and the snake
     * connected to the action is in the field
     * @param input the player input to bind to and action
     */
    void playerInput(InputEvent input);

    /**
     * @return true if the game is ended, false otherwise. 
     */
    boolean isGameEnded();

    /**
     * Set the GameInterstice instance the controller needs to show and 
     * report the game result to at the end of a level.
     * @param interstice the instance responsible of the current world or classic level.
     */
    void setInterstice(GameInterstice interstice);

}
