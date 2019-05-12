package implementation.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import design.view.GameHud;
import javafx.scene.image.Image;

public class GameHudImpl implements GameHud{

	private Optional<Image> bg;
	private final int nPlayer;
	private Long time;
	private final List<String> names;
	private final List<Integer> scores;
	private final List<Boolean> deads;
	private final GameViewImpl gw;
	
	public GameHudImpl(int nPlayer, GameViewImpl gw) {
		this.gw = gw;
		bg = Optional.empty();
		this.nPlayer = nPlayer;
		time = 0L;
		names = new ArrayList<>();
		scores = new ArrayList<>();
		deads = new ArrayList<>();
		for (int i = 0; i < nPlayer; ++i) {
			names.add("");
			scores.add(0);
			deads.add(false);
		}
	}
	
	@Override
	public void setBackground(Image image) {
		bg = Optional.of(image);
		gw.setDirty();
	}
	
	protected Optional<Image> getBackground() {
		return bg;
	}
	
	@Override
	public void setTime(Long time) {
		this.time = time;
	}

	@Override
	public void setPlayerName(int playerNumber, String name) {
		checkSet(playerNumber);
		gw.setDirty();
		names.set(playerNumber, name);
	}
	
	protected String getPlayerName(int playerNumber) {
		return names.get(playerNumber);
	}

	@Override
	public void setPlayerScore(int playerNumber, int score) {
		checkSet(playerNumber);
		gw.setDirty();
		scores.set(playerNumber, score);
	}

	@Override
	public void setPlayerDead(int playerNumber, boolean dead) {
		checkSet(playerNumber);
		gw.setDirty();
		deads.set(playerNumber, dead);
	}
	
	protected boolean getPlayerDead(int playerNumber) {
		return deads.get(playerNumber);
	}
	
	private void checkSet(int playerNumber) {
		if (playerNumber < 0 || playerNumber >= nPlayer) {
			throw new IllegalArgumentException();
		}
	}

}
