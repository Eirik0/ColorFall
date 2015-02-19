package gameentity.update;

import gamestate.colorfall.GameSizer;

import java.awt.Graphics;
import java.util.List;

import util.DrawingUtilities;
import util.GameConstants;

public class DroppingCells implements UpdateEntity {
	private static final long DURATION = GameConstants.ONE_SECOND / 5;

	private double internalTimer = 0;

	private final List<DroppingCell> drops;
	private final int[][] grid;

	public DroppingCells(List<DroppingCell> drops, int[][] grid) {
		this.drops = drops;
		this.grid = grid;
	}

	public List<DroppingCell> getDrops() {
		return drops;
	}

	@Override
	public int[][] getGrid() {
		return grid;
	}

	@Override
	public void update(long dt) {
		internalTimer += dt;
	}

	@Override
	public void drawOn(Graphics g) {
		double percentComplete = Math.min(1, internalTimer / DURATION);
		for (DroppingCell drop : drops) {
			double dy = percentComplete * GameSizer.getCellSize() * drop.dy + GameSizer.getOffsetY();
			DrawingUtilities.drawCell(g, drop.x, drop.y, drop.color, GameSizer.getOffsetX(), dy);
		}
	}

	@Override
	public boolean updateFinished() {
		return internalTimer >= DURATION;
	}

	public static class DroppingCell {
		public final int x;
		public final int y;
		public final int dy;
		public final int color;

		public DroppingCell(int x, int y, int dy, int color) {
			this.x = x;
			this.y = y;
			this.dy = dy;
			this.color = color;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			return prime * (prime * (prime + x) + y) + dy;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof DroppingCell) {
				DroppingCell other = (DroppingCell) obj;
				return x == other.x && y == other.y && dy == other.dy;
			}
			return false;
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + "), " + dy;
		}
	}
}
