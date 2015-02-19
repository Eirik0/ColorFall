package gamestate.menu;

import gamestate.GameState;

import javax.swing.JFrame;

import main.ColorFall;
import main.GameDelegate;
import main.GameSettings;

public class OptionsMenuState extends MenuState {
	public OptionsMenuState(GameDelegate gameDelegate, GameState previousState, JFrame mainFrame) {
		addMenuItem(new MenuItem("Save and Return", () -> {
			for (int i = 0; i < menuItems.size(); ++i) {
				MenuItemList subMenu = menuItems.getItem(i).subMenu;
				if (subMenu.size() > 0) {
					subMenu.getSelectedItem().itemAction.run();
				}
			}
			ColorFall.saveSettings();
			gameDelegate.setSettings();
			mainFrame.pack();
			gameDelegate.setState(previousState);
		}));
		addMenuItem(ceateResolutionOption());
		addMenuItem(createShowFpsOption());
	}

	private MenuItem ceateResolutionOption() {
		MenuItem resolutionOption = new MenuItem("Resolution", MenuItem.NO_ACTION);
		resolutionOption.addSubMenuItem(new MenuItem("1024x768", () -> {
			GameSettings.componentWidth = 1024;
			GameSettings.componentHeight = 768;
		}));
		resolutionOption.addSubMenuItem(new MenuItem("1280x720", () -> {
			GameSettings.componentWidth = 1280;
			GameSettings.componentHeight = 720;
		}));
		return resolutionOption;
	}

	private MenuItem createShowFpsOption() {
		MenuItem fpsOption = new MenuItem("Show FPS", MenuItem.NO_ACTION);
		fpsOption.addSubMenuItem(new MenuItem("Yes", () -> GameSettings.showFps = true));
		fpsOption.addSubMenuItem(new MenuItem("No", () -> GameSettings.showFps = false));
		return fpsOption;
	}
}
