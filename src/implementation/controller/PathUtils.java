package implementation.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

/**
 * Utility class containing constants useful to deal with files.
 */
public final class PathUtils {

    private static final String SEP = File.separator;
    //private static final String HOME = System.getProperty("user.home") + SEP + ".snekpp" + SEP;
    private static final String RES = "res";

    /**
     * Path to graphical resources.
     */
    public static final String RESPACKS = RES + SEP + "resources" + SEP;

    /**
     * Path to descriptions files.
     */
    public static final String DESCRIPTIONS = RES + SEP + "descriptions" + SEP;

    /**
     * Path to the menu images.
     */
    public static final String MENU = "menu" + SEP;
    /**
     * Path to world stages.
     */
    public static final String WORLDS = "stages" + SEP + "worlds" + SEP;

    /**
     * Path to classic stages.
     */
    public static final String CLASSIC = "stages" + SEP + "classic" + SEP;

    /**
     * Path to the music of this application.
     */
    public static final String THEMES = "soundtrack" + SEP;

    /**
     * Prefix of every music file used while playing.
     */
    public static final String GAME_THEMES_PREFIX = "Game_theme_";

    /**
     * Type of musical files used in this application.
     */
    public static final String GAME_THEMES_TYPE = ".mp3";

    /**
     * Type of image files used in this application.
     */
    public static final String IMAGE_TYPE = ".png";

    private static FileSystem fileSystem;

    /**
     * Return the Path representing the requested item.
     * @param path 
     * @return The Path representing the requested item.
     */
    public static synchronized Path getResourcePath(final String path) {
        Path myPath = null;
        try {
            final URI root = PathUtils.class.getResource("").toURI();
            if (root.getScheme().equals("jar")) {
                if (fileSystem == null) {
                    fileSystem = FileSystems.newFileSystem(root, Collections.<String, Object>emptyMap());
                }
                myPath = fileSystem.getPath(path);
            } else {
                myPath = Paths.get(RES + SEP + path);
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return myPath;
    }

    private PathUtils() { }
}
