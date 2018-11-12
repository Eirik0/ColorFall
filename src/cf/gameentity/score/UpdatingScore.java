package cf.gameentity.score;

import java.awt.Graphics2D;

import cf.gamestate.colorfall.DurationTimer;
import gt.gameentity.GameEntity;
import gt.gameloop.TimeConstants;

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
    public void drawOn(Graphics2D graphics) {
        double percentComplete = timer.getPercentComplete();
        graphics.drawString(prefix, x, y);
        int prefixWidth = graphics.getFontMetrics().stringWidth(prefix);
        int prefixHeight = graphics.getFontMetrics().getHeight();
        graphics.drawString(pendingSuffix, x + prefixWidth, round(y + prefixHeight - percentComplete * prefixHeight));
        graphics.drawString(finishedSuffix, x + prefixWidth, round(y - percentComplete * prefixHeight));
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
