package cf.gameentity.update;

import cf.gameentity.FixedDurationEntity;

public abstract class GridUpdateEntity extends FixedDurationEntity {
    public GridUpdateEntity(long duration) {
        super(duration);
    }

    public abstract int[][] getGrid();
}
