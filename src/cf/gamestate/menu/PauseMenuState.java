package cf.gamestate.menu;

import cf.gamestate.GameState;
import cf.main.GameDelegate;

public class PauseMenuState extends MenuState {
    public PauseMenuState(GameDelegate gameDelegate, GameState previousState) {
        addMenuItem(new MenuItem("Return", () -> gameDelegate.setState(previousState)));
    }
}
