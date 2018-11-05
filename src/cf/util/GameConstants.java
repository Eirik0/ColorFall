package cf.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class GameConstants {
    public static final long ONE_SECOND = 1000000000; // in nanoseconds
    public static final long ONE_MILLISECOND = 1000000;

    public static final Font GAME_FONT = loadFont();

    private static Font loadFont() {
        try (InputStream fontStream = GameConstants.class.getResourceAsStream("/cf/font/LCD_Solid.ttf")) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            return font.deriveFont(Font.PLAIN, 24);
        } catch (FontFormatException | IOException e) {
        }
        return null;
    }
}
