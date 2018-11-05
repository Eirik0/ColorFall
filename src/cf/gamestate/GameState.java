package cf.gamestate;

import cf.gameentity.GameEntity;

public interface GameState extends GameEntity {
    void init();

    void keyPressed(int keyCode);
}
