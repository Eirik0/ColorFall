package cf.gamestate.colorfall;

import java.awt.Color;
import java.util.List;

import cf.gameentity.score.GameScore;
import cf.gameentity.update.CapturedCell;
import cf.gamestate.gameover.GameOverState;
import cf.gamestate.menu.PauseMenuState;
import cf.main.ColorFall;
import gt.component.ComponentCreator;
import gt.gameentity.DrawingMethods;
import gt.gameentity.DurationTimer;
import gt.gameentity.GridSizer;
import gt.gameentity.IGraphics;
import gt.gameentity.Sized;
import gt.gameloop.TimeConstants;
import gt.gamestate.GameState;
import gt.gamestate.GameStateManager;
import gt.gamestate.UserInput;

public class ColorFallState implements GameState, Sized {
    private static final long LEVEL_UP_DURATION = TimeConstants.NANOS_PER_SECOND;

    private final GameStateManager gameStateManager;

    private final DurationTimer levelUpTimer;

    private BouncingPolygon bouncingPolygon;

    final GameGrid gameGrid;
    final GameScore score;

    private FallingColumn nextFallingColumn;
    private FallingColumn fallingColumn;

    int width;
    int height;

    GridSizer sizer;

    public ColorFallState(GameStateManager gameStateManager, int level) {
        this.gameStateManager = gameStateManager;
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
            gameStateManager.setGameState(new GameOverState(gameStateManager, this, score, bouncingPolygon));
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
            gameStateManager.setGameState(new GameUpdateState(gameStateManager, this, capturedCells));
        } else {
            setNextFallingColumn();
        }
    }

    @Override
    public void drawOn(IGraphics g) {
        drawOn(g, true, true, 0);
    }

    public void drawOn(IGraphics g, boolean drawScore, boolean drawFallingColumn, double gameOverTimerPercent) {
        Color backgroundColor = levelUpTimer.getPercentComplete() >= 1 ? ComponentCreator.backgroundColor()
                : DrawingMethods.fadeToColor(Color.DARK_GRAY, ComponentCreator.backgroundColor(), levelUpTimer.getPercentComplete());

        g.fillRect(0, 0, width, height, backgroundColor);
        g.fillRect(sizer.offsetX, sizer.offsetY, sizer.gridWidth + 1, sizer.gridHeight, ComponentCreator.backgroundColor());

        bouncingPolygon.drawOn(g);

        g.drawRect(sizer.offsetX, sizer.offsetY, sizer.gridWidth + 1, sizer.gridHeight, ComponentCreator.foregroundColor());

        if (drawScore) {
            score.drawOn(g);
        }

        double cellRadius = sizer.cellSize / 2;

        for (int x = 0; x < GameGrid.WIDTH; ++x) {
            for (int y = 0; y < GameGrid.HEIGHT; ++y) {
                int color = gameGrid.get(x, y);
                if (color != GameGrid.UNPLAYED) {
                    double centerY = sizer.getCenterY(y);
                    centerY += (sizer.gridHeight + 2 * centerY) * gameOverTimerPercent;
                    ColorFall.drawCell(g, sizer.getCenterX(x), centerY, cellRadius, color);
                }
            }
        }

        double nextColumnX = sizer.offsetX + sizer.gridWidth + 20;
        double nextColumnY = 40;
        g.setColor(ComponentCreator.foregroundColor());
        g.setFont(ColorFall.GAME_FONT);
        g.drawCenteredYString("Next:", nextColumnX, 20);
        ColorFall.drawCell(g, nextColumnX + cellRadius, nextColumnY + sizer.getCenterY(2), cellRadius, nextFallingColumn.getColor1());
        ColorFall.drawCell(g, nextColumnX + cellRadius, nextColumnY + sizer.getCenterY(1), cellRadius, nextFallingColumn.getColor2());
        ColorFall.drawCell(g, nextColumnX + cellRadius, nextColumnY + sizer.getCenterY(0), cellRadius, nextFallingColumn.getColor3());

        if (drawFallingColumn) {
            int fCX = fallingColumn.getX();
            int fCY = fallingColumn.getY();
            ColorFall.drawCell(g, sizer.getCenterX(fCX), sizer.getCenterY(fCY), cellRadius, fallingColumn.getColor1());
            ColorFall.drawCell(g, sizer.getCenterX(fCX), sizer.getCenterY(fCY - 1), cellRadius, fallingColumn.getColor2());
            ColorFall.drawCell(g, sizer.getCenterX(fCX), sizer.getCenterY(fCY - 2), cellRadius, fallingColumn.getColor3());
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
            gameStateManager.setGameState(new PauseMenuState(gameStateManager, this, score, bouncingPolygon));
            break;
        }
    }
}
