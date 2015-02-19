package gamestate.menu;

import gamestate.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import main.GameSettings;

public class MenuState implements GameState {
	protected final MenuItemList menuItems;

	public MenuState() {
		menuItems = new MenuItemList();
	}

	public void addMenuItem(MenuItem menuItem) {
		menuItems.add(menuItem);
	}

	@Override
	public void init() {
	}

	@Override
	public void update(long dt) {
	}

	@Override
	public void drawOn(Graphics g) {
		drawBackground(g);
		int distance = 100;
		for (int i = 0; i < menuItems.size(); ++i) {
			if (i == menuItems.getSelectionIndex()) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.RED);
			}
			MenuItem item = menuItems.getItem(i);
			g.drawString(item.itemName, (i + 1) * distance, (i + 1) * distance);
			if (item.subMenu.size() > 0) {
				g.drawString(item.subMenu.getSelectedItem().itemName, (i + 1) * distance + 30, (i + 1) * distance + 30);
			}
		}
	}

	private void drawBackground(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameSettings.componentWidth, GameSettings.componentHeight);
	}

	@Override
	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_UP:
			menuItems.selectPrevious();
			break;
		case KeyEvent.VK_DOWN:
			menuItems.selectNext();
			break;
		case KeyEvent.VK_LEFT:
			menuItems.getSelectedItem().subMenu.selectPrevious();
			break;
		case KeyEvent.VK_RIGHT:
			menuItems.getSelectedItem().subMenu.selectNext();
			break;
		case KeyEvent.VK_ENTER:
			menuItems.getSelectedItem().itemAction.run();
			break;
		}
	}
}
