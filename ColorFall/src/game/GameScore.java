package game;

public class GameScore {
	private static final int CAPTURES_PER_LEVEL = 100;

	private int score;
	private int level;
	private int captures;

	public GameScore() {
		score = 0;
		level = 1;
		captures = 0;
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

	@Override
	public String toString() {
		return "Score: " + score + " Level: " + level + " Captures: " + captures;
	}

	public boolean addScore(int numCaptures, int comboDepth) {
		int scorePerCapture = level + comboDepth + 1;
		score += numCaptures * scorePerCapture;
		captures += numCaptures;

		boolean levelUp = false;
		if (captures > level * CAPTURES_PER_LEVEL) {
			++level;
			levelUp = true;
		}
		return levelUp;
	}
}
