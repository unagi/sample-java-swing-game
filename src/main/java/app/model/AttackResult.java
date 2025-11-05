package app.model;

public class AttackResult {
    public final boolean hit;
    public final int damage;
    public final String message;

    public AttackResult(boolean hit, int damage, String message) {
        this.hit = hit;
        this.damage = damage;
        this.message = message;
    }
}

