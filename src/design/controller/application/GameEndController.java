package design.controller.application;

/**
 * The GameEndController has to appear to the player at the end of a game to tell him if he has 
 * won/lost and why.
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface GameEndController {
    /**
     * @param reason why the game ended
     */
    void setEndReason(GameEndReason reason);
}
