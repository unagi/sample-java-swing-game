package app.view;

import app.controller.GameController;
import app.model.*;

import javax.swing.*;
import java.awt.*;

public class BattleView extends JPanel {
    private final GameController controller;
    private Battler player;
    private Battler enemy;
    private int roundIndex;
    private int roundTotal;

    private final JLabel infoLabel = new JLabel();
    private final JProgressBar playerHpBar = new JProgressBar(0, 100);
    private final JProgressBar enemyHpBar = new JProgressBar(0, 100);
    private final JTextArea logArea = new JTextArea(6, 30);
    private final JButton zanBtn = new JButton(Move.ZAN.getLabel());
    private final JButton tsukiBtn = new JButton(Move.TSUKI.getLabel());
    private final JButton uchiBtn = new JButton(Move.UCHI.getLabel());

    public BattleView(GameController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(2, 2));
        top.add(new JLabel("プレイヤーHP:"));
        top.add(new JLabel("敵HP:"));
        playerHpBar.setStringPainted(true);
        enemyHpBar.setStringPainted(true);
        top.add(playerHpBar);
        top.add(enemyHpBar);
        add(top, BorderLayout.NORTH);

        logArea.setEditable(false);
        logArea.setLineWrap(true);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(infoLabel);
        bottom.add(zanBtn);
        bottom.add(tsukiBtn);
        bottom.add(uchiBtn);
        add(bottom, BorderLayout.SOUTH);

        zanBtn.addActionListener(e -> onPlayerMove(Move.ZAN));
        tsukiBtn.addActionListener(e -> onPlayerMove(Move.TSUKI));
        uchiBtn.addActionListener(e -> onPlayerMove(Move.UCHI));
    }

    public void setupBattle(Battler player, Battler enemy, int index, int total) {
        this.player = player;
        this.enemy = enemy;
        this.roundIndex = index; // 1-based index from Gauntlet.nextEnemy() increment timing
        this.roundTotal = total;
        infoLabel.setText("対戦: " + labelForIndex(index) + " (" + index + "/" + total + ")");
        logArea.setText(enemy.name + " があらわれた！\n");
        refreshBars();
        setButtonsEnabled(true);
    }

    private void refreshBars() {
        playerHpBar.setValue(player.hp);
        playerHpBar.setString(player.hp + "/" + player.maxHp);
        enemyHpBar.setValue(enemy.hp);
        enemyHpBar.setString(enemy.hp + "/" + enemy.maxHp);
    }

    private void onPlayerMove(Move playerMove) {
        setButtonsEnabled(false);
        Move enemyMove = Move.values()[controller.random().nextInt(Move.values().length)];
        Outcome playerOutcome = playerMove.outcomeAgainst(enemyMove);
        Outcome enemyOutcome = enemyMove.outcomeAgainst(playerMove);

        logArea.append("プレイヤーは" + playerMove.getLabel() + "！ 敵は" + enemyMove.getLabel() + "！\n");
        logArea.append("相性: プレイヤー=" + outcomeLabel(playerOutcome) + ", 敵=" + outcomeLabel(enemyOutcome) + "\n");

        // プレイヤー攻撃
        AttackResult r1 = controller.engine().attemptAttack(player, enemy, playerOutcome, player.name);
        logArea.append(r1.message + "\n");
        refreshBars();

        if (!enemy.isAlive()) {
            logArea.append(enemy.name + " を倒した！\n");
            // 勝利処理: 回復20
            SwingUtilities.invokeLater(() -> controller.onBattleWon(enemy, 20));
            return;
        }

        // 敵攻撃
        AttackResult r2 = controller.engine().attemptAttack(enemy, player, enemyOutcome, enemy.name);
        logArea.append(r2.message + "\n");
        refreshBars();

        if (!player.isAlive()) {
            logArea.append("プレイヤーは倒れた…\n");
            SwingUtilities.invokeLater(() -> controller.onBattleLost(enemy));
            return;
        }

        setButtonsEnabled(true);
    }

    private String outcomeLabel(Outcome o) {
        switch (o) {
            case PLUS: return "＋";
            case MINUS: return "－";
            default: return "なし";
        }
    }

    private String labelForIndex(int idx) {
        switch (idx) {
            case 1: return "先鋒";
            case 2: return "次鋒";
            case 3: return "中堅";
            case 4: return "副将";
            case 5: return "大将";
            default: return "" + idx;
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        zanBtn.setEnabled(enabled);
        tsukiBtn.setEnabled(enabled);
        uchiBtn.setEnabled(enabled);
    }
}

