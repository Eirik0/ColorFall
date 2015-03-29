package gamestate.gameover;

import gamestate.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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

	private double scoreOffset = -20;

	private final GameDelegate gameDelegate;

	private HighScores highScores;
	private final HighScore highScore;

	private BufferedImage scrollImage;
	private Graphics2D scrollGraphcis;

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

		scoreOffset = -20;

		scrollImage = new BufferedImage(GameSettings.componentWidth, GameSettings.componentHeight - 125, BufferedImage.TYPE_INT_RGB);
		scrollGraphcis = scrollImage.createGraphics();
	}

	@Override
	public void update(long dt) {
		scoreOffset += dt / 50000000.0;
	}

	@Override
	public void drawOn(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameSettings.componentWidth, GameSettings.componentWidth);
		g.setColor(Color.RED);
		g.setFont(GameConstants.GAME_FONT.deriveFont(Font.PLAIN, 30));
		HighScore topScore = highScores.get(0);
		DrawingUtilities.drawCenteredString(g, "1. " + topScore.name, SPACE_BETWEEN_SCORES);
		String topScoreLine2 = "Score: " + topScore.score + "   Level: " + topScore.level + "   Captures: " + topScore.captures;
		DrawingUtilities.drawCenteredString(g, topScoreLine2, 2 * SPACE_BETWEEN_SCORES);

		scrollGraphcis.setColor(Color.BLACK);
		scrollGraphcis.fillRect(0, 0, scrollImage.getWidth(), scrollImage.getHeight());

		scrollGraphcis.setColor(Color.RED);
		scrollGraphcis.setFont(GameConstants.GAME_FONT);
		for (int i = 1; i < highScores.size(); ++i) {
			int y = scoreOffset < 0 ? SPACE_BETWEEN_SCORES * i : DrawingUtilities.round(SPACE_BETWEEN_SCORES * i - scoreOffset);
			scrollGraphcis.drawString((i + 1) + ". " + highScores.get(i).toString(), 50, y);
		}

		g.drawImage(scrollImage, 0, 125, null);
	}

	@Override
	public void keyPressed(int keyCode) {
		gameDelegate.setState(ColorFall.instance.mainMenuState);
	}
}
