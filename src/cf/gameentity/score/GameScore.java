package cf.gameentity.score;

import java.awt.Graphics2D;

import cf.main.ColorFall;
import gt.component.ComponentCreator;
import gt.gameentity.GameEntity;
import gt.gameloop.TimeConstants;

public class GameScore implements GameEntity {
    private static final int LEFT_PADDING = 20;
    private static final int SCORE_Y = 100;
    private static final int LEVEL_Y = 200;
    private static final int CAPTURES_Y = 300;
    private static final int TIME_Y = 400;

    private int score;
    private int startingLevel;
    private int level;
    private int captures;
    private double time;

    private final ScoreAdder scoreAdder = new ScoreAdder("Score", LEFT_PADDING, SCORE_Y);
    private final ScoreAdder capturesAdder = new ScoreAdder("Captures", LEFT_PADDING, CAPTURES_Y);

    public GameScore(int score, int level, int captures) {
        this.score = score;
        startingLevel = level;
        this.level = level;
        this.captures = captures;
        time = 0;
    }

    @Override
    public void update(double dt) {
        scoreAdder.update(dt);
        capturesAdder.update(dt);
        time += dt / TimeConstants.NANOS_PER_MILLISECOND;
    }

    @Override
    public void drawOn(Graphics2D graphics) {
        graphics.setColor(ComponentCreator.foregroundColor());
        graphics.setFont(ColorFall.GAME_FONT);
        scoreAdder.drawOn(graphics);
        graphics.drawString("Level: " + level, LEFT_PADDING, LEVEL_Y);
        capturesAdder.drawOn(graphics);
        graphics.drawString("Time: " + ColorFall.formatTime(time), LEFT_PADDING, TIME_Y);
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getCaptures() {
        return captures;
    }

    public double getTime() {
        return time;
    }

    public void addToScore(int numCaptures, int comboDepth) {
        int baseScore = 5 * level;
        int scorePerCapture = baseScore + comboDepth * baseScore;

        score += numCaptures * scorePerCapture;
        captures += numCaptures;

        scoreAdder.add(scorePerCapture, numCaptures);
        capturesAdder.add(1, numCaptures);

        if (captures >= (level + 1 - startingLevel) * ColorFall.CAPTURES_PER_LEVEL) {
            ++level;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + captures;
        result = prime * result + level;
        result = prime * result + score;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GameScore other = (GameScore) obj;
        return captures == other.captures && level == other.level && score == other.score;
    }

    @Override
    public String toString() {
        return "Score: " + score + " Level: " + level + " Captures: " + captures;
    }
}
