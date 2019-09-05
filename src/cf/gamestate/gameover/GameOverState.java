package cf.gamestate.gameover;

import java.awt.Color;

import cf.gameentity.score.GameScore;
import cf.gamestate.colorfall.BouncingPolygon;
import cf.gamestate.colorfall.ColorFallState;
import cf.main.ColorFall;
import gt.gameentity.DurationTimer;
import gt.gameentity.IGraphics;
import gt.gameloop.TimeConstants;
import gt.gamestate.GameState;
import gt.gamestate.GameStateManager;
import gt.gamestate.UserInput;

public class GameOverState implements GameState {
    private static final double GAME_OVER_DURATION = TimeConstants.NANOS_PER_SECOND * 3;

    private final GameStateManager gameStateManager;
    private final ColorFallState colorFallState;
    private final GameScore score;
    private final BouncingPolygon bouncingPolygon;

    private final DurationTimer timer;

    double width;
    double height;

    public GameOverState(GameStateManager gameStateManager, ColorFallState colorFallState, GameScore score, BouncingPolygon bouncingPolygon) {
        this.gameStateManager = gameStateManager;
        this.colorFallState = colorFallState;
        this.score = score;
        this.bouncingPolygon = bouncingPolygon;
        timer = new DurationTimer(GAME_OVER_DURATION);
    }

    @Override
    public void update(double dt) {
        timer.update(dt);
        if (timer.getPercentComplete() >= 1) {
            gameStateManager.setGameState(getGameOverState(gameStateManager, score, bouncingPolygon));
        }
        colorFallState.update(dt, false, false);
    }

    @Override
    public void drawOn(IGraphics g) {
        colorFallState.drawOn(g, true, true, timer.getPercentComplete());
        g.setColor(Color.RED);
        g.setFont(ColorFall.GAME_FONT_XL);
        g.drawCenteredString("GAME OVER", width / 2, height / 2);
    }

    @Override
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
        colorFallState.setSize(width, height);
    }

    @Override
    public void handleUserInput(UserInput input) {
    }

    private static GameState getGameOverState(GameStateManager gameStateManager, GameScore score, BouncingPolygon bouncingPolygon) {
        HighScores highScores = HighScores.loadFromFile();
        if (highScores.getRank(score.getScore()) < HighScores.MAX_NUM_SCORES) {
            return new NameEntryState(gameStateManager, score, highScores, bouncingPolygon);
        } else {
            return new HighScoresState(gameStateManager, highScores);
        }
    }
}
