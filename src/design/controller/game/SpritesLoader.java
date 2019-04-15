package design.controller.game;

import java.io.InputStream;
import java.util.List;

public interface SpritesLoader {

	public List<InputStream> getSnakesSpriteSheet();
	
	public InputStream getItemsSpriteSheet();
	
}
