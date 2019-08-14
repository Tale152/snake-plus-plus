package implementation.view.game;

import java.util.ArrayList;
import java.util.List;

import design.view.game.Background;
import design.view.game.GameHud;
import design.view.game.PlayerHud;
import design.view.game.ResourcesLoader;

/**
 * @see GameHud
 * @author Alessandro Talmi
 */
public class GameHudImpl implements GameHud {

    private String time;
    private final List<PlayerHud> playerHUDs = new ArrayList<>();
    private final Background hudBg;

    /**
     * @param nPlayer that will be displayed
     * @param loader used to get all graphical resources needed to draw
     */
    public GameHudImpl(final int nPlayer, final ResourcesLoader loader) {
        hudBg = loader.getHudBackground();
        time = "";
        for (int i = 0; i < nPlayer; ++i) {
            playerHUDs.add(new PlayerHudImpl());
        }
    }

    @Override
    public final void setTime(final String time) {
        this.time = time;
    }

    @Override
    public final String getTime() {
        return time;
    }

    @Override
    public final Background getHudBackground() {
        return hudBg;
    }

    @Override
    public final List<PlayerHud> getPlayerHUDs() {
        return new ArrayList<>(playerHUDs);
    }

}
