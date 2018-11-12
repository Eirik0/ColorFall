package cf.gamestate.menu;

import java.awt.Color;
import java.awt.Graphics2D;

import cf.main.ColorFall;
import gt.gamestate.GameState;
import gt.gamestate.UserInput;

public class MenuState implements GameState {
    private static final int PIXELS_BETWEEN_ITEMS = 100;
    private static final int PIXELS_TO_SUBMENU = 30;

    private final MenuItemList menuItems = new MenuItemList();

    public MenuState() {
    }

    public MenuState addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
        return this;
    }

    @Override
    public void update(double dt) {
        ColorFall.getInstance().menuBackground.update(dt);
    }

    @Override
    public void drawOn(Graphics2D graphics) {
        ColorFall.getInstance().menuBackground.drawOn(graphics);

        graphics.setFont(ColorFall.GAME_FONT);

        for (int i = 0; i < menuItems.size(); ++i) {
            if (i == menuItems.getSelectionIndex()) {
                graphics.setColor(Color.GREEN);
            } else {
                graphics.setColor(Color.RED);
            }
            MenuItem item = menuItems.getItem(i);
            int cordinate = (i + 1) * PIXELS_BETWEEN_ITEMS;
            graphics.drawString(item.itemName, cordinate, cordinate);
            if (item.subMenu.size() > 0) {
                int subMenuCoordiante = cordinate + PIXELS_TO_SUBMENU;
                graphics.drawString(item.subMenu.getSelectedItem().itemName, subMenuCoordiante, subMenuCoordiante);
            }
        }
    }

    @Override
    public void setSize(int width, int height) {
        ColorFall.getInstance().menuBackground.setSize(width, height);
    }

    @Override
    public void handleUserInput(UserInput input) {
        switch (input) {
        case UP_KEY_PRESSED:
            menuItems.selectPrevious();
            break;
        case DOWN_KEY_PRESSED:
            menuItems.selectNext();
            break;
        case LEFT_KEY_PRESSED:
            menuItems.getSelectedItem().subMenu.selectPrevious();
            break;
        case RIGHT_KEY_PRESSED:
            menuItems.getSelectedItem().subMenu.selectNext();
            break;
        case ENTER_KEY_PRESSED:
            menuItems.getSelectedItem().itemAction.run();
            break;
        }
    }
}
