package cf.gamestate.colorfall;

import java.awt.Graphics;
import java.util.List;

import cf.game.score.GameScore;
import cf.gameentity.background.LevelBackground;
import cf.gameentity.update.GridUpdateEntity;
import cf.gamestate.GameState;
import cf.main.GameDelegate;
import cf.util.DrawingUtilities;

public class GameUpdateState implements GameState {
    private final GameDelegate gameDelegate;
    private final ColorFallState previousState;

    private final LevelBackground levelBackground;

    private final GameScore score;

    private final List<GridUpdateEntity> updateEntities;
    private int currentUpdate;

    public GameUpdateState(GameDelegate gameDelegate, ColorFallState colorFallState, List<GridUpdateEntity> updateEntities) {
        this.gameDelegate = gameDelegate;
        previousState = colorFallState;

        levelBackground = colorFallState.levelBackground;

        score = colorFallState.score;

        this.updateEntities = updateEntities;
        currentUpdate = 0;
    }

    @Override
    public void init() {
    }

    @Override
    public void update(long dt) {
        updateEntities.get(currentUpdate).update(dt);
        if (updateEntities.get(currentUpdate).isFinished()) {
            ++currentUpdate;
            if (currentUpdate == updateEntities.size()) {
                gameDelegate.setState(previousState);
            }
        }
        levelBackground.maybeLevelUp(score.getLevel());
        levelBackground.update(dt);
        score.update(dt);
    }

    @Override
    public void drawOn(Graphics g) {
        levelBackground.drawOn(g);
        DrawingUtilities.drawGrid(g, updateEntities.get(currentUpdate).getGrid());
        score.drawOn(g);
        updateEntities.get(currentUpdate).drawOn(g);
    }

    @Override
    public void keyPressed(int keyCode) {
    }
}
