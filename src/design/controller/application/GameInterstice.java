package design.controller.application;

import java.io.FileNotFoundException;
import java.io.IOException;

/** This class handles the transition between various levels in a world, or after a Classic level.
 * 
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface GameInterstice {
    /** Provide to the object how the last game ended.
     *  The interstice will automatically display a "next level" screen if the game is won and it wasn't the last;
     *  it will offer to get back to the main menu after a loss or after winning the last level in a world, or a
     *  classic.
     * @param reason The enum representing how the game ended.
     * @throws IOException 
     */
    void setGameEndReason(GameEndReason reason) throws IOException;

    /** Show the interstice screen to the user.
     * 
     */
    void showInterstice();

    /** Start the next level without showing a screen. 
     *  Used mostly to start the first level in a world, a classic level, or by the "next level" button.
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    void nextLevel() throws FileNotFoundException, IOException;
}
