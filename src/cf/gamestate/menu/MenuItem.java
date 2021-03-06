package cf.gamestate.menu;

public class MenuItem {
    public static final Runnable NO_ACTION = () -> {
    };

    public final String itemName;
    public final Runnable itemAction;

    public final MenuItemList subMenu;

    public MenuItem(String itemName, Runnable itemAction) {
        this.itemName = itemName;
        this.itemAction = itemAction;

        subMenu = new MenuItemList();
    }

    public MenuItem addSubMenuItem(String itemName, Runnable itemAction) {
        subMenu.add(new MenuItem(itemName, itemAction));
        return this;
    }
}
