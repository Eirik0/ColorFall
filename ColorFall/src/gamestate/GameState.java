package gamestate;

import gameentity.GameEntity;

public interface GameState extends GameEntity {
	public void init();

	public void keyPressed(int keyCode);
}
