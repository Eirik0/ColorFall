package gameentity.update;

import gameentity.FixedDurationEntity;

public abstract class GridUpdateEntity extends FixedDurationEntity {
	public GridUpdateEntity(long duration) {
		super(duration);
	}

	public abstract int[][] getGrid();
}
