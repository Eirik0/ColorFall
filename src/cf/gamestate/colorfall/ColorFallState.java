package cf.gamestate.colorfall;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;

import cf.game.FallingColumn;
import cf.game.GameGrid;
import cf.game.score.GameScore;
import cf.gameentity.background.LevelBackground;
import cf.gameentity.update.GridUpdateEntity;
import cf.gamestate.GameState;
import cf.gamestate.gameover.NameEntryState;
import cf.gamestate.menu.PauseMenuState;
import cf.main.GameDelegate;
import cf.main.GameSettings;
import cf.util.DrawingUtilities;

public class ColorFallState implements GameState {
    private final GameDelegate gameDelegate;

    final LevelBackground levelBackground;

    private final GameGrid gameGrid;
    final GameScore score;

    private FallingColumn fallingColumn;

    private boolean keyDownPressed = false;

    public ColorFallState(GameDelegate gameDelegate) {
        this.gameDelegate = gameDelegate;

        gameGrid = new GameGrid();
        score = new GameScore(0, 1, 0);

        fallingColumn = FallingColumn.newRandom(gameGrid, 1);

        levelBackground = new LevelBackground(1);
    }

    @Override
    public void init() {
        GameSizer.resizeTo(GameSettings.componentWidth, GameSettings.componentHeight);
    }

    @Override
    public void update(long dt) {
        if (keyDownPressed) {
            placeColumn(true);
            keyDownPressed = false;
        } else {
            fallingColumn.update(dt);
            if (fallingColumn.wasPlaced()) {
                placeColumn(false);
            }
        }
        levelBackground.update(dt);
        score.update(dt);
    }

    @Override
    public void drawOn(Graphics g) {
        levelBackground.drawOn(g);
        DrawingUtilities.drawGrid(g, gameGrid.getGrid());
        score.drawOn(g);
        fallingColumn.drawOn(g);
    }

    @Override
    public void keyPressed(int keyCode) {
        switch (keyCode) {
        case KeyEvent.VK_UP:
            doKeyUp();
            break;
        case KeyEvent.VK_DOWN:
            doKeyDown();
            break;
        case KeyEvent.VK_LEFT:
            doKeyLeft();
            break;
        case KeyEvent.VK_RIGHT:
            doKeyRight();
            break;
        case KeyEvent.VK_ESCAPE:
            pause();
            break;
        }
    }

    private void doKeyUp() {
        fallingColumn.rotate();
    }

    private void doKeyDown() {
        keyDownPressed = true;
    }

    private void doKeyLeft() {
        fallingColumn.maybeMoveLeft();
    }

    private void doKeyRight() {
        fallingColumn.maybeMoveRight();
    }

    private void pause() {
        gameDelegate.setState(new PauseMenuState(gameDelegate, this));
    }

    private void placeColumn(boolean drop) {
        List<GridUpdateEntity> updates = gameGrid.placeColumn(fallingColumn, score, drop);

        fallingColumn = FallingColumn.newRandom(gameGrid, score.getLevel());

        if (updates.size() > 0) {
            gameDelegate.setState(new GameUpdateState(gameDelegate, this, updates));
        } else if (gameGrid.get(fallingColumn.getX(), fallingColumn.getY()) != GameGrid.UNPLAYED) {
            gameDelegate.setState(new NameEntryState(gameDelegate, score));
        }
    }
}
