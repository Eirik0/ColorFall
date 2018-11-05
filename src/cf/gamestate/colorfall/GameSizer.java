package gamestate.colorfall;

import game.GameGrid;
import util.DrawingUtilities;

public class GameSizer {
	private static double cellSize;

	private static int gridWidth;
	private static int gridHeight;

	private static double offsetX;
	private static double offsetY;

	public GameSizer(int width, int height) {
		resizeTo(width, height);
	}

	public static void resizeTo(int componentWidth, int componentHeight) {
		if ((componentWidth <= 0 || componentHeight <= 0)) {
			return;
		}

		cellSize = Math.min((double) componentWidth / GameGrid.WIDTH, (double) componentHeight / GameGrid.HEIGHT);

		gridWidth = DrawingUtilities.round(cellSize * GameGrid.WIDTH);
		gridHeight = DrawingUtilities.round(cellSize * GameGrid.HEIGHT);

		offsetX = (double) (componentWidth - gridWidth) / 2;
		offsetY = (double) (componentHeight - gridHeight) / 2;
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
