package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import game.score.GameScore;
import gameentity.update.CapturedCells;
import gameentity.update.CapturedCells.CapturedCell;
import gameentity.update.DroppingCells;
import gameentity.update.DroppingCells.DroppingCell;
import gameentity.update.GridUpdateEntity;

import java.util.List;

import org.junit.Test;

public class GameGridTest {
	@Test
	public void testDropOneColumn() {
		GameGrid grid = new GameGrid();
		grid.placeColumn(new FallingColumn(grid, 1, 2, 3, 0, 0), new GameScore(0, 1, 0), true);
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
		grid.placeColumn(new FallingColumn(grid, 1, 2, 3, 0, 0), new GameScore(0, 1, 0), true);
		grid.placeColumn(new FallingColumn(grid, 4, 5, 6, 0, 0), new GameScore(0, 1, 0), true);
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
		GameScore score = new GameScore(0, 1, 0);

		List<GridUpdateEntity> updates = grid.placeColumn(new FallingColumn(grid, 1, 1, 1, 0, 0), score, true);

		assertEquals(1, updates.size());
		assertTrue(updates.get(0) instanceof CapturedCells);
		assertEquals(new GameScore(30, 1, 3), score);

		List<CapturedCell> captures = ((CapturedCells) updates.get(0)).getCaptures();
		// 1
		// 1
		// 1
		assertTrue(captures.contains(new CapturedCell(0, GameGrid.HEIGHT - 1, 0)));
		assertTrue(captures.contains(new CapturedCell(0, GameGrid.HEIGHT - 2, 0)));
		assertTrue(captures.contains(new CapturedCell(0, GameGrid.HEIGHT - 3, 0)));

		//
		//
		//
		assertEquals(0, grid.get(0, GameGrid.HEIGHT - 3));
		assertEquals(0, grid.get(0, GameGrid.HEIGHT - 2));
		assertEquals(0, grid.get(0, GameGrid.HEIGHT - 1));
	}

