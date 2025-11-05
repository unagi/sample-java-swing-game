package app.view;

import app.controller.GameController;
import app.model.Stats;

import javax.swing.*;
import java.awt.*;

public class AllocateView extends JPanel {
    private static final int MAX_PER_STAT = 10;
    private static final int TOTAL_POINTS = 18;

    private int power = 0, skill = 0, guard = 0;

    private final JLabel remainLabel = new JLabel();
    private final JLabel powerLabel = new JLabel();
    private final JLabel skillLabel = new JLabel();
    private final JLabel guardLabel = new JLabel();
    private final JButton startBtn = new JButton("このステータスで開始");

    public AllocateView(GameController controller) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("ステータス配分（合計18pt / 各0〜10）", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(4, 1));
        center.add(row("力", powerLabel, e -> addStat("power"), e -> subStat("power")));
        center.add(row("技", skillLabel, e -> addStat("skill"), e -> subStat("skill")));
        center.add(row("守備", guardLabel, e -> addStat("guard"), e -> subStat("guard")));
        center.add(remainLabel);
        add(center, BorderLayout.CENTER);

        JPanel south = new JPanel();
        JButton backBtn = new JButton("タイトルに戻る");
        south.add(startBtn);
        south.add(backBtn);
        add(south, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> controller.goTitle());
        startBtn.addActionListener(e -> controller.startGameWithStats(new Stats(power, skill, guard)));

        updateLabels();
    }

    private JPanel row(String name, JLabel valueLabel, java.awt.event.ActionListener plus, java.awt.event.ActionListener minus) {
        JPanel p = new JPanel();
        p.add(new JLabel(name + ": "));
        JButton sub = new JButton("-");
        JButton add = new JButton("+");
        p.add(sub);
        p.add(add);
        p.add(valueLabel);
        add.addActionListener(plus);
        sub.addActionListener(minus);
        return p;
    }

    private int sum() { return power + skill + guard; }

    private void addStat(String which) {
        if (sum() >= TOTAL_POINTS) return;
        switch (which) {
            case "power": if (power < MAX_PER_STAT) power++; break;
            case "skill": if (skill < MAX_PER_STAT) skill++; break;
            case "guard": if (guard < MAX_PER_STAT) guard++; break;
        }
        updateLabels();
    }

    private void subStat(String which) {
        switch (which) {
            case "power": if (power > 0) power--; break;
            case "skill": if (skill > 0) skill--; break;
            case "guard": if (guard > 0) guard--; break;
        }
        updateLabels();
    }

    private void updateLabels() {
        powerLabel.setText("" + power);
        skillLabel.setText("" + skill);
        guardLabel.setText("" + guard);
        int remain = TOTAL_POINTS - sum();
        remainLabel.setText("残りポイント: " + remain);
        startBtn.setEnabled(remain == 0);
    }

    public void reset() {
        power = skill = guard = 0;
        updateLabels();
    }
}

