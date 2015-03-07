package gameentity.update;

import gamestate.colorfall.GameSizer;

import java.awt.Graphics;
import java.util.List;

import util.DrawingUtilities;
import util.GameConstants;

public class CapturedCells extends GridUpdateEntity {
	private static final long DURATION = GameConstants.ONE_SECOND / 5;

	private final List<CapturedCell> captures;
	private final int[][] grid;

	public CapturedCells(List<CapturedCell> captures, int[][] grid) {
		super(DURATION);
		this.captures = captures;
		this.grid = grid;
	}

	public List<CapturedCell> getCaptures() {
		return captures;
	}

	@Override
	public int[][] getGrid() {
		return grid;
	}

	@Override
	public void drawOn(Graphics g) {
		double percentComplete = getPercentComplete();
		double d = GameSizer.getCellSize() * percentComplete;
		for (CapturedCell capture : captures) {
			int x0 = GameSizer.getCellX(capture.x, GameSizer.getOffsetX() + d / 2);
			int y0 = GameSizer.getCellY(capture.y, GameSizer.getOffsetY() + d / 2);
			int width = GameSizer.getCellWidth(capture.x, -d);
			int height = GameSizer.getCellHeight(capture.y, -d);

			g.setColor(DrawingUtilities.getColor(capture.color));
			g.fillOval(x0, y0, width, height);
		}
	}

	public static class CapturedCell {
		public final int x;
		public final int y;
		public final int color;

		public CapturedCell(int x, int y, int color) {
			this.x = x;
			this.y = y;
			this.color = color;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			return prime * (prime + x) + y;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof CapturedCell) {
				CapturedCell other = (CapturedCell) obj;
				return x == other.x && y == other.y;
			}
			return false;
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}
}
