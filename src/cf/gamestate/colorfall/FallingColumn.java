package cf.gamestate.colorfall;

import java.util.Random;

import cf.main.ColorFall;
import gt.gameentity.Updatable;
import gt.gameloop.TimeConstants;

public class FallingColumn implements Updatable {
    public static final double LEVEL_ONE_TICK_TIME = TimeConstants.NANOS_PER_SECOND;
    private static final double LEVEL_ONE_HUNDRED_TICK_TIME = LEVEL_ONE_TICK_TIME / 10;

    private static final Random RANDOM = new Random();

    private final DurationTimer tickTimer;

    private int color1;
    private int color2;
    private int color3;

    private int x;
    private int y;

    public static FallingColumn newRandom(int level) {
        return new FallingColumn(randomColor(), randomColor(), randomColor(), GameGrid.WIDTH / 2, 2, level);
    }

    FallingColumn(int color1, int color2, int color3, int x, int y, int level) {
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;

        this.x = x;
        this.y = y;

        double durationPerLerLevel = (LEVEL_ONE_HUNDRED_TICK_TIME - LEVEL_ONE_TICK_TIME) / (100 - 1);

        tickTimer = new DurationTimer(durationPerLerLevel * level - durationPerLerLevel + LEVEL_ONE_TICK_TIME);
    }

    private static int randomColor() {
        return RANDOM.nextInt(ColorFall.PALETTE.length - 1) + 1;
    }

    @Override
    public void update(double dt) {
        tickTimer.update(dt);
    }

    public boolean isTickTimeUp() {
        return tickTimer.getPercentComplete() >= 1;
    }

    public void resetTickTimer() {
        tickTimer.reset();
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void rotate() {
        int temp = color1;
        color1 = color3;
        color3 = color2;
        color2 = temp;
    }

    public boolean maybeMove(GameGrid gameGrid, int dx, int dy) {
        int newX = x + dx;
        int newY = y + dy;
        if (newX < 0 || newX >= GameGrid.WIDTH || newY >= GameGrid.HEIGHT) {
            return false;
        } else if (gameGrid.get(newX, newY) == GameGrid.UNPLAYED) {
            x += dx;
            y += dy;
            return true;
        }
        return false;
    }
}
