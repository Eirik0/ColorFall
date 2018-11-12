package cf.gamestate.menu;

import gt.gamestate.GameState;
import gt.gamestate.GameStateManager;

public class PauseMenuState extends MenuState {
    public PauseMenuState(GameState previousState) {
        addMenuItem(new MenuItem("Return", () -> GameStateManager.setGameState(previousState)));
    }
}
