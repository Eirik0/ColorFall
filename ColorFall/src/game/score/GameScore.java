package game.score;

import gameentity.GameEntity;

import java.awt.Color;
import java.awt.Graphics;

import util.DrawingUtilities;
import util.GameConstants;

public class GameScore implements GameEntity {
	private static final int LEFT_PADDING = 20;
	private static final int SCORE_Y = 100;
	private static final int LEVEL_Y = 200;
	private static final int CAPTURES_Y = 300;
	private static final int TIME_Y = 400;

	private static final int CAPTURES_PER_LEVEL = 50;

	private int score;
	private int level;
	private int captures;
	private long time;

	private final ScoreAdder scoreAdder = new ScoreAdder("Score", LEFT_PADDING, SCORE_Y);
	private final ScoreAdder capturesAdder = new ScoreAdder("Captures", LEFT_PADDING, CAPTURES_Y);

	public GameScore(int score, int level, int captures) {
		this.score = score;
		this.level = level;
		this.captures = captures;
		time = 0;
	}

	@Override
	public void update(long dt) {
		scoreAdder.update(dt);
		capturesAdder.update(dt);
		time += Math.round((double) dt / GameConstants.ONE_MILLISECOND);
	}

	@Override
	public void drawOn(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(GameConstants.GAME_FONT);
		scoreAdder.drawOn(g);
		g.drawString("Level: " + level, LEFT_PADDING, LEVEL_Y);
		capturesAdder.drawOn(g);
		g.drawString("Time: " + DrawingUtilities.formatTime(time), LEFT_PADDING, TIME_Y);
	}

	public int getScore() {
		return score;
	}

	public int getLevel() {
		return level;
	}

	public int getCaptures() {
		return captures;
	}

	public long getTime() {
		return time;
	}

	public void addToScore(int numCaptures, int comboDepth) {
		int baseScore = 5 * level;
		int scorePerCapture = baseScore + comboDepth * baseScore;

		score += numCaptures * scorePerCapture;
		captures += numCaptures;

		scoreAdder.add(scorePerCapture, numCaptures);
		capturesAdder.add(1, numCaptures);

		if (captures > level * CAPTURES_PER_LEVEL) {
			++level;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime * (prime * (prime + captures) + level) + score;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameScore) {
			GameScore other = (GameScore) obj;
			return score == other.score && level == other.level && captures == other.captures;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Score: " + score + " Level: " + level + " Captures: " + captures;
	}
}
