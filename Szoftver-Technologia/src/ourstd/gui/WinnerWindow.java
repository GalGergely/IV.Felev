package ourstd.gui;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class WinnerWindow {

    private final JFrame window;

    public WinnerWindow(String whoWon) {
        window = new JFrame("We have a winner!");
        window.setVisible(true);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        int width = 300;
        int height = 200;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        window.setSize(width, height);
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        window.setLocation(x, y);
        JPanel winnerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel winnerLabel = new JLabel("You have won the game " + whoWon + "!");
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> {
            StartWindow sw = new StartWindow();
            sw.start();
            window.dispose();
        });
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        constraints.insets = new Insets(5,5,5,5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        winnerPanel.add(winnerLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        winnerPanel.add(playAgainButton, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        winnerPanel.add(exitButton, constraints);

        window.add(winnerPanel);
    }
}
