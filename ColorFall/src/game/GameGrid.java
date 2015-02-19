package game;

import gameentity.update.CapturedCells;
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

	public List<UpdateEntity> placeColumn(FallingColumn column, boolean drop) {
		if (drop) {
			while (column.maybeMoveDown()) {
			}
		}

		grid[column.getX()][column.getY()] = column.getColor1();
		grid[column.getX()][column.getY() - 1] = column.getColor2();
		grid[column.getX()][column.getY() - 2] = column.getColor3();

		List<UpdateEntity> updates = new ArrayList<>();
		CapturedCells capturedCells = GridUtilities.getCapturedCells(grid);
		while (capturedCells != null) {
			int[][] updatedGrid = capturedCells.getGrid();
			updates.add(capturedCells);

			grid = GridUtilities.copyGrid(updatedGrid);

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

			DroppingCells droppingCells = new DroppingCells(drops, GridUtilities.newGridWithoutDrops(capturedCells.getGrid(), drops));
			updates.add(droppingCells);

			capturedCells = GridUtilities.getCapturedCells(grid);
		}

		return updates;
	}
}
