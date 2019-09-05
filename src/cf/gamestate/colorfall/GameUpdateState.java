package cf.gamestate.colorfall;

import java.util.Collections;
import java.util.List;

import cf.gameentity.update.CapturedCell;
import cf.gameentity.update.DroppingCell;
import cf.main.ColorFall;
import gt.gameentity.IGraphics;
import gt.gamestate.GameState;
import gt.gamestate.GameStateManager;
import gt.gamestate.UserInput;

public class GameUpdateState implements GameState {
    private final GameStateManager gameStateManager;
    private final ColorFallState colorFallState;

    private List<CapturedCell> currentCaptures;
    private List<DroppingCell> currentDrops;

    private int comboDepth = 0;

    public GameUpdateState(GameStateManager gameStateManager, ColorFallState colorFallState, List<CapturedCell> currentCaptures) {
        this.gameStateManager = gameStateManager;
        this.colorFallState = colorFallState;
        this.currentCaptures = currentCaptures;
        currentDrops = Collections.emptyList();
        colorFallState.score.addToScore(currentCaptures.size(), comboDepth);
    }

    @Override
    public void update(double dt) {
        colorFallState.update(dt, false, true);
        if (currentCaptures.size() > 0) {
            boolean allComplete = true;
            for (CapturedCell capturedCell : currentCaptures) {
                capturedCell.timer.update(dt);
                allComplete = allComplete && capturedCell.timer.getPercentComplete() >= 1;
            }
            if (allComplete) {
                currentCaptures = Collections.emptyList();
                currentDrops = colorFallState.gameGrid.getDrops();
            }
        } else if (currentDrops.size() > 0) {
            boolean allComplete = true;
            for (DroppingCell droppingCell : currentDrops) {
                droppingCell.timer.update(dt);
                allComplete = allComplete && droppingCell.timer.getPercentComplete() >= 1;
            }
            if (allComplete) {
                ++comboDepth;
                currentCaptures = colorFallState.gameGrid.placeDrops(currentDrops);
                colorFallState.score.addToScore(currentCaptures.size(), comboDepth);
                currentDrops = Collections.emptyList();
            }
        } else {
            colorFallState.setNextFallingColumn();
            gameStateManager.setGameState(colorFallState);
        }
    }

    @Override
    public void drawOn(IGraphics g) {
        colorFallState.drawOn(g, true, false, 0);
        for (CapturedCell capturedCell : currentCaptures) {
            double x = colorFallState.sizer.getCenterX(capturedCell.x);
            double y = colorFallState.sizer.getCenterY(capturedCell.y);
            double radius = (colorFallState.sizer.cellSize / 2) * (1 - capturedCell.timer.getPercentComplete());
            ColorFall.drawCell(g, x, y, radius, capturedCell.color);
        }
        for (DroppingCell droppingCell : currentDrops) {
            double x = colorFallState.sizer.getCenterX(droppingCell.x);
            double oldCellY = colorFallState.sizer.getCenterY(droppingCell.y);
            double newCellY = colorFallState.sizer.getCenterY(droppingCell.y + droppingCell.dy);
            double y = oldCellY + (newCellY - oldCellY) * droppingCell.timer.getPercentComplete();
            ColorFall.drawCell(g, x, y, colorFallState.sizer.cellSize / 2, droppingCell.color);
        }
    }

    @Override
    public void setSize(double width, double height) {
        colorFallState.setSize(width, height);
    }

    @Override
    public void handleUserInput(UserInput input) {
    }
}
