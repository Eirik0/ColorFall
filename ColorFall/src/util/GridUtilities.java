package util;

import game.GameGrid;
import gameentity.update.CapturedCells;
import gameentity.update.CapturedCells.CapturedCell;
import gameentity.update.DroppingCells.DroppingCell;

import java.util.ArrayList;
import java.util.List;

public class GridUtilities {
	public static int[][] newGridWithoutDrops(int[][] grid, List<DroppingCell> drops) {
		int[][] newGrid = copyGrid(grid);
		for (DroppingCell drop : drops) {
			newGrid[drop.x][drop.y] = GameGrid.UNPLAYED;
		}
		return newGrid;
	}

	public static int[][] copyGrid(int[][] grid) {
		int[][] newGrid = new int[GameGrid.WIDTH][GameGrid.HEIGHT];
		for (int i = 0; i < GameGrid.WIDTH; ++i) {
			System.arraycopy(grid[i], 0, newGrid[i], 0, GameGrid.HEIGHT);
		}
		return newGrid;
	}

	public static CapturedCells getCapturedCells(int[][] grid) {
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
		return captures.isEmpty() ? null : new CapturedCells(captures, updatedGrid);
	}

	public static boolean isSafe(int[][] grid, int x, int y) {
		int color = grid[x][y];
		//a  b  c
		// 1 2 3
		//d4   5e
		// 6 7 8
		//f  g  h
		if (x > 0 && y > 0 && grid[x - 1][y - 1] == color) { // 1
			if (x > 1 && y > 1 && grid[x - 2][y - 2] == color) {
				return false; // 1 & a
			}
			if (x < GameGrid.WIDTH - 1 && y < GameGrid.HEIGHT - 1 && grid[x + 1][y + 1] == color) {
				return false; // 1 & 8
			}
		}
		if (y > 0 && grid[x][y - 1] == color) { // 2
			if (y > 1 && grid[x][y - 2] == color) {
				return false; // 2 & b
			}
			if (y < GameGrid.HEIGHT - 1 && grid[x][y + 1] == color) {
				return false; // 2 & 7
			}
		}
		if (x < GameGrid.WIDTH - 1 && y > 0 && grid[x + 1][y - 1] == color) { // 3
			if (x < GameGrid.WIDTH - 2 && y > 1 && grid[x + 2][y - 2] == color) {
				return false; // 3 & c
			}
			if (x > 0 && y < GameGrid.HEIGHT - 1 && grid[x - 1][y + 1] == color) {
				return false; // 3 & 6
			}
		}
		if (x > 0 && grid[x - 1][y] == color) { // 4
			if (x > 1 && grid[x - 2][y] == color) {
				return false; // 4 & d
			}
			if (x < GameGrid.WIDTH - 1 && grid[x + 1][y] == color) { // 5
				return false; // 4 & 5
			}
		}
		if (x < GameGrid.WIDTH - 1 && grid[x + 1][y] == color) { // 5
			if (x < GameGrid.WIDTH - 2 && grid[x + 2][y] == color) {
				return false; // 5 & e
			}
		}
		if (x > 0 && y < GameGrid.HEIGHT - 1 && grid[x - 1][y + 1] == color) { // 6
			if (x > 1 && y < GameGrid.HEIGHT - 2 && grid[x - 2][y + 2] == color) {
				return false; // 6 & f
			}
		}
		if (y < GameGrid.HEIGHT - 1 && grid[x][y + 1] == color) { // 7
			if (y < GameGrid.HEIGHT - 2 && grid[x][y + 2] == color) {
				return false; // 7 & g
			}
		}
		if (x < GameGrid.WIDTH - 1 && y < GameGrid.HEIGHT - 1 && grid[x + 1][y + 1] == color) { // 8
			if (x < GameGrid.WIDTH - 2 && y < GameGrid.HEIGHT - 2 && grid[x + 2][y + 2] == color) {
				return false; // 8 & h
			}
		}
		return true;
	}
}
