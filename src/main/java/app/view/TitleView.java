package app.view;

import app.controller.GameController;

import javax.swing.*;
import java.awt.*;

public class TitleView extends JPanel {
    public TitleView(GameController controller) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("三すくみバトル（5人抜き）", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        add(title, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton start = new JButton("開始");
        JButton exit = new JButton("終了");
        buttons.add(start);
        buttons.add(exit);
        add(buttons, BorderLayout.SOUTH);

        start.addActionListener(e -> controller.startAllocate());
        exit.addActionListener(e -> System.exit(0));
    }
}

