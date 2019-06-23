package cf.gamestate.gameover;

import java.awt.Color;

import cf.gamestate.colorfall.BouncingPolygon;
import cf.main.ColorFall;
import gt.component.ComponentCreator;
import gt.gameentity.GameImageDrawer;
import gt.gameentity.IGameImage;
import gt.gameentity.IGraphics;
import gt.gameloop.TimeConstants;
import gt.gamestate.GameState;
import gt.gamestate.GameStateManager;
import gt.gamestate.UserInput;

public class HighScoresState implements GameState {
    private static final int PIXELS_BETWEEN_SCORES = 50;
    private static final int TOP_SCORE_HEIGHT = 125;

    private static final double NANOS_PER_PIXEL = 50 * TimeConstants.NANOS_PER_MILLISECOND;

    private final HighScores highScores;

    private final IGameImage scrollingScoreImage;

    private final BouncingPolygon bouncingPolygon;

    private boolean hasResetScoreOffset = false;
    private double currentScoreOffset = -PIXELS_BETWEEN_SCORES / 2;

    private final GameImageDrawer imageDrawer;
    private final GameStateManager gameStateManager;
    private int width;
    private int scrollingImageHeight;

    public HighScoresState(GameStateManager gameStateManager, HighScores highScores) {
        this.gameStateManager = gameStateManager;
        imageDrawer = gameStateManager.getImageDrawer();
        this.highScores = highScores;
        scrollingScoreImage = imageDrawer.newGameImage(ComponentCreator.DEFAULT_WIDTH, ComponentCreator.DEFAULT_HEIGHT - TOP_SCORE_HEIGHT);
        bouncingPolygon = new BouncingPolygon(scrollingScoreImage, Color.GREEN, highScores.get(0).level);
    }

    @Override
    public void update(double dt) {
        bouncingPolygon.update(dt);
        currentScoreOffset += dt / NANOS_PER_PIXEL;
        redrawScrollingScoreImage();
        if (currentScoreOffset > PIXELS_BETWEEN_SCORES * highScores.size()) {
            hasResetScoreOffset = true;
            currentScoreOffset = -scrollingImageHeight + PIXELS_BETWEEN_SCORES;
        }
    }

    private void redrawScrollingScoreImage() {
        IGraphics g = scrollingScoreImage.getGraphics();

        g.fillRect(0, 0, width, scrollingImageHeight, ComponentCreator.backgroundColor());
        bouncingPolygon.drawOn(g);

        g.setColor(Color.RED);
        g.setFont(ColorFall.GAME_FONT);
        for (int i = 1; i < highScores.size(); ++i) {
            double y = i * PIXELS_BETWEEN_SCORES;
            if (currentScoreOffset > 0 || hasResetScoreOffset) { // this causes the scrolling to start with a delay
                y -= currentScoreOffset;
            }
            String scoreText = (i + 1) + ". " + highScores.get(i).toString();
            g.drawCenteredString(scoreText, width / 2, y);
        }
    }

    @Override
    public void drawOn(IGraphics g) {
        g.fillRect(0, 0, width, TOP_SCORE_HEIGHT, ComponentCreator.backgroundColor());

        HighScore topScore = highScores.get(0);

        g.setColor(Color.RED);
        g.setFont(ColorFall.GAME_FONT_LARGE);

        String topScoreLine1 = "1. " + topScore.name + "   Score: " + topScore.score;
        String topScoreLine2 = "Level: " + topScore.level + "   Captures: " + topScore.captures + "   Time: " + HighScore.formatTime(topScore.time);

        g.drawCenteredString(topScoreLine1, width / 2, PIXELS_BETWEEN_SCORES);
        g.drawCenteredString(topScoreLine2, width / 2, PIXELS_BETWEEN_SCORES * 2);
        imageDrawer.drawImage(g, scrollingScoreImage, 0, TOP_SCORE_HEIGHT);
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        scrollingImageHeight = height - TOP_SCORE_HEIGHT;
        scrollingScoreImage.setSize(width, scrollingImageHeight);
        redrawScrollingScoreImage();
    }

    @Override
    public void handleUserInput(UserInput input) {
        if (UserInput.isKeyboardInput(input)) {
            gameStateManager.setGameState(ColorFall.getMainMenuState());
        }
    }
}
