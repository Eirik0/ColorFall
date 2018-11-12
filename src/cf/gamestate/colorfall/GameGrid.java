package cf.gamestate.colorfall;

import java.util.ArrayList;
import java.util.List;

import cf.gameentity.update.CapturedCell;
import cf.gameentity.update.DroppingCell;

public class GameGrid {
    public static final int UNPLAYED = 0;

    public static final int WIDTH = 9;
    public static final int HEIGHT = 19;

    private int[][] grid;

    public GameGrid() {
        grid = new int[WIDTH][HEIGHT];
    }

    public int get(int x, int y) {
        return grid[x][y];
    }

    public List<CapturedCell> placeColumn(FallingColumn column) {
        while (column.maybeMove(this, 0, 1)) {
        }

        grid[column.getX()][column.getY()] = column.getColor1();
        grid[column.getX()][column.getY() - 1] = column.getColor2();
        grid[column.getX()][column.getY() - 2] = column.getColor3();

        return getCaptures();
    }

    private List<CapturedCell> getCaptures() {
        List<CapturedCell> captures = new ArrayList<>();
        int[][] updatedGrid = new int[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; ++x) {
            for (int y = 0; y < HEIGHT; ++y) {
                int color = grid[x][y];
                if (color != UNPLAYED) {
                    if (isSafe(grid, x, y)) {
                        updatedGrid[x][y] = color;
                    } else {
                        updatedGrid[x][y] = UNPLAYED;
                        captures.add(new CapturedCell(x, y, color));
                    }
                }
            }
        }

        grid = updatedGrid;
        return captures;
    }

    public List<DroppingCell> getDrops() {
        List<DroppingCell> drops = new ArrayList<>();
        int[][] updatedGrid = new int[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; ++x) {
            for (int y = 0; y < HEIGHT; ++y) {
                int color = grid[x][y];
                if (color != UNPLAYED) {
                    int dy = 0;
                    for (int i = y; i < HEIGHT; ++i) {
                        if (grid[x][i] == UNPLAYED) {
                            ++dy;
                        }
                    }
                    if (dy > 0) {
                        updatedGrid[x][y] = UNPLAYED;
                        drops.add(new DroppingCell(x, y, dy, color));
                    } else {
                        updatedGrid[x][y] = color;
                    }
                }
            }
        }

        grid = updatedGrid;
        return drops;
    }

    public List<CapturedCell> placeDrops(List<DroppingCell> drops) {
        for (DroppingCell droppingCell : drops) {
            grid[droppingCell.x][droppingCell.y + droppingCell.dy] = droppingCell.color;
        }
        return getCaptures();
    }

    private static boolean isSafe(int[][] grid, int x, int y) {
        int color = grid[x][y];
        //a  b  c
        // 1 2 3
        //d4   5e
        // 6 7 8
        //f  g  h
        if (x > 0 && y > 0 && grid[x - 1][y - 1] == color) { // 1
            if (x > 1 && y > 1 && grid[x - 2][y - 2] == color) {
                return false; // 1 & a
            } else if (x < WIDTH - 1 && y < HEIGHT - 1 && grid[x + 1][y + 1] == color) {
                return false; // 1 & 8
            }
        }
        if (y > 0 && grid[x][y - 1] == color) { // 2
            if (y > 1 && grid[x][y - 2] == color) {
                return false; // 2 & b
            } else if (y < HEIGHT - 1 && grid[x][y + 1] == color) {
                return false; // 2 & 7
            }
        }
        if (x < WIDTH - 1 && y > 0 && grid[x + 1][y - 1] == color) { // 3
            if (x < WIDTH - 2 && y > 1 && grid[x + 2][y - 2] == color) {
                return false; // 3 & c
            } else if (x > 0 && y < HEIGHT - 1 && grid[x - 1][y + 1] == color) {
                return false; // 3 & 6
            }
        }
        if (x > 0 && grid[x - 1][y] == color) { // 4
            if (x > 1 && grid[x - 2][y] == color) {
                return false; // 4 & d
            } else if (x < WIDTH - 1 && grid[x + 1][y] == color) { // 5
                return false; // 4 & 5
            }
        }
        if (x < WIDTH - 1 && grid[x + 1][y] == color) { // 5
            if (x < WIDTH - 2 && grid[x + 2][y] == color) {
                return false; // 5 & e
            }
        }
        if (x > 0 && y < HEIGHT - 1 && grid[x - 1][y + 1] == color) { // 6
            if (x > 1 && y < HEIGHT - 2 && grid[x - 2][y + 2] == color) {
                return false; // 6 & f
            }
        }
        if (y < HEIGHT - 1 && grid[x][y + 1] == color) { // 7
            if (y < HEIGHT - 2 && grid[x][y + 2] == color) {
                return false; // 7 & g
            }
        }
        if (x < WIDTH - 1 && y < HEIGHT - 1 && grid[x + 1][y + 1] == color) { // 8
            if (x < WIDTH - 2 && y < HEIGHT - 2 && grid[x + 2][y + 2] == color) {
                return false; // 8 & h
            }
        }
        return true;
    }
}
