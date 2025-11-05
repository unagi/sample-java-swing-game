package app.view;

import app.controller.GameController;
import app.model.Battler;

import javax.swing.*;
import java.awt.*;

public class ResultView extends JPanel {
    private final JLabel title = new JLabel("", SwingConstants.CENTER);
    private final JTextArea detail = new JTextArea(6, 30);

    private final JButton nextBtn = new JButton("次へ");
    private final JButton titleBtn = new JButton("タイトルへ");

    private boolean finalWin = false;
    private GameController controller;

    public ResultView(GameController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);
        detail.setEditable(false);
        detail.setLineWrap(true);
        add(new JScrollPane(detail), BorderLayout.CENTER);
        JPanel south = new JPanel();
        south.add(nextBtn);
        south.add(titleBtn);
        add(south, BorderLayout.SOUTH);

        nextBtn.addActionListener(e -> controller.startNextBattle(false));
        titleBtn.addActionListener(e -> controller.goTitle());
    }

    public void showResult(boolean win, boolean finished, Battler player, Battler enemy, int healed) {
        // 既存のリスナーをクリア
        for (var l : nextBtn.getActionListeners()) nextBtn.removeActionListener(l);

        this.finalWin = win && finished;
        if (win) {
            if (finished) {
                title.setText("完全勝利！");
                detail.setText("5人抜きを達成した！\nお疲れさまでした。\n");
                nextBtn.setText("タイトルへ");
                nextBtn.addActionListener(e -> controller.goTitle());
            } else {
                title.setText("勝利！");
                detail.setText(enemy.name + "を倒した！\nHPが" + healed + "回復した（上限100）。\n現在HP: " + player.hp + "/" + player.maxHp + "\n");
                nextBtn.setText("次の相手へ");
                nextBtn.addActionListener(e -> controller.startNextBattle(false));
            }
        } else {
            title.setText("ゲームオーバー");
            detail.setText("残念… また挑戦しよう。\n");
            nextBtn.setText("再挑戦（タイトルへ）");
            nextBtn.addActionListener(e -> controller.goTitle());
        }
    }
}
