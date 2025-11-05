package app.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BattleEngineの命中/ダメージ計算の仕様テスト")
class BattleEngineTest {

    private final BattleEngine engine = new BattleEngine(new Random(1L));

    @Test
    @DisplayName("命中率: 技=0/10 と 相性±/なし、5〜95%にクランプ")
    void testHitPercent() {
        Stats s0 = new Stats(0, 0, 0);
        Stats s10 = new Stats(0, 10, 0);

        assertEquals(70, engine.calcHitPct(s0, Outcome.NONE));
        assertEquals(90, engine.calcHitPct(s10, Outcome.NONE));

        assertEquals(90, engine.calcHitPct(s0, Outcome.PLUS));
        assertEquals(50, engine.calcHitPct(s0, Outcome.MINUS));

        // 技10 + 相性+ で 110 → 95にクランプ
        assertEquals(95, engine.calcHitPct(s10, Outcome.PLUS));
        // 技-100など異常値でも 5にクランプされることを確認
        Stats sNeg = new Stats(0, -100, 0);
        assertEquals(5, engine.calcHitPct(sNeg, Outcome.MINUS));
    }

    @Test
    @DisplayName("ダメージ: 力/守備の極端値と相性補正±5、最小1を保証")
    void testDamage() {
        Stats atk0 = new Stats(0, 0, 0);
        Stats def10 = new Stats(0, 0, 10);
        // 10 + 0*3 - 10*2 = -10 → 最小1
        assertEquals(1, engine.calcDamage(atk0, def10, Outcome.NONE));

        Stats atk10 = new Stats(10, 0, 0);
        Stats def0 = new Stats(0, 0, 0);
        // 基本: 10 + 10*3 = 40
        assertEquals(40, engine.calcDamage(atk10, def0, Outcome.NONE));
        assertEquals(45, engine.calcDamage(atk10, def0, Outcome.PLUS));
        assertEquals(35, engine.calcDamage(atk10, def0, Outcome.MINUS));
    }
}

