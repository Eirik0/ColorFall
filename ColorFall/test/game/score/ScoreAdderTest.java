package game.score;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ScoreAdderTest {
	@Test
	public void testAdd() {
		ScoreAdder adder = new ScoreAdder("Test", 0, 0);
		adder.add(10, 3);

		assertEquals(0, adder.getValue());
		assertEquals(3, adder.getQueueSize());
	}

	@Test
	public void testUpdate() {
		ScoreAdder adder = new ScoreAdder("Test", 0, 0);
		adder.add(10, 3);

		adder.update(0);
		assertEquals(2, adder.getQueueSize());

		adder.update(UpdatingScore.DURATION);
		assertEquals(10, adder.getValue());
		assertEquals(1, adder.getQueueSize());

		adder.update(UpdatingScore.DURATION);
		assertEquals(20, adder.getValue());
		assertEquals(0, adder.getQueueSize());

		adder.update(UpdatingScore.DURATION);
		assertEquals(30, adder.getValue());
		assertEquals(0, adder.getQueueSize());
	}

	@Test
	public void testLaggyUpdate() {
		ScoreAdder adder = new ScoreAdder("Test", 0, 0);
		adder.add(10, 3);
		
		adder.update(0);
		adder.update(UpdatingScore.DURATION + UpdatingScore.DURATION + UpdatingScore.DURATION);
		assertEquals(30, adder.getValue());
		assertEquals(0, adder.getQueueSize());
	}

	@Test
	public void testEmptyUpdate() {
		ScoreAdder adder = new ScoreAdder("Test", 0, 0);
		adder.update(UpdatingScore.DURATION);
	}
}
