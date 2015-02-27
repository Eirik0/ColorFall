package gamestate.colorfall;

import game.FallingColumn;
import game.GameGrid;
import game.GameScore;
import gameentity.update.UpdateEntity;
import gamestate.GameState;
import gamestate.gameover.NameEntryState;
import gamestate.menu.PauseMenuState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;

import main.GameDelegate;
import main.GameSettings;
import util.DrawingUtilities;

public class ColorFallState implements GameState {
	private final GameDelegate gameDelegate;

	private final GameGrid gameGrid;
	private final GameScore score;

	private FallingColumn fallingColumn;

	private boolean keyDownPressed = false;

	public ColorFallState(GameDelegate gameDelegate) {
		this.gameDelegate = gameDelegate;

		gameGrid = new GameGrid();
		score = new GameScore(0, 1, 0);

		fallingColumn = FallingColumn.newRandom(gameGrid);
	}

	@Override
	public void init() {
		GameSizer.resizeTo(GameSettings.componentWidth, GameSettings.componentHeight);
	}

	@Override
	public void update(long dt) {
		if (keyDownPressed) {
			placeColumn(true);
			keyDownPressed = false;
		} else {
			fallingColumn.update(dt);
			if (fallingColumn.wasPlaced()) {
				placeColumn(false);
			}
		}
	}

	@Override
	public void drawOn(Graphics g) {
		int c = DrawingUtilities.round(255 / score.getLevel());
		g.setColor(new Color(c, c, c));
		g.fillRect(0, 0, GameSizer.getComponentWidth(), GameSizer.getComponentHeight());
		DrawingUtilities.drawGrid(g, gameGrid.getGrid());
		DrawingUtilities.drawScore(g, score);
		fallingColumn.drawOn(g);
	}

	@Override
	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_UP:
			doKeyUp();
			break;
		case KeyEvent.VK_DOWN:
			doKeyDown();
			break;
		case KeyEvent.VK_LEFT:
			doKeyLeft();
			break;
		case KeyEvent.VK_RIGHT:
			doKeyRight();
			break;
		case KeyEvent.VK_ESCAPE:
			pause();
			break;
		}
	}

	private void doKeyUp() {
		fallingColumn.rotate();
	}

	private void doKeyDown() {
		keyDownPressed = true;
	}

	private void doKeyLeft() {
		fallingColumn.maybeMoveLeft();
	}

	private void doKeyRight() {
		fallingColumn.maybeMoveRight();
	}

	private void pause() {
		gameDelegate.setState(new PauseMenuState(gameDelegate, this));
	}

	private void placeColumn(boolean drop) {
		List<UpdateEntity> updates = gameGrid.placeColumn(fallingColumn, score, drop);

		fallingColumn = FallingColumn.newRandom(gameGrid);

		if (updates.size() > 0) {
			gameDelegate.setState(new GameUpdateState(gameDelegate, this, score, updates));
		} else if (gameGrid.get(fallingColumn.getX(), fallingColumn.getY()) != GameGrid.UNPLAYED) {
			gameDelegate.setState(new NameEntryState(gameDelegate, score));
		}
	}
}
