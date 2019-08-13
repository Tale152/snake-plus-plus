package implementation.controller;

import java.io.File;

/**
 * Utility class containing constants usefull to del with files.
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 *
 */
public final class Path {

    private Path() { }
    private static final String SEP = File.separator;
    private static final String HOME = System.getProperty("user.home") + SEP + ".snekpp" + SEP;
    private static final String RES = "res";

    // settings file
    public static final String SETTINGS     = HOME + "config.json";
    public static final String SETTINGS_DEF = RES + SEP + "config" + SEP + "default.json";

    /**
     * Path to graphical resources.
     */
    public static final String RESPACKS = RES + SEP + "resources" + SEP;

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
    public static final String GAME_THEMES_PREFIX = "Game_Theme_";

    /**
     * Type of musical files used in this application.
     */
    public static final String GAME_THEMES_TYPE = ".mp3";

}
