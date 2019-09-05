package cf.gamestate.menu;

import java.awt.Color;

import cf.main.ColorFall;
import gt.component.ComponentCreator;
import gt.gameentity.IGraphics;
import gt.gamestate.GameState;
import gt.gamestate.UserInput;

public class MenuState implements GameState {
    private static final int PIXELS_BETWEEN_ITEMS = 100;
    private static final int PIXELS_TO_SUBMENU = 30;

    private final MenuItemList menuItems = new MenuItemList();

    double width;
    double height;

    public MenuState addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
        return this;
    }

    @Override
    public void update(double dt) {
        ColorFall.getMenuBackground().update(dt);
    }

    @Override
    public void drawOn(IGraphics g) {
        drawOn(g, true);
    }

    public void drawOn(IGraphics g, boolean drawBackground) {
        if (drawBackground) {
            g.fillRect(0, 0, width, height, ComponentCreator.backgroundColor());
            ColorFall.getMenuBackground().drawOn(g);
        }

        for (int i = 0; i < menuItems.size(); ++i) {
            MenuItem item = menuItems.getItem(i);
            int cordinate = (i + 1) * PIXELS_BETWEEN_ITEMS;
            g.setColor(i == menuItems.getSelectionIndex() ? Color.GREEN : Color.RED);
            g.setFont(ColorFall.GAME_FONT_LARGE);
            g.drawString(item.itemName, cordinate, cordinate);
            if (item.subMenu.size() > 0) {
                g.setFont(ColorFall.GAME_FONT_SMALL);
                int subMenuCoordiante = cordinate + PIXELS_TO_SUBMENU;
                g.drawString(item.subMenu.getSelectedItem().itemName, subMenuCoordiante, subMenuCoordiante);
            }
        }
    }

    @Override
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
        ColorFall.getMenuBackground().setSize(width, height);
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
            MenuItemList subMenu = menuItems.getSelectedItem().subMenu;
            if (subMenu.size() > 0) {
                subMenu.getSelectedItem().itemAction.run();
            }
            menuItems.getSelectedItem().itemAction.run();
            break;
        case MINUS_KEY_PRESSED:
            ColorFall.getMenuBackground().decrementN();
            break;
        case EQUALS_KEY_PRESSED:
            ColorFall.getMenuBackground().incrementN();
            break;
        case R_KEY_PRESSED:
            ColorFall.getMenuBackground().setRandomColors();
            break;
        }
    }
}
