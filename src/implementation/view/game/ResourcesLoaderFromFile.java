package implementation.view.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.util.*;
import design.view.game.ResourcesLoader;
import design.view.game.*;
import javafx.scene.image.Image;

public class ResourcesLoaderFromFile implements ResourcesLoader {

	private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private static final double HUD_PERCENTAGE = 0.1;
	//directories where resources are located (inside path)
	private final static String ITEMS_DIRECTORY = "Items";
	private final static String WALLS_DIRECTORY = "Walls";
	private final static String BODYPARTS_DIRECTORY = "BodyParts";
	private final static String BACKGROUNDS_DIRECTORY = "Backgrounds";
	
	//background names
	private final static String BACKGROUND_FIELD = "Field.png";
	private final static String BACKGROUND_HUD = "Hud.png";
	
	private final List<Sprite> items = new ArrayList<>();
	private final List<Sprite> walls = new ArrayList<>(); 
	private final List<Sprite> bodyParts = new ArrayList<>();
	private final Background fieldBg;
	private final Background hudBg;
	
	public ResourcesLoaderFromFile(String path, double nCellsWidth, double nCellsHeight) throws FileNotFoundException, IOException {
		
		double hudTopBottomHeight = SCREEN_SIZE.getHeight() * HUD_PERCENTAGE;
		double fieldHeight =  SCREEN_SIZE.getHeight() - (hudTopBottomHeight * 2);
		double spriteSize = fieldHeight / nCellsHeight;
		
		readDirectory(getDirectory(path, ITEMS_DIRECTORY), items, spriteSize, spriteSize);
		readDirectory(getDirectory(path, WALLS_DIRECTORY), walls, spriteSize, spriteSize);
		readDirectory(getDirectory(path, BODYPARTS_DIRECTORY), bodyParts, spriteSize, spriteSize);
		
		String bgPath = path + File.separator + BACKGROUNDS_DIRECTORY + File.separator;
		fieldBg = readBackground(bgPath + BACKGROUND_FIELD, spriteSize * nCellsWidth, fieldHeight);
		hudBg = readBackground(bgPath + BACKGROUND_HUD, SCREEN_SIZE.getWidth(), SCREEN_SIZE.getHeight());
	}
	
	@Override
	public Sprite getItem(String name) {
		return getFromList(name, items);
	}

	@Override
	public Sprite getWall(String name) {
		return getFromList(name, walls);
	}

	@Override
	public Sprite getBodyPart(String name) {
		return getFromList(name, bodyParts);
	}
	
	@Override
	public Background getFieldBackground() {
		return fieldBg;
	}

	@Override
	public Background getHudBackground() {
		return hudBg;
	}
	
	private Sprite getFromList(String object, List<Sprite> source) {
		Optional<Sprite> result = source.stream().filter(o -> {return o.getName().equals(object);}).findFirst();
		if (result.isPresent()) {
			return result.get();
		}
		throw new IllegalArgumentException("Cannot find " + object + "into requested list");
	}
	
	private void readDirectory(File directory, List<Sprite> container, double maxSpriteWidth, double maxSpriteHeight) throws FileNotFoundException, IOException {
		for (File file : directory.listFiles()) {
			//getting sprite file
			FileInputStream fis = new FileInputStream(file.getCanonicalPath().toString());
			//instantiating a new Sprite with file name (without extension) and scaled Image
			container.add(new SpriteImpl(file.getName().replaceFirst("[.][^.]+$", ""), new Image(fis, maxSpriteWidth, maxSpriteHeight, false, false)));
		}
	}
	
	private File getDirectory(String path, String directoryName) {
		File directory = new File(path+ File.separator + directoryName);
		//check that everything is OK
		if (!directory.exists() || !directory.canRead() || !directory.isDirectory()) {
			throw new IllegalStateException("There are problems with directory " + path);
		}
		return directory;
	}
	
	private Background readBackground(String path, double width, double height) throws FileNotFoundException, IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file.getCanonicalPath().toString());
		return new BackgroundImpl(new Image(fis, width, height, false, false), width, height);
	}

}
