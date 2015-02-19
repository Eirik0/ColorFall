package gamestate.gameover;

import game.GameScore;
import gamestate.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Date;

import main.GameDelegate;
import main.GameSettings;
import main.HighScores;
import main.HighScores.HighScore;

public class NameEntryState implements GameState {
	private final GameDelegate gameDelegate;
	private final GameScore score;

	private String name;

	public NameEntryState(GameDelegate gameDelegate, GameScore score) {
		this.gameDelegate = gameDelegate;
		this.score = score;

		name = "";
	}

	@Override
	public void init() {
	}

	@Override
	public void update(long dt) {
	}

	@Override
	public void drawOn(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameSettings.componentWidth, GameSettings.componentHeight);

		g.setColor(Color.GREEN);
		g.drawString(score.toString(), 100, 100);
		g.drawString("Name: " + name + "_", 200, 200);
	}

	@Override
	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_SPACE:
			addSpace();
			break;
		case KeyEvent.VK_ENTER:
			maybeCommitName();
			break;
		case KeyEvent.VK_BACK_SPACE:
			doDelete();
			break;
		default:
			maybeAddLetter(keyCode);
		}
	}

	private void maybeAddLetter(int keyCode) {
		// From KeyEvent:
		/** VK_A thru VK_Z are the same as ASCII 'A' thru 'Z' (0x41 - 0x5A) */
		if (keyCode >= 0x41 && keyCode <= 0x5A) {
			name = name + String.valueOf((char) keyCode);
		}
	}

	private void addSpace() {
		name = name + " ";
	}

	private void maybeCommitName() {
		if (!name.trim().isEmpty()) {
			int rank = HighScores.addHighScore(new HighScore(name, score.getScore(), score.getLevel(), score.getCaptures(), new Date().getTime()));
			gameDelegate.setState(new GameOverState(gameDelegate, rank));
		}
	}

	private void doDelete() {
		if (name.length() < 2) {
			name = "";
		} else {
			name = name.substring(0, name.length() - 2);
		}
	}
}
