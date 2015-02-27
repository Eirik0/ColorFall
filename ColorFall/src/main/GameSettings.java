package main;

import java.io.File;
import java.util.List;

public class GameSettings {
	public static final String FILE_PATH = System.getenv("APPDATA") + File.separator + GameSettings.SETTINGS_FOLDER_NAME + File.separator;

	public static final String SETTINGS_FILE_PATH = FILE_PATH + GameSettings.SETTINGS_FILE_NAME;
	public static final String HIGH_SCORES_FILE_PATH = FILE_PATH + GameSettings.HIGH_SCORE_FILE_NAME;

	public static final String SETTINGS_FOLDER_NAME = "colorfall";
	public static final String SETTINGS_FILE_NAME = "settings.txt";
	public static final String HIGH_SCORE_FILE_NAME = "highscore.txt";

	private static final int DEFAULT_WIDTH = 1280;
	private static final int DEFAULT_HEIGHT = 720;

	private static final boolean DEFAULT_SHOW_FPS = true;

	public static int componentWidth;
	public static int componentHeight;

	public static boolean showFps;

	public static void init() {
		componentWidth = DEFAULT_WIDTH;
		componentHeight = DEFAULT_HEIGHT;

		showFps = DEFAULT_SHOW_FPS;
	}

	public static void init(List<String> stringList) {
		// TODO Auto-generated constructor stub
	}
}
