package cf.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cf.util.GameConstants;

public class FallingColumnTest {
    @Test
    public void testRotateOnce() {
        FallingColumn column = new FallingColumn(null, 1, 2, 3);

        column.rotate();

        assertEquals(3, column.getColor1());
        assertEquals(1, column.getColor2());
        assertEquals(2, column.getColor3());
    }

    @Test
    public void testRotateTwice() {
        FallingColumn column = new FallingColumn(null, 1, 2, 3);

        column.rotate();
        column.rotate();

        assertEquals(2, column.getColor1());
        assertEquals(3, column.getColor2());
        assertEquals(1, column.getColor3());
    }

    @Test
    public void testRotateThrice() {
        FallingColumn column = new FallingColumn(null, 1, 2, 3);

        column.rotate();
        column.rotate();
        column.rotate();

        assertEquals(1, column.getColor1());
        assertEquals(2, column.getColor2());
        assertEquals(3, column.getColor3());
    }

    @Test
    public void testUpdate() {
        GameGrid gameGrid = new GameGrid();
        FallingColumn column = new FallingColumn(gameGrid, 1, 2, 3);
        column.update(50);
        assertEquals(0, column.getY());
        for (int i = 1; i < GameGrid.HEIGHT; ++i) {
            column.update(GameConstants.ONE_SECOND);
            assertEquals(i, i, column.getY());
            assertFalse(column.wasPlaced());
        }
        column.update(GameConstants.ONE_SECOND);
        assertEquals(GameGrid.HEIGHT - 1, column.getY());
        assertTrue(column.wasPlaced());
    }
}
