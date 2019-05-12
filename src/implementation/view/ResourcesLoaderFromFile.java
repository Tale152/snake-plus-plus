package implementation.view;

import java.io.*;
import java.util.*;
import design.view.*;
import javafx.scene.image.Image;

public class ResourcesLoaderFromFile implements ResourcesLoader {

	//directories where resources are located (inside path)
	private final static String ITEMS_DIRECTORY = "Items";
	private final static String WALLS_DIRECTORY = "Walls";
	private final static String BODYPARTS_DIRECTORY = "BodyParts";
	private final static String BACKGROUNDS_DIRECTORY = "Backgrounds";
	
	//background names
	private final static String BACKGROUND_FIELD = "Field.png";
	private final static String BACKGROUND_HUD_TOP = "Hud_top.png";
	private final static String BACKGROUND_HUD_RIGHT = "Hud_right.png";
	private final static String BACKGROUND_HUD_BOTTOM = "Hud_bottom.png";
	private final static String BACKGROUND_HUD_LEFT = "Hud_left.png";
	
	private final List<Sprite> items = new ArrayList<>();
	private final List<Sprite> walls = new ArrayList<>(); 
	private final List<Sprite> bodyParts = new ArrayList<>();
	private final Background fieldBg;
	private final Background hudTop;
	private final Background hudRight;
	private final Background hudBottom;
	private final Background hudLeft;
	
	public ResourcesLoaderFromFile(String path, double nCellsWidth, double nCellsHeight, 
			double maxScreenWidth, double maxScreenHeight, double hudPercentage) throws FileNotFoundException, IOException {
		
		if (hudPercentage >= 0.5 || hudPercentage < 0) {
			throw new IllegalArgumentException();
		}
		double hudTopBottomHeight = maxScreenHeight * hudPercentage;
		double fieldHeight =  maxScreenHeight - (hudTopBottomHeight * 2);
		double spriteSize = fieldHeight / nCellsHeight;
		double fieldWidth = spriteSize * nCellsWidth;
		double hudLeftRightWidth = (maxScreenWidth - fieldWidth) / 2;
		if (hudLeftRightWidth < 0) {
			throw new IllegalStateException("error on proportions");
		}
		
		readDirectory(getDirectory(path, ITEMS_DIRECTORY), items, spriteSize, spriteSize);
		readDirectory(getDirectory(path, WALLS_DIRECTORY), walls, spriteSize, spriteSize);
		readDirectory(getDirectory(path, BODYPARTS_DIRECTORY), bodyParts, spriteSize, spriteSize);
		
		String bgPath = path + File.separator + BACKGROUNDS_DIRECTORY + File.separator;
		fieldBg = readBackground(bgPath + BACKGROUND_FIELD, fieldWidth, fieldHeight);
		hudTop = readBackground(bgPath + BACKGROUND_HUD_TOP, maxScreenWidth, hudTopBottomHeight);
		hudBottom = readBackground(bgPath + BACKGROUND_HUD_BOTTOM, maxScreenWidth, hudTopBottomHeight);
		hudRight = readBackground(bgPath + BACKGROUND_HUD_RIGHT, hudLeftRightWidth, fieldHeight);
		hudLeft = readBackground(bgPath + BACKGROUND_HUD_LEFT, hudLeftRightWidth, fieldHeight);
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
	public Background getFieldBg() {
		return fieldBg;
	}

	@Override
	public Background getTopHudBg() {
		return hudTop;
	}

	@Override
	public Background getRightHudBg() {
		return hudRight;
	}

	@Override
	public Background getBottomHudBg() {
		return hudBottom;
	}

	@Override
	public Background getLeftHudBg() {
		return hudLeft;
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
