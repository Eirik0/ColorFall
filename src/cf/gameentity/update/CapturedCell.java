package cf.gameentity.update;

import cf.gamestate.colorfall.DurationTimer;
import gt.gameloop.TimeConstants;

public class CapturedCell {
    private static final double DURATION = TimeConstants.NANOS_PER_SECOND / 4;

    public final int x;
    public final int y;
    public final int color;

    public final DurationTimer timer;

    public CapturedCell(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
        timer = new DurationTimer(DURATION);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime * (prime + x) + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CapturedCell other = (CapturedCell) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
