package app.model;

import java.util.Random;

public class Gauntlet {
    private static final String[] TITLES = {"先鋒", "次鋒", "中堅", "副将", "大将"};
    private static final int[] ENEMY_POINTS = {12, 13, 14, 15, 16};

    private final Random random;
    private int index = 0;

    public Gauntlet(Random random) {
        this.random = random;
    }

    public int size() { return TITLES.length; }
    public int index() { return index; }
    public boolean isLast() { return index >= size() - 1; }
    public String currentTitle() { return TITLES[index]; }

    public Battler nextEnemy() {
        if (index >= size()) return null;
        int total = ENEMY_POINTS[index];
        Stats s = randomStats(total);
        String name = currentTitle() + "のスライム";
        Battler b = new Battler(name, 100, s);
        index++;
        return b;
    }

    private Stats randomStats(int total) {
        int power = 0, skill = 0, guard = 0;
        int remaining = total;
        // ランダム配分（各0〜10）
        for (int i = 0; i < 3; i++) {
            int maxAlloc = Math.min(10, remaining);
            int alloc = (i == 2) ? remaining : random.nextInt(maxAlloc + 1);
            if (i == 0) power = Math.min(10, alloc);
            else if (i == 1) skill = Math.min(10, alloc);
            else guard = Math.min(10, alloc);
            remaining -= alloc;
        }
        // 配分が偏った場合の微調整
        while (remaining > 0) {
            int pick = random.nextInt(3);
            if (pick == 0 && power < 10) { power++; remaining--; }
            else if (pick == 1 && skill < 10) { skill++; remaining--; }
            else if (pick == 2 && guard < 10) { guard++; remaining--; }
        }
        return new Stats(power, skill, guard);
    }
}

