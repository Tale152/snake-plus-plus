package implementation.view.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

        readDirectory(resourcesPath.resolve(ITEMS_DIRECTORY), items, spriteSize, spriteSize);
        readDirectory(resourcesPath.resolve(WALLS_DIRECTORY), walls, spriteSize, spriteSize);
        readDirectory(resourcesPath.resolve(BODYPARTS_DIRECTORY), bodyParts, spriteSize, spriteSize);

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

    private void readDirectory(final Path directory, final List<Sprite> container, 
            final double maxSpriteWidth, final double maxSpriteHeight) throws FileNotFoundException, IOException {
        Files.walk(directory, 1).forEach(p -> {
            final Path fileName = p.getFileName();
            if (fileName == null) {
                return;
            }
            container.add(new SpriteImpl(
                    fileName.toString().replaceFirst("[.][^.]+$", ""),
                    new Image(p.toUri().toString(), maxSpriteWidth, maxSpriteHeight, false, false)));
        });
    }

    private Background readBackground(final Path path, final double width, final double height) 
            throws FileNotFoundException, IOException {
        final Image bg = new Image(path.toUri().toString(), width, height, false, false);
        return new BackgroundImpl(bg, width, height);
    }

    @Override
    public final Sprite getDeadPlayerIndicator() {
        return deadPlayer;
    }

}
