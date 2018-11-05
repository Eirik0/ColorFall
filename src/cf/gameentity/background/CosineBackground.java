package gameentity.background;

import gameentity.GameEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import main.GameSettings;
import util.DrawingUtilities;

public class CosineBackground implements GameEntity {
	private static final double dTheta = Math.PI / 1440;

	private final Color color1;
	private final Color color2;

	private static double n = 0;

	public CosineBackground() {
		Random random = new Random();
		color1 = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		color2 = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}

	@Override
	public void update(long dt) {
		n += dt / 50000000000.0;
	}

	@Override
	public void drawOn(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameSettings.componentWidth, GameSettings.componentHeight);

		int halfWidth = GameSettings.componentWidth / 2;
		int halfHeight = GameSettings.componentHeight / 2;

		double theta = 0;
		int i = 0;

		while (theta < 2 * Math.PI) {
			if (i % 5 == 0) {
				g.setColor(color1);
			} else {
				g.setColor(color2);
			}

			double r = halfHeight * Math.sin(n * theta);

			int x = DrawingUtilities.round(r * Math.cos(theta) + halfWidth);
			int y = DrawingUtilities.round(halfHeight - r * Math.sin(theta));

			g.drawLine(x, y, x, y);

			theta += dTheta;
			++i;
		}
	}
}
