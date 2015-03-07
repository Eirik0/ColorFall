package gamestate.gameover;

import gamestate.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

import main.ColorFall;
import main.GameDelegate;
import main.GameSettings;
import main.HighScores;
import main.HighScores.HighScore;
import util.DrawingUtilities;
import util.GameConstants;

public class HighScoresState implements GameState {
	private static final int SPACE_BETWEEN_SCORES = 50;

	private final GameDelegate gameDelegate;

	private HighScores highScores;
	private final HighScore highScore;

	public HighScoresState(GameDelegate gameDelegate, HighScore highScore) {
		this.gameDelegate = gameDelegate;
		this.highScore = highScore;
	}

	@Override
	public void init() {
		highScores = HighScores.loadFromFile();
		if (highScore != null) {
			highScores.addHighScore(highScore);
			try {
				highScores.saveToFile();
			} catch (IOException e) {
			}
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
		g.setFont(GameConstants.GAME_FONT.deriveFont(Font.PLAIN, 30));
		HighScore topScore = highScores.get(1);
		DrawingUtilities.drawCenteredString(g, "1. " + topScore.name, SPACE_BETWEEN_SCORES);
		String topScoreLine2 = "Score: " + topScore.score + "   Level: " + topScore.level + "   Captures: " + topScore.captures;
		DrawingUtilities.drawCenteredString(g, topScoreLine2, 2 * SPACE_BETWEEN_SCORES);
		g.setFont(GameConstants.GAME_FONT);
		for (int i = 1; i < highScores.size(); ++i) {
			g.drawString((i + 1) + ". " + highScores.get(i).toString(), 50, 50 * i + 100);
		}
	}

	@Override
	public void keyPressed(int keyCode) {
		gameDelegate.setState(ColorFall.instance.mainMenuState);
	}
}
