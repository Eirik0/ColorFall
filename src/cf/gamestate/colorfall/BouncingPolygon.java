package cf.gamestate.colorfall;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import gt.gameentity.GameEntity;
import gt.gameloop.TimeConstants;

public class BouncingPolygon implements GameEntity {
    private static final double RADUIS = 50;
    private static final double TRAIL_TIME = TimeConstants.NANOS_PER_SECOND / 2;
    private static final int TRAIL_LENGTH = 25;

    private final ColorFallState parentState;
    private final DurationTimer timer;

    private BouncingPolygonTrail[] trails;

    double centerX;
    double centerY;
    double theta;

    double dx;
    double dy;
    double dTheta;

    final int numSides;
    private final double[] xs;
    private final double[] ys;

    private final int pointsToSkip;

    public BouncingPolygon(ColorFallState parentState, int level) {
        this(parentState, RADUIS * 1.5, RADUIS * 1.5, 0, 0.5, 0.5, Math.PI / 360, level);
    }

    public BouncingPolygon nextLevelPolygon() {
        return new BouncingPolygon(parentState, centerX, centerY, theta, dx, dy, dTheta, numSides + 1);
    }

    private BouncingPolygon(ColorFallState parentState, double x, double y, double theta, double dx, double dy, double dTheta, int numSides) {
        this.parentState = parentState;
        timer = new DurationTimer(TRAIL_TIME);

        centerX = x;
        centerY = y;
        this.theta = theta;

        this.dx = dx;
        this.dy = dy;
        this.dTheta = dTheta;

        this.numSides = numSides;
        xs = new double[numSides];
        ys = new double[numSides];

        setPoints(xs, ys, theta, numSides);

        pointsToSkip = getPointsToSkip(numSides);

        trails = new BouncingPolygonTrail[TRAIL_LENGTH];
    }

    private static void setPoints(double[] xs, double[] ys, double theta, int numSides) {
        double dTheta = 2 * Math.PI / numSides;

        for (int i = 0; i < numSides; i++) {
            xs[i] = RADUIS * Math.cos(theta);
            ys[i] = RADUIS * Math.sin(theta);
            theta += dTheta;
        }
    }

    private static int getPointsToSkip(int numSides) {
        if (numSides == 1) {
            return 1;
        }

        Random random = new Random();

        int pointsToSkip;
        do {
            pointsToSkip = random.nextInt(numSides - 1) + 1;
        } while (pointsToSkip != 1 && gcd(pointsToSkip, numSides) != 1);

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
        timer.update(dt);
        if (timer.getPercentComplete() >= 1) {
            BouncingPolygonTrail.shiftRight(trails);
            trails[0] = new BouncingPolygonTrail(xs, ys, centerX, centerY);
            timer.reset();
        }

        centerX += dx;
        if (centerX > parentState.width - RADUIS) {
            centerX = parentState.width - RADUIS;
            dx *= -1;
            dTheta *= -1;
        } else if (centerX < RADUIS) {
            centerX = RADUIS;
            dx *= -1;
            dTheta *= -1;
        }

        centerY += dy;
        if (centerY > parentState.height - RADUIS) {
            centerY = parentState.height - RADUIS;
            dy *= -1;
            dTheta *= -1;
        } else if (centerY < RADUIS) {
            centerY = RADUIS;
            dy *= -1;
            dTheta *= -1;
        }

        theta += dTheta;
        setPoints(xs, ys, theta, numSides);
    }

    @Override
    public void drawOn(Graphics2D graphics) {
        BouncingPolygonTrail trailStart = new BouncingPolygonTrail(xs, ys, centerX, centerY);
        double[] xs0 = trailStart.xs;
        double[] ys0 = trailStart.ys;
        int i = 0;
        while (i < TRAIL_LENGTH && trails[i] != null) {
            graphics.setColor(fadeToColor(Color.RED, Color.BLACK, (double) i / TRAIL_LENGTH));
            double[] xs1 = trails[i].xs;
            double[] ys1 = trails[i].ys;
            for (int j = 0; j < xs0.length; ++j) {
                graphics.drawLine(round(xs0[j]), round(ys0[j]), round(xs1[j]), round(ys1[j]));
            }

            xs0 = xs1;
            ys0 = ys1;

            ++i;
        }

        double x0 = xs[0];
        double y0 = ys[0];
        graphics.setColor(Color.RED);
        int index = 0;
        do {
            index = (index + pointsToSkip) % xs.length;

            double x1 = xs[index];
            double y1 = ys[index];

            graphics.drawLine(round(centerX + x0), round(centerY + y0), round(centerX + x1), round(centerY + y1));

            x0 = x1;
            y0 = y1;
        } while (index != 0);
    }
}
