package app.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Moveの相性（3すくみ）の仕様テスト")
class MoveTest {

    @Test
    @DisplayName("3×3の全組み合わせでOutcomeが期待通りになる")
    void testOutcomeMatrix() {
        for (Move a : Move.values()) {
            for (Move b : Move.values()) {
                Outcome o = a.outcomeAgainst(b);
                if (a == b) {
                    assertEquals(Outcome.NONE, o, () -> a + " vs " + b);
                } else {
                    // 仕様: 斬>打、打>突、突>斬
                    Outcome expected;
                    switch (a) {
                        case ZAN: expected = (b == Move.UCHI) ? Outcome.PLUS : Outcome.MINUS; break;
                        case UCHI: expected = (b == Move.TSUKI) ? Outcome.PLUS : Outcome.MINUS; break;
                        case TSUKI: expected = (b == Move.ZAN) ? Outcome.PLUS : Outcome.MINUS; break;
                        default: expected = Outcome.NONE;
                    }
                    assertEquals(expected, o, () -> a + " vs " + b);
                }
            }
        }
    }
}

