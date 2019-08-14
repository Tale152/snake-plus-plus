package design.view.game;

import java.util.List;

public interface GameHud {

    Background getHudBackground();

    void setTime(String time);

    String getTime();

    List<PlayerHud> getPlayerHUDs();

}
