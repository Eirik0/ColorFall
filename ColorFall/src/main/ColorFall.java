package main;

import gamestate.colorfall.ColorFallState;
import gamestate.menu.MenuItem;
import gamestate.menu.MenuState;
import gamestate.menu.OptionsMenuState;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JFrame;

import util.FileUtilities;

public class ColorFall {
	private static final String TITLE = "Color Fall";

	private final GameDelegate gameDelegate;

	private final MenuState mainMenuState;
	private final OptionsMenuState optionsMenuState;
	private final ColorFallState colorFallState;

	public static void main(String[] args) {
		JFrame mainFrame = new JFrame(TITLE);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setFocusable(false);
		mainFrame.setResizable(false);

		ColorFall colorFall = new ColorFall(mainFrame);

		mainFrame.setVisible(true);

		new Thread(colorFall.gameDelegate).start();
		colorFall.gameDelegate.gamePanel.requestFocus();
	}

	public ColorFall(JFrame mainFrame) {
		loadSettings();
		loadHighScores();

		mainMenuState = new MenuState();
		mainMenuState.addMenuItem(new MenuItem("Start", () -> gameDelegate.setState(colorFallState)));
		mainMenuState.addMenuItem(new MenuItem("Options", () -> gameDelegate.setState(optionsMenuState)));
		mainMenuState.addMenuItem(new MenuItem("Exit", () -> System.exit(0)));

		gameDelegate = new GameDelegate(mainMenuState);

		optionsMenuState = new OptionsMenuState(gameDelegate, mainMenuState, mainFrame);

		colorFallState = new ColorFallState(gameDelegate);

		mainFrame.setContentPane(gameDelegate.gamePanel);

		mainFrame.pack();
	}

	private static void loadSettings() {
		String filePath = System.getenv("APPDATA") + File.separator + GameSettings.SETTINGS_FOLDER_NAME + File.separator + GameSettings.SETTINGS_FILE_NAME;
		loadFromFile(filePath, fileList -> GameSettings.init(fileList), () -> GameSettings.init());
	}

	public static void saveSettings() {

	}

	private static void loadHighScores() {
		String filePath = System.getenv("APPDATA") + File.separator + GameSettings.SETTINGS_FOLDER_NAME + File.separator + GameSettings.HIGH_SCORE_FILE_NAME;
		loadFromFile(filePath, fileList -> HighScores.init(fileList), () -> HighScores.init());
	}

	public static void saveHighScores() {

	}

	private static void loadFromFile(String filePath, Consumer<List<String>> onSuccess, Runnable onFailure) {
		File settingFile = new File(filePath);
		if (settingFile.exists()) {
			try {
				onSuccess.accept(FileUtilities.fileToList(settingFile));
				return;
			} catch (IOException e) {
			}
		}
		onFailure.run();
	}
}
