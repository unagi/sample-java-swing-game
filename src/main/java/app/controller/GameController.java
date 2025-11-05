package app.controller;

import app.model.*;
import app.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameController {
    private final JFrame frame;
    private final JPanel root;
    private final CardLayout cards;

    private final Random random;
    private final BattleEngine engine;

    private Gauntlet gauntlet;
    private Battler player;

    private TitleView titleView;
    private AllocateView allocateView;
    private BattleView battleView;
    private ResultView resultView;

    public static final String CARD_TITLE = "title";
    public static final String CARD_ALLOC = "alloc";
    public static final String CARD_BATTLE = "battle";
    public static final String CARD_RESULT = "result";

    public GameController(JFrame frame) {
        this.frame = frame;
        this.cards = new CardLayout();
        this.root = new JPanel(cards);
        this.random = new Random();
        this.engine = new BattleEngine(random);

        initViews();

        frame.setContentPane(root);
    }

    private void initViews() {
        titleView = new TitleView(this);
        allocateView = new AllocateView(this);
        battleView = new BattleView(this);
        resultView = new ResultView(this);

        root.add(titleView, CARD_TITLE);
        root.add(allocateView, CARD_ALLOC);
        root.add(battleView, CARD_BATTLE);
        root.add(resultView, CARD_RESULT);
    }

    public void showTitle() {
        cards.show(root, CARD_TITLE);
    }

    public void startAllocate() {
        allocateView.reset();
        cards.show(root, CARD_ALLOC);
    }

    public void startGameWithStats(Stats stats) {
        this.player = new Battler("プレイヤー", 100, stats.copy());
        this.gauntlet = new Gauntlet(random);
        startNextBattle(true);
    }

    public void startNextBattle(boolean first) {
        Battler enemy = gauntlet.nextEnemy();
        if (enemy == null) {
            // 完全勝利
            resultView.showResult(true, true, player, null, 0);
            cards.show(root, CARD_RESULT);
            return;
        }
        battleView.setupBattle(player, enemy, gauntlet.index(), gauntlet.size());
        cards.show(root, CARD_BATTLE);
    }

    public void onBattleWon(Battler enemy, int heal) {
        player.heal(heal);
        resultView.showResult(true, gauntlet.index() >= gauntlet.size(), player, enemy, heal);
        cards.show(root, CARD_RESULT);
    }

    public void onBattleLost(Battler enemy) {
        resultView.showResult(false, false, player, enemy, 0);
        cards.show(root, CARD_RESULT);
    }

    public BattleEngine engine() {
        return engine;
    }

    public Random random() {
        return random;
    }

    public void goTitle() {
        showTitle();
    }
}

