package cf.gamestate.colorfall;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import cf.gameentity.update.CapturedCell;
import cf.gameentity.update.DroppingCell;

public class GameGridTest {
    @Test
    public void testDropOneColumn() {
        GameGrid grid = new GameGrid();
        grid.placeColumn(new FallingColumn(1, 2, 3, 0, 0, 1));
        // 3
        // 2
        // 1
        assertEquals(3, grid.get(0, GameGrid.HEIGHT - 3));
        assertEquals(2, grid.get(0, GameGrid.HEIGHT - 2));
        assertEquals(1, grid.get(0, GameGrid.HEIGHT - 1));
    }

    @Test
    public void testDropTwoColumn() {
        GameGrid grid = new GameGrid();
        grid.placeColumn(new FallingColumn(1, 2, 3, 0, 0, 1));
        grid.placeColumn(new FallingColumn(4, 5, 6, 0, 0, 1));
        // 6
        // 5
        // 4
        // 3
        // 2
        // 1
        assertEquals(6, grid.get(0, GameGrid.HEIGHT - 6));
        assertEquals(5, grid.get(0, GameGrid.HEIGHT - 5));
        assertEquals(4, grid.get(0, GameGrid.HEIGHT - 4));
        assertEquals(3, grid.get(0, GameGrid.HEIGHT - 3));
        assertEquals(2, grid.get(0, GameGrid.HEIGHT - 2));
        assertEquals(1, grid.get(0, GameGrid.HEIGHT - 1));
    }

    @Test
    public void testDropThreeOfTheSame() {
        GameGrid grid = new GameGrid();
        List<CapturedCell> captures = grid.placeColumn(new FallingColumn(1, 1, 1, 0, 0, 1));

        assertEquals(3, captures.size());
        assertEquals(0, grid.getDrops().size());

        assertTrue(captures.contains(new CapturedCell(0, GameGrid.HEIGHT - 1, 0)));
        assertTrue(captures.contains(new CapturedCell(0, GameGrid.HEIGHT - 2, 0)));
        assertTrue(captures.contains(new CapturedCell(0, GameGrid.HEIGHT - 3, 0)));

        assertEquals(0, grid.get(0, GameGrid.HEIGHT - 3));
        assertEquals(0, grid.get(0, GameGrid.HEIGHT - 2));
        assertEquals(0, grid.get(0, GameGrid.HEIGHT - 1));
    }

    @Test
    public void testClearHorizontal() {
        GameGrid grid = new GameGrid();
        grid.placeColumn(new FallingColumn(1, 2, 3, 0, 0, 1));
        grid.placeColumn(new FallingColumn(1, 4, 5, 1, 0, 1));
        List<CapturedCell> captures = grid.placeColumn(new FallingColumn(1, 6, 7, 2, 0, 1));
        // 3 5 7
        // 2 4 6
        // 1 1 1
        assertEquals(3, captures.size());
        List<DroppingCell> drops = grid.getDrops();
        assertEquals(6, drops.size());

        assertTrue(captures.contains(new CapturedCell(0, GameGrid.HEIGHT - 1, 0)));
        assertTrue(captures.contains(new CapturedCell(1, GameGrid.HEIGHT - 1, 0)));
        assertTrue(captures.contains(new CapturedCell(2, GameGrid.HEIGHT - 1, 0)));
        assertTrue(drops.contains(new DroppingCell(0, GameGrid.HEIGHT - 2, 1, 0)));
        assertTrue(drops.contains(new DroppingCell(1, GameGrid.HEIGHT - 2, 1, 0)));
        assertTrue(drops.contains(new DroppingCell(2, GameGrid.HEIGHT - 2, 1, 0)));
        assertTrue(drops.contains(new DroppingCell(0, GameGrid.HEIGHT - 3, 1, 0)));
        assertTrue(drops.contains(new DroppingCell(1, GameGrid.HEIGHT - 3, 1, 0)));
        assertTrue(drops.contains(new DroppingCell(2, GameGrid.HEIGHT - 3, 1, 0)));

        grid.placeDrops(drops);
        // 0 0 0
        // 3 5 7
        // 2 4 6
        assertEquals(2, grid.get(0, GameGrid.HEIGHT - 1));
        assertEquals(4, grid.get(1, GameGrid.HEIGHT - 1));
        assertEquals(6, grid.get(2, GameGrid.HEIGHT - 1));
        assertEquals(3, grid.get(0, GameGrid.HEIGHT - 2));
        assertEquals(5, grid.get(1, GameGrid.HEIGHT - 2));
        assertEquals(7, grid.get(2, GameGrid.HEIGHT - 2));
        assertEquals(0, grid.get(0, GameGrid.HEIGHT - 3));
        assertEquals(0, grid.get(1, GameGrid.HEIGHT - 3));
        assertEquals(0, grid.get(2, GameGrid.HEIGHT - 3));
    }

