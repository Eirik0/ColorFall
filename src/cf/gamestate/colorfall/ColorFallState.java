package cf.gamestate.colorfall;

import java.awt.Graphics2D;
import java.util.List;

import cf.gameentity.score.GameScore;
import cf.gameentity.update.CapturedCell;
import cf.gamestate.gameover.NameEntryState;
import cf.gamestate.menu.PauseMenuState;
import cf.main.ColorFall;
import gt.component.ComponentCreator;
import gt.gameentity.GridSizer;
import gt.gamestate.GameState;
import gt.gamestate.GameStateManager;
import gt.gamestate.UserInput;

public class ColorFallState implements GameState {
    final GameGrid gameGrid;

    final GameScore score;

    private FallingColumn fallingColumn;

    private BouncingPolygon bouncingPolygon;

    int width;
    int height;

    GridSizer sizer;

    public ColorFallState() {
        int level = 1;

        bouncingPolygon = new BouncingPolygon(this, level);

        gameGrid = new GameGrid();
        score = new GameScore(0, level, 0);

        nextFallingColumn();
    }

    public void nextFallingColumn() {
        fallingColumn = FallingColumn.newRandom(score.getLevel());
        if (gameGrid.get(fallingColumn.getX(), fallingColumn.getY()) != GameGrid.UNPLAYED) {
            GameStateManager.setGameState(new NameEntryState(score, bouncingPolygon));
        }
    }

    @Override
    public void update(double dt) {
        update(dt, true);
    }

    public void update(double dt, boolean updateFallingColumn) {
        score.update(dt);
        if (score.getLevel() > bouncingPolygon.numSides) {
            bouncingPolygon = bouncingPolygon.nextLevelPolygon();
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
            nextFallingColumn();
        }
    }

    @Override
    public void drawOn(Graphics2D graphics) {
        drawOn(graphics, true, true);
    }

    public void drawOn(Graphics2D graphics, boolean drawScore, boolean drawFallingColumn) {
        fillRect(graphics, 0, 0, width, height, ComponentCreator.backgroundColor());
        bouncingPolygon.drawOn(graphics);
        drawRect(graphics, sizer.offsetX, sizer.offsetY, sizer.gridWidth, sizer.gridHeight, ComponentCreator.foregroundColor());
        if (drawScore) {
            score.drawOn(graphics);
        }
        for (int x = 0; x < GameGrid.WIDTH; ++x) {
            for (int y = 0; y < GameGrid.HEIGHT; ++y) {
                int color = gameGrid.get(x, y);
                if (color != GameGrid.UNPLAYED) {
                    ColorFall.drawCell(graphics, sizer.getCenterX(x), sizer.getCenterY(y), sizer.cellSize / 2, color);
                }
            }
        }
        if (drawFallingColumn) {
            int fCX = fallingColumn.getX();
            int fCY = fallingColumn.getY();
            ColorFall.drawCell(graphics, sizer.getCenterX(fCX), sizer.getCenterY(fCY), sizer.cellSize / 2, fallingColumn.getColor1());
            ColorFall.drawCell(graphics, sizer.getCenterX(fCX), sizer.getCenterY(fCY - 1), sizer.cellSize / 2, fallingColumn.getColor2());
            ColorFall.drawCell(graphics, sizer.getCenterX(fCX), sizer.getCenterY(fCY - 2), sizer.cellSize / 2, fallingColumn.getColor3());
        }
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        sizer = new GridSizer(width, height, GameGrid.WIDTH, GameGrid.HEIGHT);
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
