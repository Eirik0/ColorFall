package cf.gameentity.update;

import cf.gamestate.colorfall.DurationTimer;
import gt.gameloop.TimeConstants;

public class DroppingCell {
    private static final double DURATION = TimeConstants.NANOS_PER_SECOND / 4;

    public final int x;
    public final int y;
    public final int dy;
    public final int color;

    public final DurationTimer timer;

    public DroppingCell(int x, int y, int dy, int color) {
        this.x = x;
        this.y = y;
        this.dy = dy;
        this.color = color;

        timer = new DurationTimer(DURATION);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime * (prime * (prime + x) + y) + dy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DroppingCell other = (DroppingCell) obj;
        return x == other.x && y == other.y && dy == other.dy;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + "), " + dy;
    }
}
