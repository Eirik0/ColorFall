package gameentity;

import java.awt.Color;
import java.awt.Graphics;

import util.GameConstants;

public class FpsTracker implements GameEntity {
	private int frames;
	private int currentFps;
	private double timeSleeping;
	private double currentTimeSleeping;
	private double totalTime;

	public FpsTracker() {
		frames = 0;
		currentFps = 0;
		currentTimeSleeping = 0;
		timeSleeping = 0;
		totalTime = 0;
	}

	@Override
	public void update(long dt) {
		++frames;
		totalTime += dt;
		if (totalTime > GameConstants.ONE_SECOND) {
			currentFps = frames;
			currentTimeSleeping = timeSleeping;
			frames = 0;
			timeSleeping = 0;
			totalTime = 0;
		}
	}

	public void addTimeSleeping(double dt) {
		timeSleeping += dt;
	}

	@Override
	public void drawOn(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(GameConstants.GAME_FONT);
		String percent = String.format("%,.2f%%", (1 - (currentTimeSleeping / GameConstants.ONE_SECOND)) * 100);
		g.drawString(Integer.toString(currentFps) + " " + percent, 25, 25);
	}
}
