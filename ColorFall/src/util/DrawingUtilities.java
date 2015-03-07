package util;

import game.GameGrid;
import gamestate.colorfall.GameSizer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import main.GameSettings;

public class DrawingUtilities {
	public static final Color[] PALETTE = new Color[] { Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN };

	public static void drawGrid(Graphics g, int[][] grid) {
		g.setColor(Color.BLACK);
		g.fillRect(GameSizer.getOffsetX(), GameSizer.getOffsetY(), GameSizer.getGridWidth(), GameSizer.getGridHeight());
		for (int x = 0; x < GameGrid.WIDTH; ++x) {
			for (int y = 0; y < GameGrid.HEIGHT; ++y) {
				int color = grid[x][y];
				if (color != GameGrid.UNPLAYED) {
					drawCell(g, x, y, color, GameSizer.getOffsetX(), GameSizer.getOffsetY());
				}
			}
		}
	}

	public static void drawCell(Graphics g, int x, int y, int color, double dx, double dy) {
		g.setColor(getColor(color));
		g.fillOval(GameSizer.getCellX(x, dx), GameSizer.getCellY(y, dy), GameSizer.getCellWidth(x, 0), GameSizer.getCellHeight(y, 0));
	}

	public static Color getColor(int c) {
		return PALETTE[c];
	}

	public static void drawCenteredString(Graphics g, String text, int y) {
		FontMetrics metrics = g.getFontMetrics();
		double height = metrics.getHeight();
		double width = metrics.stringWidth(text);
		g.drawString(text, round((GameSettings.componentWidth - width) / 2), round(y + height / 3));
	}

	public static int round(double d) {
		return (int) Math.round(d);
	}
}
