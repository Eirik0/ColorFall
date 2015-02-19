package main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HighScores {
	public static final String EL_GRECO_1541 = "El Greco,1541,0,0,0";
	public static final String HOLBEIN_1497 = "Hans Holbein the Younger,1497,0,0,0";
	public static final String RAPHAEL_1483 = "Raffaello Sanzio da Urbino,1483,0,0,0";
	public static final String MICHELANGELO_1475 = "Michelangelo di Lodovico Buonarroti Simoni,1475,0,0,0";
	public static final String CRANACH_1472 = "Lucas Cranach the Elder,1472,0,0,0";
	public static final String LEONARDO_1452 = "Leonardo da Vinci,1452,0,0,0";
	public static final String GHIRLANDAIO_1449 = "Domenico Ghirlandaio,1449,0,0,0";
	public static final String EYCK_1395 = "Jan van Eyck,1395,0,0,0";
	public static final String DONATELLO_1386 = "Donato di Niccol� di Betto Bardi,1386,0,0,0";
	public static final String DUCCIO_1260 = "Duccio di Buoninsegna,1260,0,0,0";

	private static final List<HighScore> highScores = new ArrayList<>();

	public static void init() {
		highScores.add(new HighScore(EL_GRECO_1541));
		highScores.add(new HighScore(HOLBEIN_1497));
		highScores.add(new HighScore(RAPHAEL_1483));
		highScores.add(new HighScore(MICHELANGELO_1475));
		highScores.add(new HighScore(CRANACH_1472));
		highScores.add(new HighScore(LEONARDO_1452));
		highScores.add(new HighScore(GHIRLANDAIO_1449));
		highScores.add(new HighScore(EYCK_1395));
		highScores.add(new HighScore(DONATELLO_1386));
		highScores.add(new HighScore(DUCCIO_1260));
	}

	public static void init(List<String> stringList) {
		for (String s : stringList) {
			highScores.add(new HighScore(s));
		}
	}

	public static int addHighScore(HighScore highScore) {
		int newScore = highScore.score;
		int rank = getRank(newScore);
		highScores.add(rank, highScore);
		return rank;
	}

	private static int getRank(int newScore) {
		int rank = 0;
		if (newScore <= highScores.get(0).score) { // Not first place
			for (int i = highScores.size() - 1; i >= 0; --i) {
				if (newScore < highScores.get(i).score) {
					rank = i + 1;
					break;
				}
			}
		}
		return rank;
	}

	public static int size() {
		return highScores.size();
	}

	public static HighScore get(int i) {
		return highScores.get(i);
	}

	public static class HighScore {
		private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d, ''yy h:mm:ss a");

		public final String name;
		public final int score;
		public final int level;
		public final int captures;
		public final long dateTime;

		public HighScore(String s) {
			String[] split = s.split(",");
			name = split[0];
			score = Integer.parseInt(split[1]);
			level = Integer.parseInt(split[2]);
			captures = Integer.parseInt(split[3]);
			dateTime = Long.parseLong(split[4]);
		}

		public HighScore(String name, int score, int level, int captures, long dateTime) {
			this.name = name;
			this.score = score;
			this.level = level;
			this.captures = captures;
			this.dateTime = dateTime;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + score;
			result = prime * result + level;
			result = prime * result + captures;
			result = prime * result + (int) (dateTime ^ (dateTime >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof HighScores) {
				HighScore other = (HighScore) obj;
				return name.equals(other.name) && score == other.score && level == other.level && captures == other.captures && dateTime == other.dateTime;
			}
			return false;
		}

		@Override
		public String toString() {
			return name + ", " + score + ", " + level + ", " + captures + ", " + DATE_FORMAT.format(new Date(dateTime));
		}
	}
}
