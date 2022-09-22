package ourstd.gui;

import ourstd.model.map.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;

public class MapCreationWindow {
    private static final ResourceLoader rl = new ResourceLoader();
    public static int FRAME_SIZE;
    private final JPanel buttonPanel;
    private final JFrame selectMapSizeFrame;
    private final Map map;
    private final GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final GraphicsDevice device = graphics.getDefaultScreenDevice();
    private MapAttributes attribute;

    public MapCreationWindow(int mapSize) {
        selectMapSizeFrame = new JFrame("Our Special TD");
        selectMapSizeFrame.setUndecorated(true);
        selectMapSizeFrame.setResizable(false);
        //device.setFullScreenWindow(selectMapSizeFrame);
        java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(selectMapSizeFrame);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        selectMapSizeFrame.addMouseListener(new MyMouseListener());
        BorderLayout blayout = new BorderLayout();
        selectMapSizeFrame.setLayout(blayout);
        selectMapSizeFrame.setVisible(true);
        selectMapSizeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int height = screenSize.height;
        int width = screenSize.width;
        FRAME_SIZE = height;

        selectMapSizeFrame.setSize(width, height);
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        selectMapSizeFrame.setLocation(x, y);
        map = new Map(mapSize);
        mapPanel mainPanel = new mapPanel(map);
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(width - mapSize * (FRAME_SIZE / map.getSize()), height));
        buttonPanel.setLayout(new GridLayout(7, 1));
        JButton p1CastleBtn = new JButton("P1 Castle");
        p1CastleBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                attribute = new Castle(1);
            }
        });
        JButton p2CastleBtn = new JButton("P2 Castle");
        p2CastleBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                attribute = new Castle(2);
            }
        });
        JButton landBtn = new JButton(("Land"));
        landBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                attribute = new Land();
            }
        });
        JButton mountainBtn = new JButton(("Mountain"));
        mountainBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                attribute = new Mountain();
            }
        });
        JButton riverBtn = new JButton("River");
        riverBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                attribute = new Water();
            }
        });

        JButton saveAndExitBtn = new JButton("Save and exit");
        saveAndExitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int retrival = chooser.showSaveDialog(null);
                if (retrival == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileWriter myWriter = new FileWriter(chooser.getSelectedFile() + ".txt");
                        for (int i = 0; i < map.getSize(); i++) {
                            String line = "";
                            for (int j = 0; j < map.getSize(); j++) {
                                line += map.getMap().get(i).get(j).getAttribute();
                            }
                            myWriter.write(line);
                            myWriter.write("\n");
                        }

                        myWriter.close();
                        selectMapSizeFrame.setVisible(false);
                        StartWindow sw = new StartWindow();
                        sw.start();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if (retrival == JFileChooser.CANCEL_OPTION) {
                    return;
                }
            }
        });

        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartWindow sw = new StartWindow();
                sw.start();
                selectMapSizeFrame.setVisible(false);

            }
        });

        buttonPanel.add(landBtn);
        buttonPanel.add(mountainBtn);
        buttonPanel.add(riverBtn);
        buttonPanel.add(p2CastleBtn);
        buttonPanel.add(p1CastleBtn);
        buttonPanel.add(saveAndExitBtn);
        buttonPanel.add(exitBtn);


        selectMapSizeFrame.add(mainPanel, BorderLayout.CENTER);
        selectMapSizeFrame.add(buttonPanel, BorderLayout.EAST);
        selectMapSizeFrame.setVisible(true);
        attribute = new Land();

    }

    public static class mapPanel extends JPanel implements ActionListener {

        private final Map m;
        private final Timer timer = new Timer(1, this);

        public mapPanel(Map map) {
            this.m = map;
            timer.start();
        }

        @Override
        public void paint(Graphics g) {

            int w = FRAME_SIZE / m.getSize();
            int h = FRAME_SIZE / m.getSize();
            for (int i = 0; i < m.getSize(); i++) {
                for (int j = 0; j < m.getSize(); j++) {
                    Image img = decideBoardPiece(i, j);
                    paintComponent(g, i * w, j * h, img, w, h);
                }
            }
        }

        private Image decideBoardPiece(int i, int j) {
            MapAttributes ma = m.getMap().get(i).get(j);
            if (ma.getAttribute().equals("land")) {
                return rl.land;
            } else if (ma.getAttribute().equals("castle1")) {
                return rl.castlePlayerOne;
            } else if (ma.getAttribute().equals("castle2")) {
                return rl.castlePlayerTwo;
            } else if (ma.getAttribute().equals("water")) {
                return rl.water;
            } else {
                return rl.mountain;
            }
        }

        private void paintComponent(Graphics g, int x, int y, Image img, int width, int height) {
            g.drawImage(img, x, y, width, height, null);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == timer) {
                repaint();
            }
        }


    }

    public class MyMouseListener extends JFrame implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            String s = "X-Corrdinate = " + e.getX() + " y-Coordinate = " + e.getY();
            System.out.println("Mouse Clicked: " + s);
            decideWitchTileIsClicked(e.getX(), e.getY());
            selectMapSizeFrame.setCursor(Cursor.getDefaultCursor());


        }

        @Override
        public void mousePressed(MouseEvent e) {
            String s = "X-Corrdinate = " + e.getX() + " y-Coordinate = " + e.getY();
            System.out.println("Mouse Pressed: " + s);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            String s = "X-Corrdinate = " + e.getX() + " y-Coordinate = " + e.getY();
            System.out.println("Mouse Released: " + s);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("Mouse Entered");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println("Mouse Exited");
        }

        private void decideWitchTileIsClicked(int x, int y) {
            x = (int) Math.floor(x / (FRAME_SIZE / map.getSize()));
            y = (int) Math.floor(y / (FRAME_SIZE / map.getSize()));
            if (attribute instanceof Castle) {
                System.out.println("igen ez így van");
                for (int i = 0; i < map.getSize(); i++) {
                    for (int j = 0; j < map.getSize(); j++) {
                        if (map.getMap().get(i).get(j) instanceof Castle && ((Castle) map.getMap().get(i).get(j)).getNumber() == ((Castle) attribute).getNumber())
                            map.setMapAttribute(i, j, new Land());
                    }
                }
            }
            if (map.getMap().get(x).get(y) instanceof Castle){
                return;
            }
            System.out.println(x + " x || y " + y);

            map.setMapAttribute(x, y, attribute);


            repaint();
        }
    }
}
