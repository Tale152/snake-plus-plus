package implementation.controller.game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import design.controller.game.Sprite;
import design.controller.game.SpritesLoader;
import javafx.scene.image.Image;

public class SpritesLoaderFromFile implements SpritesLoader {
	
	private final static String ITEMS_DIRECTORY = "Items";
	private final static String WALLS_DIRECTORY = "Walls";
	private final static String BODYPARTS_DIRECTORY = "BodyParts";
	private final List<Sprite> items = new ArrayList<>();
	private final List<Sprite> walls = new ArrayList<>(); 
	private final List<Sprite> bodyParts = new ArrayList<>();
	
	public SpritesLoaderFromFile(String path) throws FileNotFoundException, IOException {
		readDirectory(getDirectory(path, ITEMS_DIRECTORY), items);
		readDirectory(getDirectory(path, WALLS_DIRECTORY), walls);
		readDirectory(getDirectory(path, BODYPARTS_DIRECTORY), bodyParts);
	}

	@Override
	public List<Sprite> getItems() {
		return new ArrayList<>(items);
	}

	@Override
	public List<Sprite> getWalls() {
		return new ArrayList<>(walls);
	}

	@Override
	public List<Sprite> getBodyParts() {
		return new ArrayList<>(bodyParts);
	}
	
	private void readDirectory(File directory, List<Sprite> container) throws FileNotFoundException, IOException {
		for (File file : directory.listFiles()) {
			checkPNG(file.getName());
			FileInputStream fis = new FileInputStream(file.getCanonicalPath().toString());
			container.add(new SpriteImpl(file.getName(), new Image(fis)));
		}
	}
	
	private File getDirectory(String path, String directoryName) {
		File directory = new File(path+ File.separator + directoryName);
		if (!directory.canRead() || !directory.isDirectory()) {
			throw new IllegalStateException();
		}
		return directory;
	}
	
	private void checkPNG(String fileName) {
		if (fileName.length() > 4) {
			if (!(fileName.toLowerCase().charAt(fileName.length() -1) == 'g' &&
					fileName.toLowerCase().charAt(fileName.length() -2) == 'n' && 
					fileName.toLowerCase().charAt(fileName.length() -3) == 'p' && 
					fileName.charAt(fileName.length() -4) == '.')) {
				throw new IllegalStateException();
			}
		}
		else {
			throw new IllegalStateException();
		}
	}

}
