package cf.gamestate.colorfall;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import gt.gameentity.GameEntity;

public class BouncingPolygon implements GameEntity {
    private static final double RADUIS = 50;

    private final ColorFallState parentState;

    double centerX;
    double centerY;

    double velX;
    double velY;

    final int numSides;
    private final double[] xs;
    private final double[] ys;

    private final int pointsToSkip;

    public BouncingPolygon(ColorFallState parentState, int level) {
        this(parentState, RADUIS * 1.5, RADUIS * 1.5, 0.5, 0.5, level);
    }

    public BouncingPolygon nextLevelPolygon() {
        return new BouncingPolygon(parentState, centerX, centerY, velX, velY, numSides + 1);
    }

    private BouncingPolygon(ColorFallState parentState, double centerX, double centerY, double velX, double velY, int numSides) {
        this.parentState = parentState;

        this.centerX = centerX;
        this.centerY = centerY;

        this.velX = velX;
        this.velY = velY;

        this.numSides = numSides;
        xs = new double[numSides];
        ys = new double[numSides];

        setPoints();

        pointsToSkip = getPointsToSkip();

    }

    private void setPoints() {
        double dTheta = 2 * Math.PI / xs.length;

        double theta = 0;
        for (int i = 0; i < xs.length; i++) {
            xs[i] = RADUIS * Math.cos(theta);
            ys[i] = RADUIS * Math.sin(theta);
            theta += dTheta;
        }
    }

    private int getPointsToSkip() {
        if (xs.length == 1) {
            return 1;
        }

        Random random = new Random();

        int pointsToSkip;
        do {
            pointsToSkip = random.nextInt(xs.length - 1) + 1;
        } while (pointsToSkip != 1 && gcd(pointsToSkip, xs.length) != 1);

        return pointsToSkip;
    }

    private static int gcd(int a, int b) {
        int r = a % b;
        while (r != 0) {
            a = b;
            b = r;
            r = a % b;
        }
        return b;
    }

    @Override
    public void update(double dt) {
        centerX += velX;
        if (centerX > parentState.width - RADUIS) {
            centerX = parentState.width - RADUIS;
            velX *= -1;
        } else if (centerX < RADUIS) {
            centerX = RADUIS;
            velX *= -1;
        }
        centerY += velY;
        if (centerY > parentState.height - RADUIS) {
            centerY = parentState.height - RADUIS;
            velY *= -1;
        } else if (centerY < RADUIS) {
            centerY = RADUIS;
            velY *= -1;
        }
    }

    @Override
    public void drawOn(Graphics2D graphics) {
        int index = 0;

        double x0 = xs[0];
        double y0 = ys[0];

        graphics.setColor(Color.RED);
        do {
            index = (index + pointsToSkip) % xs.length;

            double x1 = xs[index];
            double y1 = ys[index];

            graphics.drawLine(round(centerX + x0), round(centerY - y0), round(centerX + x1), round(centerY - y1));

            x0 = x1;
            y0 = y1;
        } while (index != 0);
    }
}
