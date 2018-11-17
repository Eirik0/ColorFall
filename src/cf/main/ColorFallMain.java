package cf.main;

import java.awt.Dimension;

import cf.gamestate.colorfall.ColorFallState;
import cf.gamestate.gameover.HighScores;
import cf.gamestate.gameover.HighScoresState;
import cf.gamestate.menu.MenuItem;
import cf.gamestate.menu.MenuState;
import gt.component.ComponentCreator;
import gt.component.GamePanel;
import gt.component.MainFrame;
import gt.gamestate.GameStateManager;

public class ColorFallMain {
    private static final String TITLE = "Color Fall";

    public static void main(String[] args) {
        ComponentCreator.setCrossPlatformLookAndFeel();

        GamePanel mainPanel = new GamePanel("ColorFall");
        mainPanel.setPreferredSize(new Dimension(ComponentCreator.DEFAULT_WIDTH, ComponentCreator.DEFAULT_HEIGHT));

        GameStateManager.setMainPanel(mainPanel);

        MenuItem startGameMenuItem = new MenuItem("Start", () -> GameStateManager.setGameState(new ColorFallState(ColorFall.getStartingLevel())));
        for (int i = 1; i < 100; ++i) {
            int level = i;
            startGameMenuItem.addSubMenuItem(new MenuItem("* Level: " + level, () -> ColorFall.setStartingLevel(level)));
        }

        MenuState mainMenuState = new MenuState()
                .addMenuItem(startGameMenuItem)
                .addMenuItem(new MenuItem("High Scores", () -> GameStateManager.setGameState(new HighScoresState(HighScores.loadFromFile()))))
                .addMenuItem(new MenuItem("Options", () -> GameStateManager.setGameState(ColorFall.getOptionsMenuState())))
                .addMenuItem(new MenuItem("Exit", () -> System.exit(0)));

        MenuState optionsMenuState = new MenuState()
                .addMenuItem(new MenuItem("Save and Return", () -> GameStateManager.setGameState(ColorFall.getMainMenuState())));

        ColorFall.initialize(mainMenuState, optionsMenuState);

        GameStateManager.setGameState(mainMenuState);

        MainFrame mainFrame = new MainFrame(TITLE, mainPanel);

        mainFrame.show();
    }
}
