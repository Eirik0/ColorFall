package cf.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;

import cf.gamestate.menu.CosineBackground;
import gt.gameentity.DrawingMethods;
import gt.gamestate.GameState;
import gt.io.FileUtilities;

public class ColorFall {
    public static final String COLORFALL_FOLDER_NAME = ".ColorFall";

    public static final String SETTINGS_FILE_NAME = "settings.txt";
    public static final String HIGH_SCORE_FILE_NAME = "highscore.txt";

    public static final String BASE_FILE_PATH = System.getProperty("user.home") + File.separator + ColorFall.COLORFALL_FOLDER_NAME + File.separator;

    public static final String SETTINGS_FILE_PATH = BASE_FILE_PATH + ColorFall.SETTINGS_FILE_NAME;
    public static final String HIGH_SCORES_FILE_PATH = BASE_FILE_PATH + ColorFall.HIGH_SCORE_FILE_NAME;

    public static final Font GAME_FONT = FileUtilities.loadFont("/cf/font/LCD_Solid.ttf", Font.TRUETYPE_FONT).deriveFont(Font.PLAIN, 24);
    public static final Font GAME_FONT_LARGE = GAME_FONT.deriveFont(Font.PLAIN, 30);

    public static final int CAPTURES_PER_LEVEL = 50;

    public static final Color[] PALETTE = new Color[] { Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN };

    private static ColorFall instance;

    public final GameState mainMenuState;
    public final GameState optionsMenuState;
    public final CosineBackground menuBackground;

    private ColorFall(GameState mainMenuState, GameState optionsMenuState) {
        this.mainMenuState = mainMenuState;
        this.optionsMenuState = optionsMenuState;
        menuBackground = new CosineBackground();
    }

    public static void initialize(GameState mainMenuState, GameState optionsMenuState) {
        instance = new ColorFall(mainMenuState, optionsMenuState);
    }

    public static ColorFall getInstance() {
        return instance;
    }

    public static String formatTime(double timeDouble) {
        long time = Math.round(timeDouble);
        time /= 100;
        long secondTenths = time % 10;
        time /= 10;
        long seconds = time % 60;
        time /= 60;
        return time + "m " + seconds + "." + secondTenths + "s";
    }

    public static void drawCell(Graphics2D graphics, double x, double y, double radius, int color) {
        graphics.setColor(PALETTE[color]);
        DrawingMethods.fillCircleS(graphics, x, y, radius);
    }
}
