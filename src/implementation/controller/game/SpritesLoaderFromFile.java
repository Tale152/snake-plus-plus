package implementation.controller.game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import design.controller.game.SpritesLoader;

public class SpritesLoaderFromFile implements SpritesLoader {

	private final static int MAX_PLAYERS = 4;
	private final List<File> snakes;
	private final File items;
	
	public SpritesLoaderFromFile(List<File> snakes, File items) {
		check(snakes, items);
		this.snakes = snakes;
		this.items = items;
	}
	
	@Override
	public List<InputStream> getSnakesSpriteSheet() throws FileNotFoundException {
		List<InputStream> res = new ArrayList<>();
		for (File f : snakes) {
			res.add(new FileInputStream(f));
		}
		return res;
	}

	@Override
	public InputStream getItemsSpriteSheet() throws FileNotFoundException {
		return new FileInputStream(items);
	}
	
	private void check(List<File> snakes, File items) {
		if (snakes.contains(null) || items == null) {
			throw new NullPointerException();
		}
		if (!items.isFile() || snakes.stream().anyMatch(s -> {return !s.isFile();})) {
			throw new IllegalArgumentException();
		}
		if (!isPNG(items.getName())) {
			throw new IllegalStateException();
		}
		for (File f : snakes) {
			if (!isPNG(f.getName())) {
				throw new IllegalStateException();
			}
		}
		if (snakes.isEmpty() || snakes.size() > MAX_PLAYERS) {
			throw new IllegalStateException();
		}
	}
	
	private boolean isPNG(String fileName) {
		if (fileName.length() > 4) {
			if (fileName.toLowerCase().charAt(fileName.length() -1) == 'g' &&
					fileName.toLowerCase().charAt(fileName.length() -2) == 'n' && 
					fileName.toLowerCase().charAt(fileName.length() -3) == 'p' && 
					fileName.charAt(fileName.length() -4) == '.') {
				return true;
			}
		}
		return false;
	}

}
