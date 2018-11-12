package cf.gamestate.colorfall;

import gt.gameentity.Updatable;

public class DurationTimer implements Updatable {
    private final double duration;

    private double internalTimer = 0;

    public DurationTimer(double duration) {
        this.duration = duration;
    }

    @Override
    public void update(double dt) {
        internalTimer += dt;
    }

    public double getPercentComplete() {
        return Math.min(internalTimer / duration, 1);
    }

    public void reset() {
        internalTimer = 0;
    }
}
