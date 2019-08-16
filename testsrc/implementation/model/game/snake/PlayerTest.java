package implementation.model.game.snake;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import design.model.game.Player;
import design.model.game.PlayerNumber;

/**
 * Tests regarding player information.
 */
public class PlayerTest {

    private static final int SCORE_SUPPOSED = 300;
    /**
     * Test if the player information are set correctly and if the score works.
     */
    @Test
    public void testPlayer() {
        Player player;

        try {
            player = SnakeComponentsFactoryForTest.createPlayer(null, "Ale");
            fail("Player number cannot be null");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception");
        }

        try {
            player = SnakeComponentsFactoryForTest.createPlayer(PlayerNumber.PLAYER1, null);
            fail("Player name cannot be null");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("wrong exception");
        }

        player = SnakeComponentsFactoryForTest.createPlayer(PlayerNumber.PLAYER1, "Ale");

        assertEquals("Check if the player number is the player1", player.getPlayerNumber(), PlayerNumber.PLAYER1);
        assertEquals("Check if the player name is Ale", player.getName(), "Ale");
        assertEquals("Check if the initial score is 0", player.getScore(), 0);
        assertEquals("Check if the initial score multiplier is 1", player.getScoreMultiplier(), 1, 0);

        player.addScore(100);
        assertEquals("Check if the score is 100, after adding 100 points and the score multiplier is 1",
                player.getScore(), 100);
        player.applyScoreMultiplier(2);
        assertEquals("Check if the score multiplier is 2", player.getScoreMultiplier(), 2, 0);
        player.addScore(100);
        assertEquals("Check if the score is 300, after adding 100 points, the score was 100 and the score multiplier is 2",
                player.getScore(), SCORE_SUPPOSED);
        player.reduceScore(100);
        assertEquals("Check if the score is 100, after subtacting 100 points, the score was 300 and the score multiplier is 2", 
                player.getScore(), 100);
        player.applyScoreMultiplier(1);
        player.reduceScore(100);
        assertEquals("Checking if the score is 0", player.getScore(), 0);
        player.reduceScore(100);
        assertEquals("Check if the score is 0 when trying to go negative", player.getScore(), 0);
    }

}
