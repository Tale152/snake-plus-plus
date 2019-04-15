package implementation.controller.game;

import java.io.InputStream;
import java.util.List;

import design.controller.game.GameInitializer;
import design.controller.game.GameLoader;
import design.controller.game.SpritesLoader;
import design.model.game.Game;

public class GameInitializerImpl implements GameInitializer {

	public GameInitializerImpl(GameLoader gameLoader, SpritesLoader spritesLoader) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InputStream> getSnakesSpriteSheet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getItemsSpriteSheet() {
		// TODO Auto-generated method stub
		return null;
	}

}
