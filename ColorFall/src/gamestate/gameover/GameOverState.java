package gamestate.gameover;

import gamestate.GameState;

import java.awt.Color;
import java.awt.Graphics;

import main.GameDelegate;
import main.GameSettings;
import main.HighScores;

public class GameOverState implements GameState {
	private int width;
	private int height;

	public GameOverState(GameDelegate gameDelegate, int rank) {
	}

	@Override
	public void init() {
		width = GameSettings.componentWidth;
		height = GameSettings.componentWidth;
	}

	@Override
	public void update(long dt) {
	}

	@Override
	public void drawOn(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.RED);
		for (int i = 0; i < HighScores.size(); ++i) {
			g.drawString((i + 1) + ". " + HighScores.get(i).toString(), 50, 50 * i + 50);
		}
	}

	@Override
	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub

	}
}
