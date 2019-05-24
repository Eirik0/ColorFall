package cf.gamestate.colorfall;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import cf.gameentity.score.GameScore;
import cf.gameentity.update.CapturedCell;
import cf.gamestate.gameover.GameOverState;
import cf.gamestate.menu.PauseMenuState;
import cf.main.ColorFall;
import gt.component.ComponentCreator;
import gt.gameentity.DurationTimer;
import gt.gameentity.GridSizer;
import gt.gameentity.Sized;
import gt.gameloop.TimeConstants;
import gt.gamestate.GameState;
import gt.gamestate.GameStateManager;
import gt.gamestate.UserInput;

public class ColorFallState implements GameState, Sized {
    private static final long LEVEL_UP_DURATION = TimeConstants.NANOS_PER_SECOND;

    private final DurationTimer levelUpTimer;

    private BouncingPolygon bouncingPolygon;

    final GameGrid gameGrid;
    final GameScore score;

    private FallingColumn nextFallingColumn;
    private FallingColumn fallingColumn;

    int width;
    int height;

    GridSizer sizer;

    public ColorFallState(int level) {
        levelUpTimer = new DurationTimer(LEVEL_UP_DURATION);

        bouncingPolygon = new BouncingPolygon(this, Color.RED, level);

        gameGrid = new GameGrid();
        score = new GameScore(0, level, 0);

        nextFallingColumn = FallingColumn.newRandom(level);
        setNextFallingColumn();
    }

    public void setNextFallingColumn() {
        fallingColumn = nextFallingColumn;
        nextFallingColumn = FallingColumn.newRandom(score.getLevel());
        if (gameGrid.get(fallingColumn.getX(), fallingColumn.getY()) != GameGrid.UNPLAYED) {
            GameStateManager.setGameState(new GameOverState(this, score, bouncingPolygon));
        }
    }

    @Override
    public void update(double dt) {
        update(dt, true, true);
    }

    public void update(double dt, boolean updateFallingColumn, boolean updateScoreTime) {
        score.update(dt, updateScoreTime);
        levelUpTimer.update(dt);
        if (score.getLevel() > bouncingPolygon.numSides) {
            bouncingPolygon = bouncingPolygon.nextLevelPolygon();
            levelUpTimer.reset();
        }
        bouncingPolygon.update(dt);
        if (updateFallingColumn) {
            fallingColumn.update(dt);
            if (fallingColumn.isTickTimeUp()) {
                if (!fallingColumn.maybeMove(gameGrid, 0, 1)) {
                    placeFallingColumn();
                } else {
                    fallingColumn.resetTickTimer();
                }
            }
        }
    }

    private void placeFallingColumn() {
        List<CapturedCell> capturedCells = gameGrid.placeColumn(fallingColumn);
        if (capturedCells.size() > 0) {
            GameStateManager.setGameState(new GameUpdateState(this, capturedCells));
        } else {
            setNextFallingColumn();
        }
    }

    @Override
    public void drawOn(Graphics2D graphics) {
        drawOn(graphics, true, true, 0);
    }

    public void drawOn(Graphics2D graphics, boolean drawScore, boolean drawFallingColumn, double gameOverTimerPercent) {
        Color backgroundColor = levelUpTimer.getPercentComplete() >= 1 ? ComponentCreator.backgroundColor()
                : fadeToColor(Color.DARK_GRAY, ComponentCreator.backgroundColor(), levelUpTimer.getPercentComplete());

        fillRect(graphics, 0, 0, width, height, backgroundColor);
        fillRect(graphics, sizer.offsetX, sizer.offsetY, sizer.gridWidth, sizer.gridHeight, ComponentCreator.backgroundColor());

        bouncingPolygon.drawOn(graphics);

        drawRect(graphics, sizer.offsetX, sizer.offsetY, sizer.gridWidth, sizer.gridHeight, ComponentCreator.foregroundColor());

        if (drawScore) {
            score.drawOn(graphics);
        }

        double cellRadius = sizer.cellSize / 2;

        for (int x = 0; x < GameGrid.WIDTH; ++x) {
            for (int y = 0; y < GameGrid.HEIGHT; ++y) {
                int color = gameGrid.get(x, y);
                if (color != GameGrid.UNPLAYED) {
                    double centerY = sizer.getCenterY(y);
                    centerY += (sizer.gridHeight + 2 * centerY) * gameOverTimerPercent;
                    ColorFall.drawCell(graphics, sizer.getCenterX(x), centerY, cellRadius, color);
                }
            }
        }

        graphics.setFont(ColorFall.GAME_FONT);
        graphics.setColor(ComponentCreator.foregroundColor());
        double nextColumnX = sizer.offsetX + sizer.gridWidth + 20;
        double nextColumnY = 40;
        drawCenteredYString(graphics, "Next:", nextColumnX, 20);
        ColorFall.drawCell(graphics, nextColumnX + cellRadius, nextColumnY + sizer.getCenterY(2), cellRadius, nextFallingColumn.getColor1());
        ColorFall.drawCell(graphics, nextColumnX + cellRadius, nextColumnY + sizer.getCenterY(1), cellRadius, nextFallingColumn.getColor2());
        ColorFall.drawCell(graphics, nextColumnX + cellRadius, nextColumnY + sizer.getCenterY(0), cellRadius, nextFallingColumn.getColor3());

        if (drawFallingColumn) {
            int fCX = fallingColumn.getX();
            int fCY = fallingColumn.getY();
            ColorFall.drawCell(graphics, sizer.getCenterX(fCX), sizer.getCenterY(fCY), cellRadius, fallingColumn.getColor1());
            ColorFall.drawCell(graphics, sizer.getCenterX(fCX), sizer.getCenterY(fCY - 1), cellRadius, fallingColumn.getColor2());
            ColorFall.drawCell(graphics, sizer.getCenterX(fCX), sizer.getCenterY(fCY - 2), cellRadius, fallingColumn.getColor3());
        }
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        sizer = new GridSizer(width, height, GameGrid.WIDTH, GameGrid.HEIGHT);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void handleUserInput(UserInput input) {
        switch (input) {
        case UP_KEY_PRESSED:
            fallingColumn.rotate();
            break;
        case DOWN_KEY_PRESSED:
            placeFallingColumn();
            break;
        case LEFT_KEY_PRESSED:
            fallingColumn.maybeMove(gameGrid, -1, 0);
            break;
        case RIGHT_KEY_PRESSED:
            fallingColumn.maybeMove(gameGrid, 1, 0);
            break;
        case ESC_KEY_PRESSED:
            GameStateManager.setGameState(new PauseMenuState(this, score, bouncingPolygon));
            break;
        }
    }
}
