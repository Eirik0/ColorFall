package cf.gamestate.gameover;

import java.awt.Color;
import java.awt.Graphics2D;

import cf.main.ColorFall;
import gt.component.ComponentCreator;
import gt.component.GameImage;
import gt.gameloop.TimeConstants;
import gt.gamestate.GameState;
import gt.gamestate.GameStateManager;
import gt.gamestate.UserInput;

public class HighScoresState implements GameState {
    private static final int PIXELS_BETWEEN_SCORES = 50;
    private static final int TOP_SCORE_HEIGHT = 125;

    private static final double NANOS_PER_PIXEL = 50 * TimeConstants.NANOS_PER_MILLISECOND;

    private final HighScores highScores;

    private GameImage scrollingScoreImage = new GameImage();

    private double currentScoreOffset = -PIXELS_BETWEEN_SCORES / 2;

    private int width;
    private int height;

    public HighScoresState(HighScores highScores) {
        this.highScores = highScores;
    }

    @Override
    public void update(double dt) {
        currentScoreOffset += dt / NANOS_PER_PIXEL;
        redrawScrollingScoreImage();
    }

    private void redrawScrollingScoreImage() {
        Graphics2D graphics = scrollingScoreImage.getGraphics();

        fillRect(graphics, 0, 0, width, height - TOP_SCORE_HEIGHT, ComponentCreator.backgroundColor());

        graphics.setColor(Color.RED);
        graphics.setFont(ColorFall.GAME_FONT);
        for (int i = 1; i < highScores.size(); ++i) {
            double y = i * PIXELS_BETWEEN_SCORES;
            if (currentScoreOffset > 0) { // this causes the scrolling to start with a delay
                y -= currentScoreOffset;
            }
            graphics.drawString((i + 1) + ". " + highScores.get(i).toString(), 50, round(y));
        }
    }

    @Override
    public void drawOn(Graphics2D graphics) {
        fillRect(graphics, 0, 0, width, TOP_SCORE_HEIGHT, ComponentCreator.backgroundColor());

        HighScore topScore = highScores.get(0);

        graphics.setColor(Color.RED);
        graphics.setFont(ColorFall.GAME_FONT_LARGE);

        String topScoreLine1 = "1. " + topScore.name + "   Score: " + topScore.score;
        String topScoreLine2 = "Level: " + topScore.level + "   Captures: " + topScore.captures + "   Time: " + ColorFall.formatTime(topScore.time);

        drawCenteredString(graphics, topScoreLine1, width / 2, PIXELS_BETWEEN_SCORES);
        drawCenteredString(graphics, topScoreLine2, width / 2, PIXELS_BETWEEN_SCORES * 2);

        graphics.drawImage(scrollingScoreImage.getImage(), 0, TOP_SCORE_HEIGHT, null);
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        scrollingScoreImage.setSize(width, height - TOP_SCORE_HEIGHT);
        redrawScrollingScoreImage();
    }

    @Override
    public void handleUserInput(UserInput input) {
        if (UserInput.isKeyboardInput(input)) {
            GameStateManager.setGameState(ColorFall.getMainMenuState());
        }
    }
}
