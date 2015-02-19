package gameentity;

import java.awt.Graphics;

public interface GameEntity {
	public void update(long dt);

	public void drawOn(Graphics g);
}
