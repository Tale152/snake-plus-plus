package implementation.controller.game;

import java.io.*;
import java.util.List;
import design.controller.game.*;
import design.model.game.Game;
import implementation.model.game.GameImpl;

public class GameInitializerImpl implements GameInitializer {

	private final GameLoader gameLoader;
	private final SpritesLoader spritesLoader;
	private final Long time;
	
	public GameInitializerImpl(GameLoader gameLoader, SpritesLoader spritesLoader, Long time) {
		check(gameLoader, spritesLoader, time);
		this.gameLoader = gameLoader;
		this.spritesLoader = spritesLoader;
		this.time = time;
	}
	
	@Override
	public Game getGame() {
		return new GameImpl(gameLoader.getGameRules(), 
				gameLoader.getInitialGameState(), 
				gameLoader.getWinConditions(), gameLoader.getLossConditions(), 
				time);
	}

	@Override
	public List<InputStream> getSnakesSpriteSheet() throws FileNotFoundException {
		return spritesLoader.getSnakesSpriteSheet();
	}

	@Override
	public InputStream getItemsSpriteSheet() throws FileNotFoundException {
		return spritesLoader.getItemsSpriteSheet();
	}
	
	private void check(GameLoader gameLoader, SpritesLoader spritesLoader, Long time) {
		if (gameLoader == null || spritesLoader == null || time == null) {
			throw new NullPointerException();
		}
		if (time < 0 ) {
			throw new IllegalArgumentException();
		}
	}

}
