package cf.gameentity;

import java.awt.Graphics;

public interface GameEntity {
    void update(long dt);

    void drawOn(Graphics g);
}
