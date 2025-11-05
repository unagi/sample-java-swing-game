package app;

import app.controller.GameController;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("三すくみバトル 5人抜き");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(new Dimension(640, 480));
            frame.setResizable(false);

            GameController controller = new GameController(frame);
            controller.showTitle();

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

