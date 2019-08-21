package implementation.view.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import design.view.game.Background;
import design.view.game.ResourcesLoader;
import design.view.game.Sprite;
import implementation.controller.PathUtils;
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
     * @param resourcesPath path were to find resources to load
     * @param nCellsWidth number of cells composing field's width
     * @param nCellsHeight number of cells composing field's height
     * @throws FileNotFoundException if there are problem finding files into specified path
     * @throws IOException if there are problem finding files into specified path
     */
    public ResourcesLoaderFromFile(final Path resourcesPath, final double nCellsWidth, final double nCellsHeight) 
            throws FileNotFoundException, IOException {

        final double hudTopBottomHeight = SCREEN_SIZE.getHeight() * HUD_PERCENTAGE;
        final double fieldHeight =  SCREEN_SIZE.getHeight() - (hudTopBottomHeight * 2);
        final double spriteSize = fieldHeight / nCellsHeight;

        readDirectory(getDirectory(resourcesPath, ITEMS_DIRECTORY), items, spriteSize, spriteSize);
        readDirectory(getDirectory(resourcesPath, WALLS_DIRECTORY), walls, spriteSize, spriteSize);
        readDirectory(getDirectory(resourcesPath, BODYPARTS_DIRECTORY), bodyParts, spriteSize, spriteSize);

        final Path bgPath = resourcesPath.resolve(BACKGROUNDS_DIRECTORY);
        fieldBg = readBackground(bgPath.resolve(BACKGROUND_FIELD), spriteSize * nCellsWidth, fieldHeight);
        hudBg = readBackground(bgPath.resolve(BACKGROUND_HUD), SCREEN_SIZE.getWidth(), SCREEN_SIZE.getHeight());
        deadPlayer = new SpriteImpl(DEAD_PLAYER.replaceFirst("[.][^.]+$", ""), 
                new Image(bgPath.resolve(DEAD_PLAYER).toUri().toString()));
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

    private void readDirectory(final Path directory, final List<Sprite> container, 
            final double maxSpriteWidth, final double maxSpriteHeight) throws FileNotFoundException, IOException {
        Files.walk(directory, 1).forEach(p -> {
            container.add(new SpriteImpl(
                    p.getFileName().toString().replaceFirst("[.][^.]+$", ""),
                    new Image(p.toUri().toString(), maxSpriteWidth, maxSpriteHeight, false, false)));
        });
    }

    private Path getDirectory(final Path path, final String directoryName) throws IOException {
        //final File directory = new File(path + File.separator + directoryName);
        final Path directory = Files.walk(path, 1).filter(p ->
                p.getFileName().toString().contains(directoryName)
        ).findAny().get();
                //PathUtils.getResourcePath(path.toString() + File.separator + directoryName);
        //check that everything is OK
        if (!Files.exists(directory) || !Files.isReadable(directory) || !Files.isDirectory(directory)) {
            throw new IllegalStateException("There are problems with directory " + path);
        }
        return directory;
    }

    private Background readBackground(final Path path, final double width, final double height) 
            throws FileNotFoundException, IOException {
        //final File file = new File(path);
        //final FileInputStream fis = new FileInputStream(file.getCanonicalPath().toString());
        final Image bg = new Image(path.toUri().toURL().toString(), width, height, false, false);
        return new BackgroundImpl(bg, width, height);
    }

    @Override
    public final Sprite getDeadPlayerIndicator() {
        return deadPlayer;
    }

}
