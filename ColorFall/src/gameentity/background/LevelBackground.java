package gameentity.background;

import gameentity.GameEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import main.GameSettings;
import util.DrawingUtilities;

public class LevelBackground implements GameEntity {
	private int level;

	private Polygon polygon;

	public LevelBackground(int level) {
		this.level = level;
		polygon = new Polygon(75, 75, 0.5, 0.5, level);
	}

	public void maybeLevelUp(int newLevel) {
		if (newLevel != level) {
			level = newLevel;
			polygon = new Polygon(polygon.centerX, polygon.centerY, polygon.velX, polygon.velY, level);
		}
	}

	@Override
	public void update(long dt) {
		polygon.update(dt);
	}

	@Override
	public void drawOn(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GameSettings.componentWidth, GameSettings.componentHeight);

		polygon.drawOn(g);
	}

	private static class Polygon implements GameEntity {
		private static final int RADUIS = 50;
		double centerX;
		double centerY;

		double velX;
		double velY;

		private final double[] xs;
		private final double[] ys;

		private final int pointsToSkip;

		public Polygon(double x0, double y0, double velX, double velY, int numSides) {
			centerX = x0;
			centerY = y0;

			this.velX = velX;
			this.velY = velY;

			xs = new double[numSides];
			ys = new double[numSides];

			setPoints();

			pointsToSkip = getPointsToSkip();
		}

		private void setPoints() {
			double dTheta = 2 * Math.PI / xs.length;

			double theta = 0;
			for (int i = 0; i < xs.length; i++) {
				xs[i] = RADUIS * Math.cos(theta);
				ys[i] = RADUIS * Math.sin(theta);
				theta += dTheta;
			}
		}

		private int getPointsToSkip() {
			if (xs.length == 1) {
				return 1;
			}

			Random random = new Random();

			int pointsToSkip = 0;
			do {
				pointsToSkip = random.nextInt(xs.length - 1) + 1;
			} while (pointsToSkip != 1 && pointsToSkip % xs.length == 0);

			return pointsToSkip;
		}

		@Override
		public void update(long dt) {
			centerX += velX;
			if (centerX > GameSettings.componentWidth - RADUIS) {
				centerX = GameSettings.componentWidth - RADUIS;
				velX *= -1;
			} else if (centerX < RADUIS) {
				centerX = RADUIS;
				velX *= -1;
			}
			centerY += velY;
			if (centerY > GameSettings.componentHeight - RADUIS) {
				centerY = GameSettings.componentHeight - RADUIS;
				velY *= -1;
			} else if (centerY < RADUIS) {
				centerY = RADUIS;
				velY *= -1;
			}
		}

		@Override
		public void drawOn(Graphics g) {
			int index = 0;

			double x0 = xs[0];
			double y0 = ys[0];

			g.setColor(Color.RED);
			do {
				index = (index + pointsToSkip) % xs.length;

				double x1 = xs[index];
				double y1 = ys[index];

				g.drawLine(DrawingUtilities.round(centerX + x0), DrawingUtilities.round(centerY - y0),
						DrawingUtilities.round(centerX + x1), DrawingUtilities.round(centerY - y1));

				x0 = x1;
				y0 = y1;
			} while (index != 0);
		}
	}
}
