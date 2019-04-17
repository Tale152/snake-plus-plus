package implementation.controller.game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import design.controller.game.GameLoader;
import design.model.game.*;
import design.model.game.InitialGameState.InitialPlayerState;
import implementation.model.game.initializers.InitialGameStateImpl;
import implementation.model.game.initializers.InitialPlayerStateImpl;

public class GameLoaderFromFile implements GameLoader {
	
	private final static int MAX_PLAYERS = 4;
	private final Level level;
	private final List<String> playerNames;
	private final List<Integer> playerScores;
	
	public GameLoaderFromFile(File file, List<String> playerNames, List<Integer> playerScores) throws FileNotFoundException, IOException, ClassNotFoundException {
		check(file, playerNames, playerScores);
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		level = (Level)in.readObject();
		in.close();
		this.playerNames = playerNames;
		this.playerScores = playerScores;
		if (playerNames.size() > level.getInitialGameState().getInitialPlayerState().size()) {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public InitialGameState getInitialGameState() {
		InitialGameState loaded = level.getInitialGameState();
		List<InitialPlayerState> ips = new ArrayList<>();
		for (int i = 0; i < playerNames.size(); ++i) {
			InitialPlayerState tmp = loaded.getInitialPlayerState().get(i);
			ips.add(new InitialPlayerStateImpl(playerNames.get(i), tmp.getBodyPoints(), tmp.getDirection(), playerScores.get(i)));
		}
		
		InitialGameState res = new InitialGameStateImpl(loaded.getFieldItems(), loaded.getFieldSize(), ips);
		return res;
	}

	@Override
	public GameRules getGameRules() {
		return level.getGameRules();
	}

	@Override
	public WinConditions getWinConditions() {
		return level.getWinConditions();
	}

	@Override
	public LossConditions getLossConditions() {
		return level.getLossConditions();
	}

	private void check(File file, List<String> playerNames, List<Integer> playerScores) {
		if (file == null || playerNames == null || playerScores == null) {
			throw new NullPointerException();
		}
		if (playerNames.contains(null)) {
			throw new NullPointerException();
		}
		if (playerScores.contains(null)) {
			throw new NullPointerException();
		}
		if (playerNames.size() != playerScores.size()) {
			throw new IllegalStateException();
		}
		if (playerNames.isEmpty() || playerNames.size() > MAX_PLAYERS) {
			throw new IllegalStateException();
		}
	}
}
