package cf.gamestate.colorfall;

public class BouncingPolygonTrail {
    final double[] xs;
    final double[] ys;

    public BouncingPolygonTrail(double[] xs, double[] ys, double centerX, double centerY) {
        this.xs = new double[xs.length];
        this.ys = new double[ys.length];
        System.arraycopy(xs, 0, this.xs, 0, xs.length);
        System.arraycopy(ys, 0, this.ys, 0, ys.length);
        for (int i = 0; i < xs.length; ++i) {
            this.xs[i] += centerX;
        }
        for (int i = 0; i < ys.length; ++i) {
            this.ys[i] += centerY;
        }
    }

    public static void shiftRight(BouncingPolygonTrail[] trails) {
        System.arraycopy(trails, 0, trails, 1, trails.length - 1);
    }
}
