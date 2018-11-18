package cf.gamestate.gameover;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

public class HighScoresTest {
    private static void assertEqualScores(HighScore expected, HighScore actual) {
        if (!expected.equals(actual)) {
            assertEquals(expected.toString(), actual.toString());
        }
    }

    @Test
    public void testAddScoreFirst() {
        HighScores highScores = new HighScores(Arrays.asList(HighScores.MICHELANGELO_1475, HighScores.LEONARDO_1452));
        highScores.addHighScore(new HighScore(HighScores.RAPHAEL_1483));
        assertEqualScores(new HighScore(HighScores.RAPHAEL_1483), highScores.get(0));
        assertEqualScores(new HighScore(HighScores.MICHELANGELO_1475), highScores.get(1));
        assertEqualScores(new HighScore(HighScores.LEONARDO_1452), highScores.get(2));
    }

    @Test
    public void testAddScoreMiddle1() {
        HighScores highScores = new HighScores(Arrays.asList(HighScores.RAPHAEL_1483, HighScores.DONATELLO_1386));
        highScores.addHighScore(new HighScore(HighScores.MICHELANGELO_1475));
        assertEqualScores(new HighScore(HighScores.RAPHAEL_1483), highScores.get(0));
        assertEqualScores(new HighScore(HighScores.MICHELANGELO_1475), highScores.get(1));
        assertEqualScores(new HighScore(HighScores.DONATELLO_1386), highScores.get(2));
    }

    @Test
    public void testAddScoreMiddle2() {
        HighScores highScores = new HighScores(Arrays.asList(HighScores.RAPHAEL_1483, HighScores.DONATELLO_1386));
        highScores.addHighScore(new HighScore(HighScores.MICHELANGELO_1475));
        highScores.addHighScore(new HighScore(HighScores.LEONARDO_1452));
        assertEqualScores(new HighScore(HighScores.RAPHAEL_1483), highScores.get(0));
        assertEqualScores(new HighScore(HighScores.MICHELANGELO_1475), highScores.get(1));
        assertEqualScores(new HighScore(HighScores.LEONARDO_1452), highScores.get(2));
        assertEqualScores(new HighScore(HighScores.DONATELLO_1386), highScores.get(3));
    }

    @Test
    public void testAddScoreMiddle3() {
        HighScores highScores = new HighScores(Arrays.asList(HighScores.RAPHAEL_1483, HighScores.DONATELLO_1386));
        highScores.addHighScore(new HighScore(HighScores.LEONARDO_1452));
        highScores.addHighScore(new HighScore(HighScores.MICHELANGELO_1475));
        assertEqualScores(new HighScore(HighScores.RAPHAEL_1483), highScores.get(0));
        assertEqualScores(new HighScore(HighScores.MICHELANGELO_1475), highScores.get(1));
        assertEqualScores(new HighScore(HighScores.LEONARDO_1452), highScores.get(2));
        assertEqualScores(new HighScore(HighScores.DONATELLO_1386), highScores.get(3));
    }

    @Test
    public void testAddScoreLast() {
        HighScores highScores = new HighScores(Arrays.asList(HighScores.RAPHAEL_1483, HighScores.LEONARDO_1452));
        highScores.addHighScore(new HighScore(HighScores.DONATELLO_1386));
        assertEqualScores(new HighScore(HighScores.RAPHAEL_1483), highScores.get(0));
        assertEqualScores(new HighScore(HighScores.LEONARDO_1452), highScores.get(1));
        assertEqualScores(new HighScore(HighScores.DONATELLO_1386), highScores.get(2));
    }

    private static long diffDates(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        return getDate(endYear, endMonth, endDay) - getDate(startYear, startMonth, startDay);
    }

    private static long getDate(int startYear, int startMonth, int startDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(startYear, startMonth, startDay);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime().getTime();
    }

    @Test
    public void testHighScoreTimes() {
        long elGreco = diffDates(1541, Calendar.OCTOBER, 1, 1614, Calendar.APRIL, 7); // birth day of month not known
        long holbein = diffDates(1497, Calendar.OCTOBER, 7, 1543, Calendar.NOVEMBER, 29);
        long raphael = diffDates(1483, Calendar.MARCH, 28, 1520, Calendar.APRIL, 6);
        long michelangelo = diffDates(1475, Calendar.MARCH, 6, 1564, Calendar.FEBRUARY, 18);
        long cranach = diffDates(1472, Calendar.JANUARY, 1, 1553, Calendar.OCTOBER, 16); // birth month not known
        long leonardo = diffDates(1452, Calendar.APRIL, 15, 1519, Calendar.MAY, 2);
        long ghirlandaio = diffDates(1448, Calendar.JUNE, 2, 1494, Calendar.JANUARY, 11);
        long eyck = diffDates(1390, Calendar.JANUARY, 1, 1441, Calendar.JULY, 9); // birth year not known
        long donatello = diffDates(1386, Calendar.JANUARY, 1, 1466, Calendar.DECEMBER, 13); // birth month not known
        long duccio = diffDates(1255, Calendar.JANUARY, 1, 1318, Calendar.JANUARY, 1); // birth and death years not known
        assertEquals(elGreco, new HighScore(HighScores.EL_GRECO_1541).time);
        assertEquals(holbein, new HighScore(HighScores.HOLBEIN_1497).time);
        assertEquals(raphael, new HighScore(HighScores.RAPHAEL_1483).time);
        assertEquals(michelangelo, new HighScore(HighScores.MICHELANGELO_1475).time);
        assertEquals(cranach, new HighScore(HighScores.CRANACH_1472).time);
        assertEquals(leonardo, new HighScore(HighScores.LEONARDO_1452).time);
        assertEquals(ghirlandaio, new HighScore(HighScores.GHIRLANDAIO_1448).time);
        assertEquals(eyck, new HighScore(HighScores.EYCK_1390).time);
        assertEquals(donatello, new HighScore(HighScores.DONATELLO_1386).time);
        assertEquals(duccio, new HighScore(HighScores.DUCCIO_1255).time);
    }

    @Test
    public void testFormatTimeYears() {
        assertEquals("1y", HighScore.formatTime(diffDates(1999, Calendar.JANUARY, 1, 2000, Calendar.JANUARY, 1)));
    }

    @Test
    public void testMaxHighScores() {
        HighScores highScores = new HighScores();
        for (int i = 0; i < 1000; ++i) {
            highScores.addHighScore(new HighScore("test", i, i, i, i));
        }
        assertEquals(HighScores.MAX_NUM_SCORES, highScores.size());
    }

    @Test
    public void testAppendHighScore() {
        HighScores highScores = new HighScores();
        int rank = highScores.getRank(100);
        highScores.addHighScore(new HighScore("test", 100, 1, 0, 0));
        assertEquals(rank + 1, highScores.getRank(100));
    }
}
