package game.score;

import gameentity.GameEntity;

import java.awt.Graphics;

import util.DrawingUtilities;
import util.GameConstants;

public class UpdatingScore implements GameEntity {
	public static final long DURATION = GameConstants.ONE_SECOND / 10;

	private double internalTimer = 0;

	private final int x;
	private final int y;

	private final String prefix;
	private final String pendingSuffix;
	private final String finishedSuffix;

	public UpdatingScore(String name, int oldScore, int newScore, int x, int y) {
		this.x = x;
		this.y = y;

		int[] oldScoreArray = toArray(oldScore);
		int[] newScoreArray = toArray(newScore);

		if (newScoreArray.length != oldScoreArray.length) {
			prefix = name + ": ";
			pendingSuffix = Integer.toString(newScore);
			finishedSuffix = Integer.toString(oldScore);
		} else {
			int firstMismatch = 0;

			for (int i = 0; i < newScoreArray.length; ++i) {
				if (newScoreArray[i] != oldScoreArray[i]) {
					break;
				}
				++firstMismatch;
			}

			StringBuilder prefixSb = new StringBuilder(name).append(": ");
			int i = 0;
			for (; i < firstMismatch; ++i) {
				prefixSb.append(newScoreArray[i]);
			}
			prefix = prefixSb.toString();

			StringBuilder pendingSuffixSb = new StringBuilder();
			StringBuilder finishedSuffixSb = new StringBuilder();
			for (; i < newScoreArray.length; ++i) {
				pendingSuffixSb.append(newScoreArray[i]);
				finishedSuffixSb.append(oldScoreArray[i]);
			}
			pendingSuffix = pendingSuffixSb.toString();
			finishedSuffix = finishedSuffixSb.toString();
		}
	}

	public String getPrefix() {
		return prefix;
	}

	public String getPendingSuffix() {
		return pendingSuffix;
	}

	public String getFinishedSuffix() {
		return finishedSuffix;
	}

	@Override
	public void update(long dt) {
		internalTimer += dt;
	}

	@Override
	public void drawOn(Graphics g) {
		double percentComplete = Math.min(1, internalTimer / DURATION);
		g.drawString(prefix, x, y);
		int prefixWidth = g.getFontMetrics().stringWidth(prefix);
		int prefixHeight = g.getFontMetrics().getHeight();
		g.drawString(pendingSuffix, x + prefixWidth, DrawingUtilities.round(y + prefixHeight - percentComplete * prefixHeight));
		g.drawString(finishedSuffix, x + prefixWidth, DrawingUtilities.round(y - percentComplete * prefixHeight));
	}

	public boolean updateFinished() {
		return internalTimer >= DURATION;
	}

	private static int[] toArray(int score) {
		int tenthPower = 0;
		int n = score;
		while (n > 0) {
			n /= 10;
			++tenthPower;
		}
		int[] num = new int[tenthPower];
		for (int i = 0; i < tenthPower; ++i) {
			num[tenthPower - i - 1] = score % 10;
			score /= 10;
		}
		return num;
	}
}