    @Test
    public void testClearDiagonalRight() {
        GameGrid grid = new GameGrid();
        grid.placeColumn(new FallingColumn(2, 3, 1, 0, 0, 1));
        grid.placeColumn(new FallingColumn(4, 1, 5, 1, 0, 1));
        List<CapturedCell> captures = grid.placeColumn(new FallingColumn(1, 6, 7, 2, 0, 1));
        // 1 5 7
        // 3 1 6
        // 2 4 1
        assertEquals(3, captures.size());
        List<DroppingCell> drops = grid.getDrops();
        assertEquals(3, drops.size());
        grid.placeDrops(drops);
        // 0 0 0
        // 3 5 7
        // 2 4 6
        assertEquals(2, grid.get(0, GameGrid.HEIGHT - 1));
        assertEquals(4, grid.get(1, GameGrid.HEIGHT - 1));
        assertEquals(6, grid.get(2, GameGrid.HEIGHT - 1));
        assertEquals(3, grid.get(0, GameGrid.HEIGHT - 2));
        assertEquals(5, grid.get(1, GameGrid.HEIGHT - 2));
        assertEquals(7, grid.get(2, GameGrid.HEIGHT - 2));
        assertEquals(0, grid.get(0, GameGrid.HEIGHT - 3));
        assertEquals(0, grid.get(1, GameGrid.HEIGHT - 3));
        assertEquals(0, grid.get(2, GameGrid.HEIGHT - 3));
    }

    @Test
    public void testClearDiagonalLeft() {
        GameGrid grid = new GameGrid();
        grid.placeColumn(new FallingColumn(1, 2, 3, 0, 0, 1));
        grid.placeColumn(new FallingColumn(4, 1, 5, 1, 0, 1));
        List<CapturedCell> captures = grid.placeColumn(new FallingColumn(6, 7, 1, 2, 0, 1));
        // 3 5 1
        // 2 1 7
        // 1 4 6
        assertEquals(3, captures.size());
        List<DroppingCell> drops = grid.getDrops();
        assertEquals(3, drops.size());
        grid.placeDrops(drops);
        // 0 0 0
        // 3 5 7
        // 2 4 6
        assertEquals(2, grid.get(0, GameGrid.HEIGHT - 1));
        assertEquals(4, grid.get(1, GameGrid.HEIGHT - 1));
        assertEquals(6, grid.get(2, GameGrid.HEIGHT - 1));
        assertEquals(3, grid.get(0, GameGrid.HEIGHT - 2));
        assertEquals(5, grid.get(1, GameGrid.HEIGHT - 2));
        assertEquals(7, grid.get(2, GameGrid.HEIGHT - 2));
        assertEquals(0, grid.get(0, GameGrid.HEIGHT - 3));
        assertEquals(0, grid.get(1, GameGrid.HEIGHT - 3));
        assertEquals(0, grid.get(2, GameGrid.HEIGHT - 3));
    }

