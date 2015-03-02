package gamestate.gameover;

import gamestate.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import main.ColorFall;
import main.GameDelegate;
import main.GameSettings;
import main.HighScores;
import main.HighScores.HighScore;
import util.GameConstants;

public class GameOverState implements GameState {
	private final GameDelegate gameDelegate;

	private HighScores highScores;
	private final HighScore highScore;

	public GameOverState(GameDelegate gameDelegate, HighScore highScore) {
		this.gameDelegate = gameDelegate;
		this.highScore = highScore;
	}

	@Override
	public void init() {
		highScores = HighScores.loadFromFile();
		highScores.addHighScore(highScore);
		try {
			highScores.saveToFile();
		} catch (IOException e) {
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
		g.setFont(GameConstants.GAME_FONT);
		for (int i = 0; i < highScores.size(); ++i) {
			g.drawString((i + 1) + ". " + highScores.get(i).toString(), 50, 50 * i + 50);
		}
	}

	@Override
	public void keyPressed(int keyCode) {
		gameDelegate.setState(ColorFall.instance.mainMenuState);
	}
}
