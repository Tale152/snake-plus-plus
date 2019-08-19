package implementation.controller;

import java.io.File;

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
    public static final String MENU = RES + SEP + "menu" + SEP;
    /**
     * Path to world stages.
     */
    public static final String WORLDS   = RES + SEP  + "stages" + SEP + "worlds" + SEP;

    /**
     * Path to classic stages.
     */
    public static final String CLASSIC  = RES + SEP + "stages" + SEP + "classic" + SEP;

    /**
     * Path to the music of this application.
     */
    public static final String THEMES = RES + SEP + "soundtrack" + SEP;

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

    private PathUtils() { }
}
