package game;

public class GameScore {
	private static final int CAPTURES_PER_LEVEL = 100;

	private int score;
	private int level;
	private int captures;

	public GameScore(int score, int level, int captures) {
		this.score = score;
		this.level = level;
		this.captures = captures;
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

	public void addToScore(int numCaptures, int comboDepth) {
		int baseScore = 5 * level;

		score += numCaptures * (baseScore + comboDepth * baseScore);
		captures += numCaptures;

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
