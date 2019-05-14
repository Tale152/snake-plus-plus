package implementation.controller.game;

import design.controller.game.*;
import design.model.game.Game;
import implementation.model.game.GameImpl;

public class GameInitializerImpl implements GameInitializer {

	private final GameLoader gameLoader;
	private final Long time;
	
	public GameInitializerImpl(GameLoader gameLoader, Long time) {
		check(gameLoader, time);
		this.gameLoader = gameLoader;
		this.time = time;
	}
	
	@Override
	public Game getGame() {
		return new GameImpl(gameLoader.getGameRules(), 
				gameLoader.getInitialGameState(), 
				gameLoader.getWinConditions(), gameLoader.getLossConditions(), 
				time);
	}
	
	private void check(GameLoader gameLoader, Long time) {
		if (gameLoader == null || time == null) {
			throw new NullPointerException();
		}
		if (time < 0 ) {
			throw new IllegalArgumentException();
		}
	}

}
