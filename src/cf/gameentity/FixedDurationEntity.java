package cf.gameentity;

public abstract class FixedDurationEntity implements GameEntity {
    protected final long duration;

    private double internalTimer = 0;

    protected FixedDurationEntity(long duration) {
        this.duration = duration;
    }

    @Override
    public void update(long dt) {
        internalTimer += dt;
    }

    protected double getPercentComplete() {
        return Math.min(internalTimer / duration, 1);
    }

    public boolean isFinished() {
        return internalTimer >= duration;
    }

    public long getExcessTime() {
        return (long) internalTimer - duration;
    }
}
