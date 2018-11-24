package cf.gamestate.menu;

import java.awt.Graphics2D;

import cf.gameentity.score.GameScore;
import cf.gamestate.colorfall.BouncingPolygon;
import cf.gamestate.colorfall.ColorFallState;
import cf.gamestate.gameover.HighScoresState;
import gt.gamestate.GameState;
import gt.gamestate.GameStateManager;
import gt.gamestate.UserInput;

public class PauseMenuState implements GameState {
    private final ColorFallState colorFallState;
    private final MenuState pauseMenu;

    public PauseMenuState(ColorFallState colorFallState, GameScore score, BouncingPolygon bouncingPolygon) {
        this.colorFallState = colorFallState;
        pauseMenu = new MenuState()
                .addMenuItem(new MenuItem("Return", () -> GameStateManager.setGameState(colorFallState)))
                .addMenuItem(new MenuItem("End Game", () -> GameStateManager.setGameState(HighScoresState.getGameOverState(score, bouncingPolygon))));
    }

    @Override
    public void update(double dt) {
        colorFallState.update(dt, false, false);
        pauseMenu.update(dt);
    }

    @Override
    public void drawOn(Graphics2D graphics) {
        colorFallState.drawOn(graphics, false, true);
        pauseMenu.drawOn(graphics, false);
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
