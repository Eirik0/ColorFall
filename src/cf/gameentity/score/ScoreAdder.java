package cf.gameentity.score;

import java.awt.Graphics2D;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import gt.gameentity.GameEntity;

public class ScoreAdder implements GameEntity {
    private final String name;

    private final int x;
    private final int y;

    private int value = 0;
    private int nextValue = 0;

    private UpdatingScore currentUpdate = null;

    private final Queue<Integer> queue = new LinkedBlockingDeque<>();

    public ScoreAdder(String name, int x, int y) {
        this.name = name;

        this.x = x;
        this.y = y;
    }

    @Override
    public void update(double dt) {
        if (currentUpdate == null) {
            Integer toAdd = queue.poll();
            if (toAdd != null) {
                nextValue = value + toAdd.intValue();
                currentUpdate = new UpdatingScore(name, value, nextValue, x, y);
                currentUpdate.update(dt);
            }
        } else {
            currentUpdate.update(dt);
            if (currentUpdate.timer.getPercentComplete() >= 1) {
                value = nextValue;
                currentUpdate = null;
            }
        }
    }

    @Override
    public void drawOn(Graphics2D graphics) {
        if (currentUpdate == null) {
            graphics.drawString(name + ": " + value, x, y);
        } else {
            currentUpdate.drawOn(graphics);
        }
    }

    public void add(int valueToAdd, int numTimes) {
        for (int i = 0; i < numTimes; ++i) {
            queue.add(Integer.valueOf(valueToAdd));
        }
    }

    public int getValue() {
        return value;
    }

    public int getQueueSize() {
        return queue.size();
    }
}
