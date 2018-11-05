package gamestate.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuItemList {
	private final List<MenuItem> menuItems;
	private int selectionIndex;

	public MenuItemList() {
		menuItems = new ArrayList<>();
	}

	public void add(MenuItem menuItem) {
		menuItems.add(menuItem);
	}

	public int size() {
		return menuItems.size();
	}

	public void setSelectedIndex(int selectedAction) {
		selectionIndex = selectedAction;
	}

	public int getSelectionIndex() {
		return selectionIndex;
	}

	public void selectPrevious() {
		--selectionIndex;
		if (selectionIndex == -1) {
			selectionIndex = menuItems.size() - 1;
		}
	}

	public void selectNext() {
		++selectionIndex;
		if (selectionIndex == menuItems.size()) {
			selectionIndex = 0;
		}
	}

	public MenuItem getItem(int index) {
		return menuItems.get(index);
	}

	public MenuItem getSelectedItem() {
		return menuItems.get(selectionIndex);
	}

}
