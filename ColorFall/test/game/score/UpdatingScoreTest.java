package game.score;

import static org.junit.Assert.assertEquals;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.Test;

public class UpdatingScoreTest {
	private final Graphics2D fakeGraphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).createGraphics();

	@Test
	public void test_1_1() {
		UpdatingScore updatingScore = new UpdatingScore("Test", 1, 2, 0, 0);
		assertEquals("Test: ", updatingScore.getPrefix());
		assertEquals("2", updatingScore.getPendingSuffix());
		assertEquals("1", updatingScore.getFinishedSuffix());
	}

	@Test
	public void testCarryOver() {
		UpdatingScore updatingScore = new UpdatingScore("Test", 9, 10, 0, 0);
		assertEquals("Test: ", updatingScore.getPrefix());
		assertEquals("10", updatingScore.getPendingSuffix());
		assertEquals("9", updatingScore.getFinishedSuffix());
	}

	@Test
	public void test_1_2() {
		UpdatingScore updatingScore = new UpdatingScore("Test", 21, 22, 0, 0);
		assertEquals("Test: 2", updatingScore.getPrefix());
		assertEquals("2", updatingScore.getPendingSuffix());
		assertEquals("1", updatingScore.getFinishedSuffix());
	}

	@Test
	public void test_2_2() {
		UpdatingScore updatingScore = new UpdatingScore("Test", 29, 30, 0, 0);
		assertEquals("Test: ", updatingScore.getPrefix());
		assertEquals("30", updatingScore.getPendingSuffix());
		assertEquals("29", updatingScore.getFinishedSuffix());
	}

	@Test
	public void test_1_3() {
		UpdatingScore updatingScore = new UpdatingScore("Test", 101, 102, 0, 0);
		assertEquals("Test: 10", updatingScore.getPrefix());
		assertEquals("2", updatingScore.getPendingSuffix());
		assertEquals("1", updatingScore.getFinishedSuffix());
	}

	@Test
	public void test_2_3() {
		UpdatingScore updatingScore = new UpdatingScore("Test", 109, 110, 0, 0);
		assertEquals("Test: 1", updatingScore.getPrefix());
		assertEquals("10", updatingScore.getPendingSuffix());
		assertEquals("09", updatingScore.getFinishedSuffix());
	}
}
