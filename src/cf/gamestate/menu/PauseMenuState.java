package cf.gamestate.menu;

import cf.gameentity.score.GameScore;
import cf.gamestate.colorfall.BouncingPolygon;
import cf.gamestate.colorfall.ColorFallState;
import cf.gamestate.gameover.GameOverState;
import gt.gameentity.IGraphics;
import gt.gamestate.GameState;
import gt.gamestate.GameStateManager;
import gt.gamestate.UserInput;

public class PauseMenuState implements GameState {
    private final ColorFallState colorFallState;
    private final MenuState pauseMenu;

    public PauseMenuState(GameStateManager gameStateManager, ColorFallState colorFallState, GameScore score, BouncingPolygon bouncingPolygon) {
        this.colorFallState = colorFallState;
        pauseMenu = new MenuState()
                .addMenuItem(new MenuItem("Return", () -> gameStateManager.setGameState(colorFallState)))
                .addMenuItem(new MenuItem("End Game",
                        () -> gameStateManager.setGameState(new GameOverState(gameStateManager, colorFallState, score, bouncingPolygon))));
    }

    @Override
    public void update(double dt) {
        colorFallState.update(dt, false, false);
        pauseMenu.update(dt);
    }

    @Override
    public void drawOn(IGraphics g) {
        colorFallState.drawOn(g, false, true, 0);
        pauseMenu.drawOn(g, false);
    }

    @Override
    public void setSize(int width, int height) {
        colorFallState.setSize(width, height);
        pauseMenu.setSize(width, height);
    }

    @Override
    public void handleUserInput(UserInput input) {
        pauseMenu.handleUserInput(input);
    }
}
