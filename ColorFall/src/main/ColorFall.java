package main;

import gamestate.colorfall.ColorFallState;
import gamestate.menu.MenuItem;
import gamestate.menu.MenuState;
import gamestate.menu.OptionsMenuState;

import javax.swing.JFrame;

import util.FileUtilities;

public class ColorFall {
	private static final String TITLE = "Color Fall";

	public static ColorFall instance;

	private final GameDelegate gameDelegate;

	public final MenuState mainMenuState;
	private final OptionsMenuState optionsMenuState;

	public static void main(String[] args) {
		JFrame mainFrame = new JFrame(TITLE);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setFocusable(false);
		mainFrame.setResizable(false);

		instance = new ColorFall(mainFrame);

		mainFrame.setVisible(true);

		new Thread(instance.gameDelegate).start();
		instance.gameDelegate.gamePanel.requestFocus();
	}

	public ColorFall(JFrame mainFrame) {
		loadSettings();

		mainMenuState = new MenuState();
		mainMenuState.addMenuItem(new MenuItem("Start", () -> gameDelegate.setState(new ColorFallState(gameDelegate))));
		mainMenuState.addMenuItem(new MenuItem("Options", () -> gameDelegate.setState(optionsMenuState)));
		mainMenuState.addMenuItem(new MenuItem("Exit", () -> System.exit(0)));

		gameDelegate = new GameDelegate(mainMenuState);

		optionsMenuState = new OptionsMenuState(gameDelegate, mainMenuState, mainFrame);

		mainFrame.setContentPane(gameDelegate.gamePanel);

		mainFrame.pack();
	}

	private static void loadSettings() {
		FileUtilities.initFromFile(GameSettings.SETTINGS_FILE_PATH, fileList -> GameSettings.init(fileList), () -> GameSettings.init());
	}

	public static void saveSettings() {

	}
}
