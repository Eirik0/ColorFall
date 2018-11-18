package cf.gameentity.score;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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

        assertEquals(3, adder.getQueueSize());
        assertEquals(0, adder.getValue());

        adder.update(UpdatingScore.DURATION / 2);
        assertEquals(2, adder.getQueueSize());
        adder.update(UpdatingScore.DURATION / 2);
        assertEquals(10, adder.getValue());

        adder.update(UpdatingScore.DURATION / 2);
        assertEquals(1, adder.getQueueSize());
        adder.update(UpdatingScore.DURATION / 2);
        assertEquals(20, adder.getValue());

        adder.update(UpdatingScore.DURATION / 2);
        assertEquals(0, adder.getQueueSize());
        adder.update(UpdatingScore.DURATION / 2);
        assertEquals(30, adder.getValue());
    }

    @Test
    public void testEmptyUpdate() {
        ScoreAdder adder = new ScoreAdder("Test", 0, 0);
        adder.update(UpdatingScore.DURATION);
    }
}