    @Test
    public void testClearTwoInOneColumn() {
        GameGrid grid = new GameGrid();
        grid.placeColumn(new FallingColumn(1, 2, 3, 0, 0, 1));
        grid.placeColumn(new FallingColumn(4, 1, 5, 0, 0, 1));
        grid.placeColumn(new FallingColumn(6, 1, 7, 1, 0, 1));
        grid.placeColumn(new FallingColumn(1, 8, 9, 1, 0, 1));
        List<CapturedCell> captures = grid.placeColumn(new FallingColumn(9, 9, 1, 2, 0, 1));
        // 5 9
        // 1 8
        // 4 1
        // 3 7 1
        // 2 1 9
        // 1 6 9
        assertEquals(5, captures.size());
        List<DroppingCell> drops = grid.getDrops();
        assertEquals(7, drops.size());
        grid.placeDrops(drops);
        // 5 9 0
        // 4 8 0
        // 3 7 9
        // 2 6 9
        assertEquals(2, grid.get(0, GameGrid.HEIGHT - 1));
        assertEquals(6, grid.get(1, GameGrid.HEIGHT - 1));
        assertEquals(9, grid.get(2, GameGrid.HEIGHT - 1));
        assertEquals(3, grid.get(0, GameGrid.HEIGHT - 2));
        assertEquals(7, grid.get(1, GameGrid.HEIGHT - 2));
        assertEquals(9, grid.get(2, GameGrid.HEIGHT - 2));
        assertEquals(4, grid.get(0, GameGrid.HEIGHT - 3));
        assertEquals(8, grid.get(1, GameGrid.HEIGHT - 3));
        assertEquals(0, grid.get(2, GameGrid.HEIGHT - 3));
        assertEquals(5, grid.get(0, GameGrid.HEIGHT - 4));
        assertEquals(9, grid.get(1, GameGrid.HEIGHT - 4));
        assertEquals(0, grid.get(2, GameGrid.HEIGHT - 4));
    }

    @Test
    public void testComboLevelTwo() {
        GameGrid grid = new GameGrid();
        grid.placeColumn(new FallingColumn(2, 5, 1, 2, 0, 1));
        grid.placeColumn(new FallingColumn(2, 1, 4, 1, 0, 1));
        List<CapturedCell> captures1 = grid.placeColumn(new FallingColumn(1, 2, 3, 0, 0, 1));
        List<DroppingCell> drops1 = grid.getDrops();
        // 3 4 1
        // 2 1 5
        // 1 2 2
        assertTrue(captures1.contains(new CapturedCell(0, GameGrid.HEIGHT - 1, 0)));
        assertTrue(captures1.contains(new CapturedCell(1, GameGrid.HEIGHT - 2, 0)));
        assertTrue(captures1.contains(new CapturedCell(2, GameGrid.HEIGHT - 3, 0)));
        assertTrue(drops1.contains(new DroppingCell(0, GameGrid.HEIGHT - 2, 1, 0)));
        assertTrue(drops1.contains(new DroppingCell(0, GameGrid.HEIGHT - 3, 1, 0)));
        assertTrue(drops1.contains(new DroppingCell(1, GameGrid.HEIGHT - 3, 1, 0)));
        List<CapturedCell> captures2 = grid.placeDrops(drops1);
        List<DroppingCell> drops2 = grid.getDrops();
        // 3 4 5
        // 2 2 2
        assertTrue(captures2.contains(new CapturedCell(0, GameGrid.HEIGHT - 1, 0)));
        assertTrue(captures2.contains(new CapturedCell(1, GameGrid.HEIGHT - 1, 0)));
        assertTrue(captures2.contains(new CapturedCell(2, GameGrid.HEIGHT - 1, 0)));
        assertTrue(drops2.contains(new DroppingCell(0, GameGrid.HEIGHT - 2, 1, 0)));
        assertTrue(drops2.contains(new DroppingCell(1, GameGrid.HEIGHT - 2, 1, 0)));
        assertTrue(drops2.contains(new DroppingCell(2, GameGrid.HEIGHT - 2, 1, 0)));
        grid.placeDrops(drops2);
        // 3 4 5
        assertEquals(3, grid.get(0, GameGrid.HEIGHT - 1));
        assertEquals(4, grid.get(1, GameGrid.HEIGHT - 1));
        assertEquals(5, grid.get(2, GameGrid.HEIGHT - 1));
    }
}
