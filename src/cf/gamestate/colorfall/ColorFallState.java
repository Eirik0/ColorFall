package gamestate.colorfall;

import game.FallingColumn;
import game.GameGrid;
import game.score.GameScore;
import gameentity.background.LevelBackground;
import gameentity.update.GridUpdateEntity;
import gamestate.GameState;
import gamestate.gameover.NameEntryState;
import gamestate.menu.PauseMenuState;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;

import main.GameDelegate;
import main.GameSettings;
import util.DrawingUtilities;

public class ColorFallState implements GameState {
	private final GameDelegate gameDelegate;

	final LevelBackground levelBackground;

	private final GameGrid gameGrid;
	final GameScore score;

	private FallingColumn fallingColumn;

	private boolean keyDownPressed = false;

	public ColorFallState(GameDelegate gameDelegate) {
		this.gameDelegate = gameDelegate;

		gameGrid = new GameGrid();
		score = new GameScore(0, 1, 0);

		fallingColumn = FallingColumn.newRandom(gameGrid, 1);

		levelBackground = new LevelBackground(1);
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
		levelBackground.update(dt);
		score.update(dt);
	}

	@Override
	public void drawOn(Graphics g) {
		levelBackground.drawOn(g);
		DrawingUtilities.drawGrid(g, gameGrid.getGrid());
		score.drawOn(g);
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
		List<GridUpdateEntity> updates = gameGrid.placeColumn(fallingColumn, score, drop);

		fallingColumn = FallingColumn.newRandom(gameGrid, score.getLevel());

		if (updates.size() > 0) {
			gameDelegate.setState(new GameUpdateState(gameDelegate, this, updates));
		} else if (gameGrid.get(fallingColumn.getX(), fallingColumn.getY()) != GameGrid.UNPLAYED) {
			gameDelegate.setState(new NameEntryState(gameDelegate, score));
		}
	}
}
