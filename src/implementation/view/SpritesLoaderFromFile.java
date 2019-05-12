package implementation.view;

import java.io.*;
import java.util.*;
import design.view.*;
import javafx.scene.image.Image;

public class SpritesLoaderFromFile implements SpritesLoader {

	//directories where sprite are located (inside path)
		private final static String ITEMS_DIRECTORY = "Items";
		private final static String WALLS_DIRECTORY = "Walls";
		private final static String BODYPARTS_DIRECTORY = "BodyParts";
		
		private final List<Sprite> items = new ArrayList<>();
		private final List<Sprite> walls = new ArrayList<>(); 
		private final List<Sprite> bodyParts = new ArrayList<>();
		
		public SpritesLoaderFromFile(String path, double maxSpriteWidth, double maxSpriteHeight) throws FileNotFoundException, IOException {
			//initialize all 3 lists
			readDirectory(getDirectory(path, ITEMS_DIRECTORY), items, maxSpriteWidth, maxSpriteHeight);
			readDirectory(getDirectory(path, WALLS_DIRECTORY), walls, maxSpriteWidth, maxSpriteHeight);
			readDirectory(getDirectory(path, BODYPARTS_DIRECTORY), bodyParts, maxSpriteWidth, maxSpriteHeight);
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

}
