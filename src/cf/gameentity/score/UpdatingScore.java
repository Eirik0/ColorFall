package cf.gameentity.score;

import gt.gameentity.DurationTimer;
import gt.gameentity.GameEntity;
import gt.gameentity.IGraphics;
import gt.gameloop.TimeConstants;
import gt.util.DoublePair;

public class UpdatingScore implements GameEntity {
    public static final long DURATION = TimeConstants.NANOS_PER_SECOND / 5;

    DurationTimer timer;

    private final int x;
    private final int y;

    private final String prefix;
    private final String pendingSuffix;
    private final String finishedSuffix;

    public UpdatingScore(String name, int oldScore, int newScore, int x, int y) {
        timer = new DurationTimer(DURATION);
        this.x = x;
        this.y = y;

        int[] oldScoreArray = toArray(oldScore);
        int[] newScoreArray = toArray(newScore);

        if (newScoreArray.length != oldScoreArray.length) {
            prefix = name + ": ";
            pendingSuffix = Integer.toString(newScore);
            finishedSuffix = Integer.toString(oldScore);
        } else {
            int firstMismatch = 0;

            for (int i = 0; i < newScoreArray.length; ++i) {
                if (newScoreArray[i] != oldScoreArray[i]) {
                    break;
                }
                ++firstMismatch;
            }

            StringBuilder prefixSb = new StringBuilder(name).append(": ");
            int i = 0;
            for (; i < firstMismatch; ++i) {
                prefixSb.append(newScoreArray[i]);
            }
            prefix = prefixSb.toString();

            StringBuilder pendingSuffixSb = new StringBuilder();
            StringBuilder finishedSuffixSb = new StringBuilder();
            for (; i < newScoreArray.length; ++i) {
                pendingSuffixSb.append(newScoreArray[i]);
                finishedSuffixSb.append(oldScoreArray[i]);
            }
            pendingSuffix = pendingSuffixSb.toString();
            finishedSuffix = finishedSuffixSb.toString();
        }
    }

    @Override
    public void update(double dt) {
        timer.update(dt);
    }

    @Override
    public void drawOn(IGraphics g) {
        double percentComplete = timer.getPercentComplete();
        g.drawString(prefix, x, y);
        DoublePair stringDimensions = g.getStringDimensions(prefix);
        double prefixWidth = stringDimensions.getFirst();
        double prefixHeight = stringDimensions.getSecond() * 1.5;
        g.drawString(pendingSuffix, x + prefixWidth, y + prefixHeight - percentComplete * prefixHeight);
        g.drawString(finishedSuffix, x + prefixWidth, y - percentComplete * prefixHeight);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPendingSuffix() {
        return pendingSuffix;
    }

    public String getFinishedSuffix() {
        return finishedSuffix;
    }

    private static int[] toArray(int score) {
        int tenthPower = 0;
        int n = score;
        while (n > 0) {
            n /= 10;
            ++tenthPower;
        }
        int[] num = new int[tenthPower];
        for (int i = 0; i < tenthPower; ++i) {
            num[tenthPower - i - 1] = score % 10;
            score /= 10;
        }
        return num;
    }
}
