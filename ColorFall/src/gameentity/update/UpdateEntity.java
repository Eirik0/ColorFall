package gameentity.update;

import gameentity.GameEntity;

public interface UpdateEntity extends GameEntity {
	public int[][] getGrid();

	public boolean updateFinished();
}
