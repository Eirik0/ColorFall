package cf.gamestate.colorfall;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FallingColumnTest {
    @Test
    public void testRotateOnce() {
        FallingColumn column = new FallingColumn(1, 2, 3, 0, 0, 1);

        column.rotate();

        assertEquals(3, column.getColor1());
        assertEquals(1, column.getColor2());
        assertEquals(2, column.getColor3());
    }

    @Test
    public void testRotateTwice() {
        FallingColumn column = new FallingColumn(1, 2, 3, 0, 0, 1);

        column.rotate();
        column.rotate();

        assertEquals(2, column.getColor1());
        assertEquals(3, column.getColor2());
        assertEquals(1, column.getColor3());
    }

    @Test
    public void testRotateThrice() {
        FallingColumn column = new FallingColumn(1, 2, 3, 0, 0, 1);

        column.rotate();
        column.rotate();
        column.rotate();

        assertEquals(1, column.getColor1());
        assertEquals(2, column.getColor2());
        assertEquals(3, column.getColor3());
    }
}
