package main;

import java.util.Arrays;

import main.HighScores.HighScore;

import org.junit.Before;
import org.junit.Test;

public class HighScoresTest {
	@Before
	public void clearHighScores() {
		HighScores.clear();
	}

	@Test
	public void testAddScoreFirst() {
		HighScores.init(Arrays.asList(HighScores.MICHELANGELO_1475, HighScores.LEONARDO_1452));
		HighScores.addHighScore(new HighScore(HighScores.RAPHAEL_1483));
		assertEquals(new HighScore(HighScores.RAPHAEL_1483), HighScores.get(0));
		assertEquals(new HighScore(HighScores.MICHELANGELO_1475), HighScores.get(1));
		assertEquals(new HighScore(HighScores.LEONARDO_1452), HighScores.get(2));
	}

	@Test
	public void testAddScoreMiddle1() {
		HighScores.init(Arrays.asList(HighScores.RAPHAEL_1483, HighScores.DONATELLO_1386));
		HighScores.addHighScore(new HighScore(HighScores.MICHELANGELO_1475));
		assertEquals(new HighScore(HighScores.RAPHAEL_1483), HighScores.get(0));
		assertEquals(new HighScore(HighScores.MICHELANGELO_1475), HighScores.get(1));
		assertEquals(new HighScore(HighScores.DONATELLO_1386), HighScores.get(2));
	}

	@Test
	public void testAddScoreMiddle2() {
		HighScores.init(Arrays.asList(HighScores.RAPHAEL_1483, HighScores.DONATELLO_1386));
		HighScores.addHighScore(new HighScore(HighScores.MICHELANGELO_1475));
		HighScores.addHighScore(new HighScore(HighScores.LEONARDO_1452));
		assertEquals(new HighScore(HighScores.RAPHAEL_1483), HighScores.get(0));
		assertEquals(new HighScore(HighScores.MICHELANGELO_1475), HighScores.get(1));
		assertEquals(new HighScore(HighScores.LEONARDO_1452), HighScores.get(2));
		assertEquals(new HighScore(HighScores.DONATELLO_1386), HighScores.get(3));
	}

	@Test
	public void testAddScoreMiddle3() {
		HighScores.init(Arrays.asList(HighScores.RAPHAEL_1483, HighScores.DONATELLO_1386));
		HighScores.addHighScore(new HighScore(HighScores.LEONARDO_1452));
		HighScores.addHighScore(new HighScore(HighScores.MICHELANGELO_1475));
		assertEquals(new HighScore(HighScores.RAPHAEL_1483), HighScores.get(0));
		assertEquals(new HighScore(HighScores.MICHELANGELO_1475), HighScores.get(1));
		assertEquals(new HighScore(HighScores.LEONARDO_1452), HighScores.get(2));
		assertEquals(new HighScore(HighScores.DONATELLO_1386), HighScores.get(3));
	}

	@Test
	public void testAddScoreLast() {
		HighScores.init(Arrays.asList(HighScores.RAPHAEL_1483, HighScores.LEONARDO_1452));
		HighScores.addHighScore(new HighScore(HighScores.DONATELLO_1386));
		assertEquals(new HighScore(HighScores.RAPHAEL_1483), HighScores.get(0));
		assertEquals(new HighScore(HighScores.LEONARDO_1452), HighScores.get(1));
		assertEquals(new HighScore(HighScores.DONATELLO_1386), HighScores.get(2));
	}

	private static void assertEquals(HighScore expected, HighScore actual) {
		if (!expected.equals(actual)) {
			org.junit.Assert.assertEquals(expected.toString(), actual.toString());
		}
	}
}
