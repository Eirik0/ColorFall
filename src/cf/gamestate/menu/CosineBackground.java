package cf.gamestate.menu;

import java.awt.Color;
import java.util.Random;

import gt.component.ComponentCreator;
import gt.gameentity.GameEntity;
import gt.gameentity.GridSizer;
import gt.gameentity.IGraphics;
import gt.gameentity.Sizable;
import gt.gameloop.TimeConstants;

public class CosineBackground implements GameEntity, Sizable {
    private static final double SECONDS_PER_N = 50 * TimeConstants.NANOS_PER_SECOND;
    private static final double D_THETA = Math.PI / 1440;

    private Color color1;
    private Color color2;

    private double n = 0;

    private GridSizer sizer = new GridSizer(ComponentCreator.DEFAULT_WIDTH, ComponentCreator.DEFAULT_HEIGHT, 1, 1);

    public CosineBackground() {
        setRandomColors();
    }

    public void setRandomColors() {
        Random random = new Random();
        color1 = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        color2 = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public void incrementN() {
        n += 0.25;
    }

    public void decrementN() {
        n -= 0.25;
    }

    @Override
    public void update(double dt) {
        n += dt / SECONDS_PER_N;
    }

    @Override
    public void drawOn(IGraphics g) {
        double theta = 0;
        int i = 0;

        while (theta < 2 * Math.PI) {
            if (i % 5 == 0) {
                g.setColor(color1);
            } else {
                g.setColor(color2);
            }

            double r = sizer.cellSize / 2 * Math.sin(n * theta);

            double x = r * Math.cos(theta) + sizer.getCenterX(0);
            double y = sizer.getCenterY(0) - r * Math.sin(theta);
            g.drawPixel(x, y);

            theta += D_THETA;
            ++i;
        }
    }

    @Override
    public void setSize(double width, double height) {
        sizer = new GridSizer(width, height, 1, 1);
    }
}
