package implementation.view.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import design.view.game.Background;
import design.view.game.ResourcesLoader;
import design.view.game.Sprite;
import javafx.scene.image.Image;

/**
 * This implementation of resources loader loads graphical resources from local files.
 * @see ResourcesLoader
 */
public class ResourcesLoaderFromFile implements ResourcesLoader {

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final double HUD_PERCENTAGE = 0.1;
    //directories where resources are located (inside path)
    private static final String ITEMS_DIRECTORY = "Items";
    private static final String WALLS_DIRECTORY = "Walls";
    private static final String BODYPARTS_DIRECTORY = "BodyParts";
    private static final String BACKGROUNDS_DIRECTORY = "Backgrounds";

    //background names
    private static final String BACKGROUND_FIELD = "Field.png";
    private static final String BACKGROUND_HUD = "Hud.png";
    private static final String DEAD_PLAYER = "Dead_signal.png";

    private final List<Sprite> items = new ArrayList<>();
    private final List<Sprite> walls = new ArrayList<>(); 
    private final List<Sprite> bodyParts = new ArrayList<>();
    private final Background fieldBg;
    private final Background hudBg;
    private final Sprite deadPlayer;

    /**
     * @param path path were to find resources to load
     * @param nCellsWidth number of cells composing field's width
     * @param nCellsHeight number of cells composing field's height
     * @throws FileNotFoundException if there are problem finding files into specified path
     * @throws IOException if there are problem finding files into specified path
     */
    public ResourcesLoaderFromFile(final String path, final double nCellsWidth, final double nCellsHeight) 
            throws FileNotFoundException, IOException {

        final double hudTopBottomHeight = SCREEN_SIZE.getHeight() * HUD_PERCENTAGE;
        final double fieldHeight =  SCREEN_SIZE.getHeight() - (hudTopBottomHeight * 2);
        final double spriteSize = fieldHeight / nCellsHeight;

        readDirectory(getDirectory(path, ITEMS_DIRECTORY), items, spriteSize, spriteSize);
        readDirectory(getDirectory(path, WALLS_DIRECTORY), walls, spriteSize, spriteSize);
        readDirectory(getDirectory(path, BODYPARTS_DIRECTORY), bodyParts, spriteSize, spriteSize);

        final String bgPath = path + File.separator + BACKGROUNDS_DIRECTORY + File.separator;
        fieldBg = readBackground(bgPath + BACKGROUND_FIELD, spriteSize * nCellsWidth, fieldHeight);
        hudBg = readBackground(bgPath + BACKGROUND_HUD, SCREEN_SIZE.getWidth(), SCREEN_SIZE.getHeight());
        deadPlayer = new SpriteImpl(DEAD_PLAYER.replaceFirst("[.][^.]+$", ""), 
                new Image(new FileInputStream(bgPath + DEAD_PLAYER)));
    }

    @Override
    public final Sprite getItem(final String name) {
        return getFromList(name, items);
    }

    @Override
    public final Sprite getWall(final String name) {
        return getFromList(name, walls);
    }

    @Override
    public final Sprite getBodyPart(final String name) {
        return getFromList(name, bodyParts);
    }

    @Override
    public final Background getFieldBackground() {
        return fieldBg;
    }

    @Override
    public final Background getHudBackground() {
        return hudBg;
    }

    private Sprite getFromList(final String object, final List<Sprite> source) {
        final Optional<Sprite> result = source.stream().filter(o -> {
            return o.getName().equals(object); })
                .findFirst();
        if (result.isPresent()) {
            return result.get();
        }
        throw new IllegalArgumentException("Cannot find " + object + "into requested list");
    }

    private List<File> initFileList(final File directory) {
        final File[] list = directory.listFiles();
        if (list != null) {
            return new ArrayList<File>(Arrays.asList(list));
        }
        throw new RuntimeException("problems with directory " + directory.getAbsolutePath());
    }

    private void readDirectory(final File directory, final List<Sprite> container, 
            final double maxSpriteWidth, final double maxSpriteHeight) throws FileNotFoundException, IOException {
        final List<File> listFile = initFileList(directory);
        for (final File file : listFile) {
            //instantiating a new Sprite with file name (without extension) and scaled Image
            container.add(new SpriteImpl(file.getName().replaceFirst("[.][^.]+$", ""), 
                    new Image(file.toURI().toString(), maxSpriteWidth, maxSpriteHeight, false, false)));
        }
    }

    private File getDirectory(final String path, final String directoryName) {
        final File directory = new File(path + File.separator + directoryName);
        //check that everything is OK
        if (!directory.exists() || !directory.canRead() || !directory.isDirectory()) {
            throw new IllegalStateException("There are problems with directory " + path);
        }
        return directory;
    }

    private Background readBackground(final String path, final double width, final double height) 
            throws FileNotFoundException, IOException {
        final File file = new File(path);
        final FileInputStream fis = new FileInputStream(file.getCanonicalPath().toString());
        return new BackgroundImpl(new Image(fis, width, height, false, false), width, height);
    }

    @Override
    public final Sprite getDeadPlayerIndicator() {
        return deadPlayer;
    }

}
