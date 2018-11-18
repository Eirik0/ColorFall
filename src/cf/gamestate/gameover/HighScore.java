package cf.gamestate.gameover;

import java.util.Objects;

public class HighScore {
    private static final long MS_PER_YEAR = 1000L * 60 * 60 * 24 * 365;

    public final String name;
    public final long score;
    public final int level;
    public final int captures;
    public final long time;

    public HighScore(String s) {
        String[] split = s.split(",");
        name = split[0];
        score = Long.parseLong(split[1]);
        level = Integer.parseInt(split[2]);
        captures = Integer.parseInt(split[3]);
        time = Long.parseLong(split[4]);
    }

    public HighScore(String name, int score, int level, int captures, long time) {
        this.name = name;
        this.score = score;
        this.level = level;
        this.captures = captures;
        this.time = time;
    }

    public String toFileString() {
        return name + "," + score + "," + level + "," + captures + "," + time;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (int) (score ^ (score >>> 32));
        result = prime * result + level;
        result = prime * result + captures;
        return prime * result + (int) (time ^ (time >>> 32));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HighScore other = (HighScore) obj;
        return Objects.equals(name, other.name) && score == other.score && level == other.level && captures == other.captures && time == other.time;
    }

    @Override
    public String toString() {
        return name + ", " + score + ", " + level + ", " + captures + ", " + formatTime(time);
    }

    public static String formatTime(long time) {
        if (time >= MS_PER_YEAR) {
            return (time / MS_PER_YEAR) + "y";
        }
        time /= 100;
        long secondTenths = time % 10;
        time /= 10;
        long seconds = time % 60;
        time /= 60;
        return time + "m " + seconds + "." + secondTenths + "s";
    }
}
