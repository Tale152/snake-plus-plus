package design.controller.game;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public interface SpritesLoader {

	public List<InputStream> getSnakesSpriteSheet() throws FileNotFoundException;
	
	public InputStream getItemsSpriteSheet() throws FileNotFoundException;
	
}
