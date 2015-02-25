package gamestate.colorfall;

import game.GameGrid;
import util.DrawingUtilities;

public class GameSizer {
	private static int componentWidth;
	private static int componentHeight;

	private static double cellSize;

	private static int gridWidth;
	private static int gridHeight;

	private static double offsetX;
	private static double offsetY;

	public GameSizer(int width, int height) {
		resizeTo(width, height);
	}

	public static boolean resizeTo(int width, int height) {
		if ((width <= 0 || height <= 0) || (width == componentWidth && height == componentHeight)) {
			return false;
		}

		componentWidth = width;
		componentHeight = height;

		cellSize = Math.min((double) width / GameGrid.WIDTH, (double) height / GameGrid.HEIGHT);

		gridWidth = DrawingUtilities.round(cellSize * GameGrid.WIDTH);
		gridHeight = DrawingUtilities.round(cellSize * GameGrid.HEIGHT);

		offsetX = (double) (componentWidth - gridWidth) / 2;
		offsetY = (double) (componentHeight - gridHeight) / 2;

		return true;
	}

	public static int getComponentWidth() {
		return componentWidth;
	}

	public static int getComponentHeight() {
		return componentHeight;
	}

	public static double getCellSize() {
		return cellSize;
	}

	public static int getGridWidth() {
		return gridWidth;
	}

	public static int getGridHeight() {
		return gridHeight;
	}

	public static int getOffsetX() {
		return DrawingUtilities.round(offsetX);
	}

	public static int getOffsetY() {
		return DrawingUtilities.round(offsetY);
	}

	public static int getCellX(int x, double dx) {
		return DrawingUtilities.round(x * cellSize + dx);
	}

	public static int getCellY(int y, double dy) {
		return DrawingUtilities.round(y * cellSize + dy);
	}

	public static int getCellWidth(int x, double dx) {
		return getCellX(x + 1, dx) - getCellX(x, 0);
	}

	public static int getCellHeight(int y, double dy) {
		return getCellY(y + 1, dy) - getCellY(y, 0);
	}
}
