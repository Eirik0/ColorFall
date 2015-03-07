package game.score;

import gameentity.GameEntity;

import java.awt.Graphics;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

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
	public void update(long dt) {
		if (currentUpdate == null) {
			pollQueue();
			if (currentUpdate != null) {
				update(dt);
			}
		} else {
			currentUpdate.update(dt);
			if (currentUpdate.isFinished()) {
				long excessTime = currentUpdate.getExcessTime();
				currentUpdate = null;
				value = nextValue;
				update(excessTime);
			}
		}
	}

	private void pollQueue() {
		Integer toAdd = queue.poll();
		if (toAdd != null) {
			nextValue = value + toAdd;
			currentUpdate = new UpdatingScore(name, value, nextValue, x, y);
		}
	}

	@Override
	public void drawOn(Graphics g) {
		if (currentUpdate == null) {
			g.drawString(name + ": " + value, x, y);
		} else {
			currentUpdate.drawOn(g);
		}
	}

	public int getValue() {
		return value;
	}

	public int getQueueSize() {
		return queue.size();
	}

	public void add(int valueToAdd, int numTimes) {
		for (int i = 0; i < numTimes; ++i) {
			queue.add(valueToAdd);
		}
	}
}