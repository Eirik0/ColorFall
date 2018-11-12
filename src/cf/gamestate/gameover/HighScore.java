package cf.gamestate.gameover;

import cf.main.ColorFall;

public class HighScore {
    public final String name;
    public final int score;
    public final int level;
    public final int captures;
    public final long time;

    public HighScore(String s) {
        String[] split = s.split(",");
        name = split[0];
        score = Integer.parseInt(split[1]);
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
        result = prime * result + score;
        result = prime * result + level;
        result = prime * result + captures;
        result = prime * result + (int) (time ^ (time >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HighScore other = (HighScore) obj;
        if (captures != other.captures) {
            return false;
        }
        if (level != other.level) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (score != other.score) {
            return false;
        }
        if (time != other.time) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + ", " + score + ", " + level + ", " + captures + ", " + ColorFall.formatTime(time);
    }
}
