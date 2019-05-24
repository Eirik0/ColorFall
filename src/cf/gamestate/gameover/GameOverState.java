package cf.gamestate.gameover;

import java.awt.Color;
import java.awt.Graphics2D;

import cf.gameentity.score.GameScore;
import cf.gamestate.colorfall.BouncingPolygon;
import cf.gamestate.colorfall.ColorFallState;
import cf.main.ColorFall;
import gt.gameentity.DurationTimer;
import gt.gameloop.TimeConstants;
import gt.gamestate.GameState;
import gt.gamestate.GameStateManager;
import gt.gamestate.UserInput;

public class GameOverState implements GameState {
    private static final double GAME_OVER_DURATION = TimeConstants.NANOS_PER_SECOND * 3;

    private final ColorFallState colorFallState;
    private GameScore score;
    private BouncingPolygon bouncingPolygon;

    private final DurationTimer timer;

    int width;
    int height;

    public GameOverState(ColorFallState colorFallState, GameScore score, BouncingPolygon bouncingPolygon) {
        this.colorFallState = colorFallState;
        this.score = score;
        this.bouncingPolygon = bouncingPolygon;
        timer = new DurationTimer(GAME_OVER_DURATION);
    }

    @Override
    public void update(double dt) {
        timer.update(dt);
        if (timer.getPercentComplete() >= 1) {
            GameStateManager.setGameState(getGameOverState(score, bouncingPolygon));
        }
        colorFallState.update(dt, false, false);
    }

    @Override
    public void drawOn(Graphics2D graphics) {
        colorFallState.drawOn(graphics, true, true, timer.getPercentComplete());
        graphics.setColor(Color.RED);
        graphics.setFont(ColorFall.GAME_FONT_XL);
        drawCenteredString(graphics, "GAME OVER", width / 2, height / 2);
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        colorFallState.setSize(width, height);
    }

    @Override
    public void handleUserInput(UserInput input) {
    }

    private static GameState getGameOverState(GameScore score, BouncingPolygon bouncingPolygon) {
        HighScores highScores = HighScores.loadFromFile();
        if (highScores.getRank(score.getScore()) < HighScores.MAX_NUM_SCORES) {
            return new NameEntryState(score, highScores, bouncingPolygon);
        } else {
            return new HighScoresState(highScores);
        }
    }
}
