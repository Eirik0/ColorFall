package gamestate.gameover;

import gamestate.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import main.GameDelegate;
import main.GameSettings;
import main.HighScores;
import main.HighScores.HighScore;

public class GameOverState implements GameState {
	private HighScores highScores;
	private final HighScore highScore;

	public GameOverState(GameDelegate gameDelegate, HighScore highScore) {
		this.highScore = highScore;
	}

	@Override
	public void init() {
		highScores = HighScores.loadFromFile();
		highScores.addHighScore(highScore);
		try {
			highScores.saveToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(long dt) {
	}

	@Override
	public void drawOn(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameSettings.componentWidth, GameSettings.componentWidth);
		g.setColor(Color.RED);
		for (int i = 0; i < highScores.size(); ++i) {
			g.drawString((i + 1) + ". " + highScores.get(i).toString(), 50, 50 * i + 50);
		}
	}

	@Override
	public void keyPressed(int keyCode) {
	}
}
