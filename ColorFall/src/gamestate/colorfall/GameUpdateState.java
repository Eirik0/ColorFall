package gamestate.colorfall;

import game.score.GameScore;
import gameentity.update.UpdateEntity;
import gamestate.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import main.GameDelegate;
import util.DrawingUtilities;

public class GameUpdateState implements GameState {
	private final GameDelegate gameDelegate;

	private final ColorFallState previousState;
	private final GameScore score;

	private final List<UpdateEntity> updateEntities;
	private int currentUpdate;

	public GameUpdateState(GameDelegate gameDelegate, ColorFallState previousState, GameScore score, List<UpdateEntity> updateEntities) {
		this.gameDelegate = gameDelegate;

		this.previousState = previousState;
		this.score = score;

		this.updateEntities = updateEntities;
		currentUpdate = 0;
	}

	@Override
	public void init() {
	}

	@Override
	public void update(long dt) {
		updateEntities.get(currentUpdate).update(dt);
		if (updateEntities.get(currentUpdate).updateFinished()) {
			++currentUpdate;
			if (currentUpdate == updateEntities.size()) {
				gameDelegate.setState(previousState);
			}
		}
		score.update(dt);
	}

	@Override
	public void drawOn(Graphics g) {
		int c = DrawingUtilities.round(255 / score.getLevel());
		g.setColor(new Color(c, c, c));
		g.fillRect(0, 0, GameSizer.getComponentWidth(), GameSizer.getComponentHeight());
		DrawingUtilities.drawGrid(g, updateEntities.get(currentUpdate).getGrid());
		score.drawOn(g);
		updateEntities.get(currentUpdate).drawOn(g);
	}

	@Override
	public void keyPressed(int keyCode) {
	}
}
