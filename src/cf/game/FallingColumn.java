package cf.game;

import java.awt.Graphics;
import java.util.Random;

import cf.gameentity.GameEntity;
import cf.gamestate.colorfall.GameSizer;
import cf.util.DrawingUtilities;
import cf.util.GameConstants;

public class FallingColumn implements GameEntity {
    private static final Random RANDOM = new Random();

    private final GameGrid gameGrid;

    private final long duration;

    private int color1;
    private int color2;
    private int color3;

    private int x;
    private int y;

    private long internalTimer = 0;
    private boolean wasPlaced = false;

    public static FallingColumn newRandom(GameGrid gameGrid, int level) {
        long duration = (long) (Math.max(1.0525 - 0.0525 * level, 0.05) * GameConstants.ONE_SECOND);
        return new FallingColumn(gameGrid, duration, randomColor(), randomColor(), randomColor(), GameGrid.WIDTH / 2, 2);
    }

    FallingColumn(GameGrid gameGrid, int color1, int color2, int color3) {
        this(gameGrid, GameConstants.ONE_SECOND, color1, color2, color3, 0, 0);
    }

    private FallingColumn(GameGrid gameGrid, long duration, int color1, int color2, int color3, int x, int y) {
        this.gameGrid = gameGrid;

        this.duration = duration;

        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;

        this.x = x;
        this.y = y;
    }

    private static int randomColor() {
        return RANDOM.nextInt(DrawingUtilities.PALETTE.length - 1) + 1;
    }

    @Override
    public void update(long dt) {
        internalTimer += dt;
        if (internalTimer >= duration) {
            wasPlaced = !maybeMoveDown();
        }
    }

    public boolean wasPlaced() {
        return wasPlaced;
    }

    @Override
    public void drawOn(Graphics g) {
        DrawingUtilities.drawCell(g, x, y, color1, GameSizer.getOffsetX(), GameSizer.getOffsetY());
        DrawingUtilities.drawCell(g, x, y - 1, color2, GameSizer.getOffsetX(), GameSizer.getOffsetY());
        DrawingUtilities.drawCell(g, x, y - 2, color3, GameSizer.getOffsetX(), GameSizer.getOffsetY());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor1() {
        return color1;
    }

    public int getColor2() {
        return color2;
    }

    public int getColor3() {
        return color3;
    }

    public void rotate() {
        int temp = color1;
        color1 = color3;
        color3 = color2;
        color2 = temp;
    }

    public boolean maybeMoveLeft() {
        return maybeMove(-1, 0);
    }

    public boolean maybeMoveRight() {
        return maybeMove(1, 0);
    }

    public boolean maybeMoveDown() {
        internalTimer = 0;
        return maybeMove(0, 1);
    }

    private boolean maybeMove(int dx, int dy) {
        if (x + dx < 0 || x + dx >= GameGrid.WIDTH || y + dy >= GameGrid.HEIGHT) {
            return false;
        } else if (gameGrid.get(x + dx, y + dy) == GameGrid.UNPLAYED) {
            x += dx;
            y += dy;
            return true;
        }
        return false;
    }
}
