package main;

import gamestate.colorfall.ColorFallState;
import gamestate.menu.MenuItem;
import gamestate.menu.MenuState;
import gamestate.menu.OptionsMenuState;

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
		FileUtilities.initFromFile(GameSettings.SETTINGS_FILE_PATH, fileList -> GameSettings.init(fileList), () -> GameSettings.init());
	}

	public static void saveSettings() {

	}

	public static void saveHighScores() {

	}
}
