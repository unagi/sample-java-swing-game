package app.model;

import java.util.Random;

public class BattleEngine {
    private final Random random;

    public BattleEngine(Random random) {
        this.random = random;
    }

    public int calcHitPct(Stats atk, Outcome outcome) {
        int base = 70 + atk.skill * 2;
        if (outcome == Outcome.PLUS) base += 20;
        else if (outcome == Outcome.MINUS) base -= 20;
        if (base < 5) base = 5;
        if (base > 95) base = 95;
        return base;
    }

    public int calcDamage(Stats atk, Stats def, Outcome outcome) {
        int base = 10 + atk.power * 3;
        if (outcome == Outcome.PLUS) base += 5;
        else if (outcome == Outcome.MINUS) base -= 5;
        int dmg = base - def.guard * 2;
        return Math.max(1, dmg);
    }

    public AttackResult attemptAttack(Battler attacker, Battler defender, Outcome outcome, String attackerName) {
        int hitPct = calcHitPct(attacker.stats, outcome);
        int roll = random.nextInt(100); // 0-99
        if (roll < hitPct) {
            int dmg = calcDamage(attacker.stats, defender.stats, outcome);
            defender.damage(dmg);
            String msg = attackerName + "の攻撃！ 命中（" + hitPct + "%）！ " + dmg + "のダメージ！";
            return new AttackResult(true, dmg, msg);
        } else {
            String msg = attackerName + "の攻撃！ 外れた…（" + hitPct + "%）";
            return new AttackResult(false, 0, msg);
        }
    }
}

