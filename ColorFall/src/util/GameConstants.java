package util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class GameConstants {
	public static final long ONE_SECOND = 1000000000; // nanoseconds

	public static final Font GAME_FONT = loadFont();

	private static Font loadFont() {
		InputStream fontStream = GameConstants.class.getResourceAsStream("/font/LCD_Solid.ttf");
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
			return font.deriveFont(Font.PLAIN, 24);
		} catch (FontFormatException e) {
		} catch (IOException e) {
		}
		return null;
	}
}
