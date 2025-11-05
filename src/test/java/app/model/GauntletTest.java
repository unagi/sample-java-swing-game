package app.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Gauntlet（5人抜き進行）の仕様テスト")
class GauntletTest {

    @Test
    @DisplayName("敵は5体生成され、各ラウンドの合計ポイントが仕様値を満たす")
    void testEnemiesAndTotals() {
        Gauntlet g = new Gauntlet(new Random(123));
        int[] expected = {12, 13, 14, 15, 16};
        for (int i = 0; i < expected.length; i++) {
            Battler e = g.nextEnemy();
            assertNotNull(e, "null at index " + i);
            int sum = e.stats.power + e.stats.skill + e.stats.guard;
            assertEquals(expected[i], sum, "sum mismatch at index " + i);
            assertTrue(0 <= e.stats.power && e.stats.power <= 10);
            assertTrue(0 <= e.stats.skill && e.stats.skill <= 10);
            assertTrue(0 <= e.stats.guard && e.stats.guard <= 10);
        }
        assertNull(g.nextEnemy(), "6体目は存在しない");
    }
}

