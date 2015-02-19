package gamestate.menu;

import gamestate.GameState;
import main.GameDelegate;

public class PauseMenuState extends MenuState {
	public PauseMenuState(GameDelegate gameDelegate, GameState previousState) {
		addMenuItem(new MenuItem("Return", () -> gameDelegate.setState(previousState)));
	}
}
