package cf.gamestate.gameover;

import java.util.Arrays;

import org.junit.Test;

public class HighScoresTest {
    @Test
    public void testAddScoreFirst() {
        HighScores highScores = new HighScores(Arrays.asList(HighScores.MICHELANGELO_1475, HighScores.LEONARDO_1452));
        highScores.addHighScore(new HighScore(HighScores.RAPHAEL_1483));
        assertEquals(new HighScore(HighScores.RAPHAEL_1483), highScores.get(0));
        assertEquals(new HighScore(HighScores.MICHELANGELO_1475), highScores.get(1));
        assertEquals(new HighScore(HighScores.LEONARDO_1452), highScores.get(2));
    }

    @Test
    public void testAddScoreMiddle1() {
        HighScores highScores = new HighScores(Arrays.asList(HighScores.RAPHAEL_1483, HighScores.DONATELLO_1386));
        highScores.addHighScore(new HighScore(HighScores.MICHELANGELO_1475));
        assertEquals(new HighScore(HighScores.RAPHAEL_1483), highScores.get(0));
        assertEquals(new HighScore(HighScores.MICHELANGELO_1475), highScores.get(1));
        assertEquals(new HighScore(HighScores.DONATELLO_1386), highScores.get(2));
    }

    @Test
    public void testAddScoreMiddle2() {
        HighScores highScores = new HighScores(Arrays.asList(HighScores.RAPHAEL_1483, HighScores.DONATELLO_1386));
        highScores.addHighScore(new HighScore(HighScores.MICHELANGELO_1475));
        highScores.addHighScore(new HighScore(HighScores.LEONARDO_1452));
        assertEquals(new HighScore(HighScores.RAPHAEL_1483), highScores.get(0));
        assertEquals(new HighScore(HighScores.MICHELANGELO_1475), highScores.get(1));
        assertEquals(new HighScore(HighScores.LEONARDO_1452), highScores.get(2));
        assertEquals(new HighScore(HighScores.DONATELLO_1386), highScores.get(3));
    }

    @Test
    public void testAddScoreMiddle3() {
        HighScores highScores = new HighScores(Arrays.asList(HighScores.RAPHAEL_1483, HighScores.DONATELLO_1386));
        highScores.addHighScore(new HighScore(HighScores.LEONARDO_1452));
        highScores.addHighScore(new HighScore(HighScores.MICHELANGELO_1475));
        assertEquals(new HighScore(HighScores.RAPHAEL_1483), highScores.get(0));
        assertEquals(new HighScore(HighScores.MICHELANGELO_1475), highScores.get(1));
        assertEquals(new HighScore(HighScores.LEONARDO_1452), highScores.get(2));
        assertEquals(new HighScore(HighScores.DONATELLO_1386), highScores.get(3));
    }

    @Test
    public void testAddScoreLast() {
        HighScores highScores = new HighScores(Arrays.asList(HighScores.RAPHAEL_1483, HighScores.LEONARDO_1452));
        highScores.addHighScore(new HighScore(HighScores.DONATELLO_1386));
        assertEquals(new HighScore(HighScores.RAPHAEL_1483), highScores.get(0));
        assertEquals(new HighScore(HighScores.LEONARDO_1452), highScores.get(1));
        assertEquals(new HighScore(HighScores.DONATELLO_1386), highScores.get(2));
    }

    private static void assertEquals(HighScore expected, HighScore actual) {
        if (!expected.equals(actual)) {
            org.junit.Assert.assertEquals(expected.toString(), actual.toString());
        }
    }
}
