package implementation.view.game;

import java.util.*;
import design.view.game.*;

public class GameHudImpl implements GameHud{

	private String time;
	private final List<PlayerHud> playerHUDs = new ArrayList<>();
	private final Background hudBg;
	
	public GameHudImpl(int nPlayer, ResourcesLoader loader) {
		hudBg = loader.getHudBackground();
		time = "";
		for (int i = 0; i < nPlayer; ++i) {
			playerHUDs.add(new PlayerHudImpl());
		}
	}

	@Override
	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String getTime() {
		return time;
	}

	@Override
	public Background getHudBackground() {
		return hudBg;
	}

	@Override
	public List<PlayerHud> getPlayerHUDs() {
		return new ArrayList<>(playerHUDs);
	}

}
