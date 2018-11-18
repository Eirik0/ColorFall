package cf.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;

import cf.gamestate.menu.CosineBackground;
import cf.gamestate.menu.MenuState;
import gt.gameentity.DrawingMethods;
import gt.io.FileUtilities;

public class ColorFall {
    public static final String COLORFALL_FOLDER_NAME = ".ColorFall";

    public static final String SETTINGS_FILE_NAME = "settings.txt";
    public static final String HIGH_SCORE_FILE_NAME = "highscore.txt";

    public static final String BASE_FILE_PATH = System.getProperty("user.home") + File.separator + ColorFall.COLORFALL_FOLDER_NAME + File.separator;

    public static final String SETTINGS_FILE_PATH = BASE_FILE_PATH + ColorFall.SETTINGS_FILE_NAME;
    public static final String HIGH_SCORES_FILE_PATH = BASE_FILE_PATH + ColorFall.HIGH_SCORE_FILE_NAME;

    public static final Font GAME_FONT = FileUtilities.loadFont("/cf/font/LCD_Solid.ttf", Font.TRUETYPE_FONT).deriveFont(Font.PLAIN, 24);
    public static final Font GAME_FONT_SMALL = GAME_FONT.deriveFont(Font.PLAIN, 18);
    public static final Font GAME_FONT_LARGE = GAME_FONT.deriveFont(Font.PLAIN, 30);

    public static final int CAPTURES_PER_LEVEL = 50;

    public static final Color[] PALETTE = new Color[] { Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN };

    private static ColorFall instance;

    private final MenuState mainMenuState;
    private final MenuState optionsMenuState;
    private final CosineBackground menuBackground;

    private int startingLevel = 1;

    private ColorFall(MenuState mainMenuState, MenuState optionsMenuState) {
        this.mainMenuState = mainMenuState;
        this.optionsMenuState = optionsMenuState;
        menuBackground = new CosineBackground();
    }

    public static void initialize(MenuState mainMenuState, MenuState optionsMenuState) {
        instance = new ColorFall(mainMenuState, optionsMenuState);
    }

    public static MenuState getMainMenuState() {
        return instance.mainMenuState;
    }

    public static MenuState getOptionsMenuState() {
        return instance.optionsMenuState;
    }

    public static CosineBackground getMenuBackground() {
        return instance.menuBackground;
    }

    public static int getStartingLevel() {
        return instance.startingLevel;
    }

    public static void setStartingLevel(int level) {
        instance.startingLevel = level;
    }

    public static void drawCell(Graphics2D graphics, double x, double y, double radius, int color) {
        graphics.setColor(PALETTE[color]);
        DrawingMethods.fillCircleS(graphics, x, y, radius);
    }
}
