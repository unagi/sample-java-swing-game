package app.model;

public class Battler {
    public final String name;
    public final int maxHp;
    public int hp;
    public final Stats stats;

    public Battler(String name, int maxHp, Stats stats) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.stats = stats;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    public void damage(int amount) {
        hp = Math.max(0, hp - Math.max(0, amount));
    }
}

