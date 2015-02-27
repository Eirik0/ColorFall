package game;

import gameentity.update.CapturedCells;
import gameentity.update.CapturedCells.CapturedCell;
import gameentity.update.DroppingCells;
import gameentity.update.DroppingCells.DroppingCell;
import gameentity.update.UpdateEntity;

import java.util.ArrayList;
import java.util.List;

import util.GridUtilities;

public class GameGrid {
	public static final int UNPLAYED = 0;

	public static final int WIDTH = 8;
	public static final int HEIGHT = 16;

	private int[][] grid;

	public GameGrid() {
		grid = new int[GameGrid.WIDTH][GameGrid.HEIGHT];
	}

	public int get(int x, int y) {
		return grid[x][y];
	}

	public int[][] getGrid() {
		return grid;
	}

	public List<UpdateEntity> placeColumn(FallingColumn column, GameScore score, boolean drop) {
		if (drop) {
			while (column.maybeMoveDown()) {
			}
		}

		grid[column.getX()][column.getY()] = column.getColor1();
		grid[column.getX()][column.getY() - 1] = column.getColor2();
		grid[column.getX()][column.getY() - 2] = column.getColor3();

		return doUpdates(score);
	}

	private List<UpdateEntity> doUpdates(GameScore score) {
		List<UpdateEntity> updates = new ArrayList<>();
		int combo = 1;

		CapturedCells capturedCells = getCapturedCells(score, combo);

		while (capturedCells != null) {
			updates.add(capturedCells);

			DroppingCells droppingCells = getDroppingCells(capturedCells);
			if (droppingCells != null) {
				updates.add(droppingCells);
			}

			capturedCells = getCapturedCells(score, ++combo);
		}

		return updates;
	}

	public CapturedCells getCapturedCells(GameScore score, int combo) {
		List<CapturedCell> captures = new ArrayList<CapturedCell>();
		int[][] updatedGrid = new int[GameGrid.WIDTH][GameGrid.HEIGHT];

		for (int x = 0; x < GameGrid.WIDTH; ++x) {
			for (int y = 0; y < GameGrid.HEIGHT; ++y) {
				int color = grid[x][y];
				if (color != GameGrid.UNPLAYED) {
					if (GridUtilities.isSafe(grid, x, y)) {
						updatedGrid[x][y] = color;
					} else {
						captures.add(new CapturedCell(x, y, color));
					}
				}
			}
		}
		if (captures.size() > 0) {
			score.addToScore(captures.size(), combo);
			grid = updatedGrid;
			return new CapturedCells(captures, GridUtilities.copyGrid(updatedGrid));
		}
		return null;
	}

	private DroppingCells getDroppingCells(CapturedCells capturedCells) {
		List<DroppingCell> drops = new ArrayList<DroppingCell>();
		for (int x = 0; x < GameGrid.WIDTH; ++x) {
			for (int y = GameGrid.HEIGHT - 2; y >= 0; --y) {
				int color = grid[x][y];
				if (color != GameGrid.UNPLAYED && grid[x][y + 1] == GameGrid.UNPLAYED) {
					int dy = 1;
					while (y + dy + 1 < GameGrid.HEIGHT && grid[x][y + dy + 1] == GameGrid.UNPLAYED) {
						++dy;
					}
					drops.add(new DroppingCell(x, y, dy, color));
					grid[x][y + dy] = color;
					grid[x][y] = GameGrid.UNPLAYED;
				}
			}
		}
		if (drops.size() > 0) {
			return new DroppingCells(drops, GridUtilities.newGridWithoutDrops(capturedCells.getGrid(), drops));
		}
		return null;
	}
}
