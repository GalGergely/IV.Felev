package ourstd.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class StartWindow {
    private GameWindow newGame;
    private SelectMapSizeWindow newMapSize;
    private String p1Name;
    private String p2Name;

    public void start() {
        JFrame startFrame = new JFrame("Our Special TD");
        startFrame.setVisible(true);
        startFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        int width = 600;
        int height = 400;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Container pane = startFrame.getContentPane();
        Dimension screenSize = toolkit.getScreenSize();
        startFrame.setSize(width, height);
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        startFrame.setLocation(x, y);
        ResourceLoader rl = new ResourceLoader();
        ImagePanel mainPanel = new ImagePanel(rl.background);
        mainPanel.setLayout(null);
        startFrame.setResizable(false);

        mainPanel.setBackground(new Color(86,142,52));
        JLabel p1Lab = new JLabel("Player1 name:");
        p1Lab.setFont(new Font("Calibri", Font.BOLD, 20));
        p1Lab.setBounds(10, 20, width / 4 - 10, 30);
        mainPanel.add(p1Lab);
        JLabel p2Lab = new JLabel("Player2 name:");
        p2Lab.setFont(new Font("Calibri", Font.BOLD, 20));
        p2Lab.setBounds(width / 2 + 10, 20, width / 2 - 10, 30);
        mainPanel.add(p2Lab);
        JTextField p1TextF = new JTextField("player1");
        p1TextF.setBounds(width / 4, 20, width / 4, 30);
        mainPanel.add(p1TextF);
        JTextField p2TextF = new JTextField("player2");
        p2TextF.setBounds(width * 3 / 4, 20, width / 4, 30);
        mainPanel.add(p2TextF);

        JButton startBtn = new JButton("Start");
        startBtn.setBounds(width / 2 - 95, 100, 190, 70);
        mainPanel.add(startBtn);
        JButton levelSelectBtn = new JButton("Create level");
        levelSelectBtn.setBounds(width / 2 - 70, 180, 140, 50);
        mainPanel.add(levelSelectBtn);
        JButton loadMapBtn = new JButton("Load map");
        loadMapBtn.setBounds(width / 2 - 70, 240, 140, 50);
        mainPanel.add(loadMapBtn);
        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(width / 2 - 70, 300, 140, 50);
        mainPanel.add(exitBtn);

        levelSelectBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startFrame.setVisible(false);
                newMapSize = new SelectMapSizeWindow();
                newMapSize.start();
            }
        });

        loadMapBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filepath = null;
                JFileChooser fc = new JFileChooser();
                int i = fc.showOpenDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    filepath = f.getPath();
                    System.out.println(filepath);
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(filepath));
                        String s1 = "", s2 = "";
                        while ((s1 = br.readLine()) != null) {
                            s2 += s1 + "\n";
                        }
                        System.out.println(s2);
                        br.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    startFrame.setVisible(false);
                    if (p1TextF.getText().equals("")) {
                        p1TextF.setText("player1");
                    }
                    if (p2TextF.getText().equals("")) {
                        p2TextF.setText("player2");
                    }
                    try {
                        newGame = new GameWindow(p1TextF.getText(), p2TextF.getText(), filepath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    saveNames(p1TextF.getText(), p2TextF.getText());
                }
                if (i == JFileChooser.CANCEL_OPTION) {
                    return;
                }
            }
        });

        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startFrame.setVisible(false);
                if (p1TextF.getText().equals("")) {
                    p1TextF.setText("player1");
                }
                if (p2TextF.getText().equals("")) {
                    p2TextF.setText("player2");
                }
                try {
                    newGame = new GameWindow(p1TextF.getText(), p2TextF.getText(), null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                saveNames(p1TextF.getText(), p2TextF.getText());
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        pane.add(mainPanel);
        startFrame.setVisible(true);

    }
    /**
     * @param p1
     * @param p2
     * Beallítja a Player 1 és a player 2 nevet
     */
    private void saveNames(String p1, String p2) {
        this.p1Name = p1;
        this.p2Name = p2;
    }

    /**
     * @param playerNumber
     * @return Vissza adja, hogy mi a player neve, pl: Ha 1-est kap akkor, playerOne nevet adja vissza.
     */
    public String getName(int playerNumber) {
        if (playerNumber == 1) {
            return this.p1Name;
        } else if (playerNumber == 2) {
            return this.p2Name;
        } else {
            return "";
        }
    }

    public static class ImagePanel extends JPanel {
        private Image image;
        public ImagePanel(Image image) {
            this.image = image;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }
}
