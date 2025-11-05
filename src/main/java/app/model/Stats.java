package app.model;

public class Stats {
    public int power;
    public int skill;
    public int guard;

    public Stats() {}

    public Stats(int power, int skill, int guard) {
        this.power = power;
        this.skill = skill;
        this.guard = guard;
    }

    public Stats copy() {
        return new Stats(power, skill, guard);
    }
}

