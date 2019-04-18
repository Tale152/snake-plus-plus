package design.controller.game;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import design.model.game.Game;

public interface GameInitializer {

	public Game getGame();
	
	public List<InputStream> getSnakesSpriteSheet() throws FileNotFoundException;
	
	public InputStream getItemsSpriteSheet() throws FileNotFoundException;
	
}
