package cf.gameentity.score;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GameScoreTest {
    @Test
    public void testLevelDontUp() {
        GameScore score = new GameScore(0, 1, 0);
        score.addToScore(49, 0);
        assertEquals(1, score.getLevel());
    }

    @Test
    public void testLevelUp() {
        GameScore score = new GameScore(0, 1, 0);
        score.addToScore(50, 0);
        assertEquals(2, score.getLevel());
    }

    @Test
    public void testLevelUpStartingLevelTwo() {
        GameScore score = new GameScore(0, 2, 0);
        score.addToScore(50, 0);
        assertEquals(3, score.getLevel());
    }
}
