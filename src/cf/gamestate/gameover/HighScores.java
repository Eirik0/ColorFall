package cf.gamestate.gameover;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cf.main.ColorFall;
import gt.io.FileUtilities;

public class HighScores {
    public static final int MAX_NUM_SCORES = 25;

    public static final String EL_GRECO_1541 = "El Greco,1541,10,0,2287526400000";
    public static final String HOLBEIN_1497 = "Hans Holbein the Younger,1497,10,7,1456185600000";
    public static final String RAPHAEL_1483 = "Raffaello Sanzio da Urbino,1483,3,28,1168473600000";
    public static final String MICHELANGELO_1475 = "Michelangelo di Lodovico Buonarroti Simoni,1475,3,28,2807222400000";
    public static final String CRANACH_1472 = "Lucas Cranach the Elder,1472,0,0,2581113600000";
    public static final String LEONARDO_1452 = "Leonardo da Vinci,1452,4,15,2115763200000";
    public static final String GHIRLANDAIO_1448 = "Domenico Ghirlandaio,1448,6,2,1439337600000";
    public static final String EYCK_1390 = "Jan van Eyck,1390,0,0,1625788800000";
    public static final String DONATELLO_1386 = "Donato di Niccolo di Betto Bardi,1386,0,0,2554502400000";
    public static final String DUCCIO_1255 = "Duccio di Buoninsegna,1255,0,0,1988150400000";

    private final List<HighScore> highScores = new ArrayList<>();

    public static HighScores loadFromFile() {
        return FileUtilities.loadFromFile(ColorFall.HIGH_SCORES_FILE_PATH, fileList -> new HighScores(fileList), () -> new HighScores());
    }

    public HighScores() {
        highScores.add(new HighScore(EL_GRECO_1541));
        highScores.add(new HighScore(HOLBEIN_1497));
        highScores.add(new HighScore(RAPHAEL_1483));
        highScores.add(new HighScore(MICHELANGELO_1475));
        highScores.add(new HighScore(CRANACH_1472));
        highScores.add(new HighScore(LEONARDO_1452));
        highScores.add(new HighScore(GHIRLANDAIO_1448));
        highScores.add(new HighScore(EYCK_1390));
        highScores.add(new HighScore(DONATELLO_1386));
        highScores.add(new HighScore(DUCCIO_1255));
    }

    HighScores(List<String> stringList) {
        for (String s : stringList) {
            highScores.add(new HighScore(s));
        }
    }

    public int size() {
        return highScores.size();
    }

    public HighScore get(int i) {
        return highScores.get(i);
    }

    public void saveToFile() {
        ColorFall.ensureColorFallDirectoryExists();
        FileUtilities.collectionToFile(new File(ColorFall.HIGH_SCORES_FILE_PATH), highScores,
                highScore -> highScore.toFileString() + System.lineSeparator());
    }

    public int addHighScore(HighScore highScore) {
        long newScore = highScore.score;
        int rank = getRank(newScore);
        highScores.add(rank, highScore);
        if (highScores.size() > MAX_NUM_SCORES) {
            highScores.remove(MAX_NUM_SCORES);
        }
        return rank;
    }

    public int getRank(long newScore) {
        int rank = 0;
        if (newScore <= highScores.get(0).score) { // Not first place
            for (int i = highScores.size() - 1; i >= 0; --i) {
                if (newScore <= highScores.get(i).score) {
                    rank = i + 1;
                    break;
                }
            }
        }
        return rank;
    }
}
