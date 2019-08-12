package implementation.controller;

import java.io.File;

public final class Path {
	private static final String SEP = File.separator;
	private static final String HOME = System.getProperty("user.home") + SEP + ".snekpp" + SEP;
	private static final String RES = "res";
	
	// settings file
	public static final String SETTINGS     = HOME + "config.json";
	public static final String SETTINGS_DEF = RES + SEP + "config" + SEP + "default.json";
	
	//resource packs folder
	public static final String RESPACKS = RES + SEP + "resources" + SEP;
	//worlds folder
	public static final String WORLDS   = RES + SEP  + "stages" + SEP + "worlds" + SEP;
	//classic folder
	public static final String CLASSIC  = RES + SEP + "stages" + SEP + "classic" + SEP;
	//public static final String PROGRESS = "";
	
	public static final String THEMES = RES + SEP + "soundtrack" + SEP;
	
	public static final String GAME_THEMES_PREFIX = "Game_Theme_";
	
	public static final String GAME_THEMES_TYPE = ".mp3";
	
}
