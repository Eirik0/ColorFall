package cf.main;

import javax.swing.JFrame;

import cf.gamestate.colorfall.ColorFallState;
import cf.gamestate.gameover.HighScoresState;
import cf.gamestate.menu.MenuItem;
import cf.gamestate.menu.MenuState;
import cf.gamestate.menu.OptionsMenuState;
import cf.util.FileUtilities;

public class ColorFallMain {
    private static final String TITLE = "Color Fall";

    public static ColorFallMain instance;

    private final GameDelegate gameDelegate;

    public final MenuState mainMenuState;

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame(TITLE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setFocusable(false);
        mainFrame.setResizable(false);

        instance = new ColorFallMain(mainFrame);

        mainFrame.setVisible(true);

        new Thread(instance.gameDelegate).start();

        instance.gameDelegate.gamePanel.requestFocus();
    }

    public ColorFallMain(JFrame mainFrame) {
        loadSettings();

        gameDelegate = new GameDelegate();

        mainMenuState = new MenuState();
        mainMenuState.addMenuItem(new MenuItem("Start", () -> gameDelegate.setState(new ColorFallState(gameDelegate))));
        mainMenuState.addMenuItem(new MenuItem("High Scores", () -> gameDelegate.setState(new HighScoresState(gameDelegate, null))));
        mainMenuState.addMenuItem(new MenuItem("Options", () -> gameDelegate.setState(new OptionsMenuState(gameDelegate, mainMenuState, mainFrame))));
        mainMenuState.addMenuItem(new MenuItem("Exit", () -> System.exit(0)));

        gameDelegate.setState(mainMenuState);

        mainFrame.setContentPane(gameDelegate.gamePanel);
        mainFrame.pack();
    }

    private static void loadSettings() {
        FileUtilities.initFromFile(GameSettings.SETTINGS_FILE_PATH, fileList -> GameSettings.init(fileList), () -> GameSettings.init());
    }

    public static void saveSettings() {

    }
}