	@Test
	public void testClearHorizontal() {
		GameGrid grid = new GameGrid();
		grid.placeColumn(new FallingColumn(grid, 1, 2, 3, 0, 0), new GameScore(0, 1, 0), true);
		grid.placeColumn(new FallingColumn(grid, 1, 4, 5, 1, 0), new GameScore(0, 1, 0), true);

		List<GridUpdateEntity> updates = grid.placeColumn(new FallingColumn(grid, 1, 6, 7, 2, 0), new GameScore(0, 1, 0), true);

		assertTrue(updates.get(0) instanceof CapturedCells);
		assertTrue(updates.get(1) instanceof DroppingCells);

		List<CapturedCell> captures = ((CapturedCells) updates.get(0)).getCaptures();
		List<DroppingCell> drops = ((DroppingCells) updates.get(1)).getDrops();
		// 3 5 7
		// 2 4 6
		// 1 1 1
		assertTrue(captures.contains(new CapturedCell(0, GameGrid.HEIGHT - 1, 0)));
		assertTrue(captures.contains(new CapturedCell(1, GameGrid.HEIGHT - 1, 0)));
		assertTrue(captures.contains(new CapturedCell(2, GameGrid.HEIGHT - 1, 0)));
		assertTrue(drops.contains(new DroppingCell(0, GameGrid.HEIGHT - 2, 1, 0)));
		assertTrue(drops.contains(new DroppingCell(1, GameGrid.HEIGHT - 2, 1, 0)));
		assertTrue(drops.contains(new DroppingCell(2, GameGrid.HEIGHT - 2, 1, 0)));
		assertTrue(drops.contains(new DroppingCell(0, GameGrid.HEIGHT - 3, 1, 0)));
		assertTrue(drops.contains(new DroppingCell(1, GameGrid.HEIGHT - 3, 1, 0)));
		assertTrue(drops.contains(new DroppingCell(2, GameGrid.HEIGHT - 3, 1, 0)));
		//
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
		grid.placeColumn(new FallingColumn(grid, 2, 3, 1, 0, 0), new GameScore(0, 1, 0), true);
		grid.placeColumn(new FallingColumn(grid, 4, 1, 5, 1, 0), new GameScore(0, 1, 0), true);
		grid.placeColumn(new FallingColumn(grid, 1, 6, 7, 2, 0), new GameScore(0, 1, 0), true);
		// 1 5 7
		// 3 1 6
		// 2 4 1
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
		grid.placeColumn(new FallingColumn(grid, 1, 2, 3, 0, 0), new GameScore(0, 1, 0), true);
		grid.placeColumn(new FallingColumn(grid, 4, 1, 5, 1, 0), new GameScore(0, 1, 0), true);
		grid.placeColumn(new FallingColumn(grid, 6, 7, 1, 2, 0), new GameScore(0, 1, 0), true);
		// 3 5 1
		// 2 1 7
		// 1 4 6
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
		grid.placeColumn(new FallingColumn(grid, 1, 2, 3, 0, 0), new GameScore(0, 1, 0), true);
		grid.placeColumn(new FallingColumn(grid, 4, 1, 5, 0, 0), new GameScore(0, 1, 0), true);
		grid.placeColumn(new FallingColumn(grid, 6, 1, 7, 1, 0), new GameScore(0, 1, 0), true);
		grid.placeColumn(new FallingColumn(grid, 1, 8, 9, 1, 0), new GameScore(0, 1, 0), true);
		grid.placeColumn(new FallingColumn(grid, 9, 9, 1, 2, 0), new GameScore(0, 1, 0), true);
		// 5 9
		// 1 8
		// 4 1
		// 3 7 1
		// 2 1 9
		// 1 6 9
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
		GameScore score = new GameScore(0, 2, 0);
		grid.placeColumn(new FallingColumn(grid, 2, 5, 1, 2, 0), score, true);
		grid.placeColumn(new FallingColumn(grid, 2, 1, 4, 1, 0), score, true);
		List<GridUpdateEntity> updates = grid.placeColumn(new FallingColumn(grid, 1, 2, 3, 0, 0), score, true);

		assertTrue(updates.get(0) instanceof CapturedCells);
		assertTrue(updates.get(1) instanceof DroppingCells);
		assertEquals(new GameScore(150, 2, 6), score);

		List<CapturedCell> captures1 = ((CapturedCells) updates.get(0)).getCaptures();
		List<DroppingCell> drops1 = ((DroppingCells) updates.get(1)).getDrops();
		// 3 4 1
		// 2 1 5
		// 1 2 2
		assertTrue(captures1.contains(new CapturedCell(0, GameGrid.HEIGHT - 1, 0)));
		assertTrue(captures1.contains(new CapturedCell(1, GameGrid.HEIGHT - 2, 0)));
		assertTrue(captures1.contains(new CapturedCell(2, GameGrid.HEIGHT - 3, 0)));
		assertTrue(drops1.contains(new DroppingCell(0, GameGrid.HEIGHT - 2, 1, 0)));
		assertTrue(drops1.contains(new DroppingCell(0, GameGrid.HEIGHT - 3, 1, 0)));
		assertTrue(drops1.contains(new DroppingCell(1, GameGrid.HEIGHT - 3, 1, 0)));

		assertTrue(updates.get(2) instanceof CapturedCells);
		assertTrue(updates.get(3) instanceof DroppingCells);

		List<CapturedCell> captures2 = ((CapturedCells) updates.get(2)).getCaptures();
		List<DroppingCell> drops2 = ((DroppingCells) updates.get(3)).getDrops();
		//
		// 3 4 5
		// 2 2 2
		assertTrue(captures2.contains(new CapturedCell(0, GameGrid.HEIGHT - 1, 0)));
		assertTrue(captures2.contains(new CapturedCell(1, GameGrid.HEIGHT - 1, 0)));
		assertTrue(captures2.contains(new CapturedCell(2, GameGrid.HEIGHT - 1, 0)));
		assertTrue(drops2.contains(new DroppingCell(0, GameGrid.HEIGHT - 2, 1, 0)));
		assertTrue(drops2.contains(new DroppingCell(1, GameGrid.HEIGHT - 2, 1, 0)));
		assertTrue(drops2.contains(new DroppingCell(2, GameGrid.HEIGHT - 2, 1, 0)));
		//
		//
		// 3 4 5
		assertEquals(3, grid.get(0, GameGrid.HEIGHT - 1));
		assertEquals(4, grid.get(1, GameGrid.HEIGHT - 1));
		assertEquals(5, grid.get(2, GameGrid.HEIGHT - 1));
	}
}
