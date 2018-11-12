package cf.gamestate.gameover;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cf.main.ColorFall;
import gt.io.FileUtilities;

public class HighScores {
    public static final String EL_GRECO_1541 = "El Greco,1541,0,0,0";
    public static final String HOLBEIN_1497 = "Hans Holbein the Younger,1497,0,0,0";
    public static final String RAPHAEL_1483 = "Raffaello Sanzio da Urbino,1483,0,0,0";
    public static final String MICHELANGELO_1475 = "Michelangelo di Lodovico Buonarroti Simoni,1475,0,0,0";
    public static final String CRANACH_1472 = "Lucas Cranach the Elder,1472,0,0,0";
    public static final String LEONARDO_1452 = "Leonardo da Vinci,1452,0,0,0";
    public static final String GHIRLANDAIO_1449 = "Domenico Ghirlandaio,1449,0,0,0";
    public static final String EYCK_1395 = "Jan van Eyck,1395,0,0,0";
    public static final String DONATELLO_1386 = "Donato di Niccolò di Betto Bardi,1386,0,0,0";
    public static final String DUCCIO_1260 = "Duccio di Buoninsegna,1260,0,0,0";

    private final List<HighScore> highScores = new ArrayList<>();

    public static HighScores loadFromFile() {
        return FileUtilities.loadFromFile(ColorFall.HIGH_SCORES_FILE_PATH, fileList -> new HighScores(fileList), () -> new HighScores());
    }

    private HighScores() {
        highScores.add(new HighScore(EL_GRECO_1541));
        highScores.add(new HighScore(HOLBEIN_1497));
        highScores.add(new HighScore(RAPHAEL_1483));
        highScores.add(new HighScore(MICHELANGELO_1475));
        highScores.add(new HighScore(CRANACH_1472));
        highScores.add(new HighScore(LEONARDO_1452));
        highScores.add(new HighScore(GHIRLANDAIO_1449));
        highScores.add(new HighScore(EYCK_1395));
        highScores.add(new HighScore(DONATELLO_1386));
        highScores.add(new HighScore(DUCCIO_1260));
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

    public void saveToFile() throws IOException {
        File directory = new File(ColorFall.BASE_FILE_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(ColorFall.HIGH_SCORES_FILE_PATH);
        FileUtilities.listToFile(file, highScores, highScore -> highScore.toFileString() + System.lineSeparator());
    }

    public int addHighScore(HighScore highScore) {
        int newScore = highScore.score;
        int rank = getRank(newScore);
        highScores.add(rank, highScore);
        return rank;
    }

    private int getRank(int newScore) {
        int rank = 0;
        if (newScore <= highScores.get(0).score) { // Not first place
            for (int i = highScores.size() - 1; i >= 0; --i) {
                if (newScore < highScores.get(i).score) {
                    rank = i + 1;
                    break;
                }
            }
        }
        return rank;
    }
}
