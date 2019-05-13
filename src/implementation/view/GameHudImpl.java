package implementation.view;

import java.util.*;
import design.view.*;

public class GameHudImpl implements GameHud{

	private Long time;
	private final List<PlayerHud> playerHUDs = new ArrayList<>();
	private final Background hudBg;
	
	public GameHudImpl(int nPlayer, ResourcesLoader loader) {
		hudBg = loader.getHudBackground();
		time = 0L;
		for (int i = 0; i < nPlayer; ++i) {
			playerHUDs.add(new PlayerHudImpl());
		}
	}

	@Override
	public void setTime(Long time) {
		this.time = time;
	}

	@Override
	public Long getTime() {
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
