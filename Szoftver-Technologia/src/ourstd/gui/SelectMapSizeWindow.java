package ourstd.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectMapSizeWindow {

    public void start() {
        JFrame selectMapSizeFrame = new JFrame("Our Special TD");
        selectMapSizeFrame.setVisible(true);
        selectMapSizeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 300;
        int height = 300;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        selectMapSizeFrame.setSize(width, height);
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        selectMapSizeFrame.setLocation(x, y);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        JLabel label = new JLabel("Set the map size:", SwingConstants.CENTER);
        JLabel emptyjlabel1 = new JLabel();
        JLabel emptyjlabel2 = new JLabel();

        JTextField mapSizeField = new JTextField();

        JButton nextButton = new JButton("Next");

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(mapSizeField.getText() == null || mapSizeField.getText().equals("")) {
                    System.out.println("String cannot be parsed, it is null or empty.");
                    mapSizeField.setText("10");
                }
                try {
                    int intValue = Integer.parseInt(mapSizeField.getText());
                    if (intValue>9) {
                        MapCreationWindow mapcrw = new MapCreationWindow(intValue);
                    }
                    else{
                        MapCreationWindow mapcrw = new MapCreationWindow(10);
                    }
                } catch (NumberFormatException f) {
                    System.out.println("Input String cannot be parsed to Integer.");
                    MapCreationWindow mapcrw = new MapCreationWindow(10);
                }

                selectMapSizeFrame.setVisible(false);
            }
        });

        mainPanel.add(label);
        mainPanel.add(mapSizeField);
        mainPanel.add(emptyjlabel1);
        mainPanel.add(emptyjlabel2);
        mainPanel.add(nextButton);

        selectMapSizeFrame.add(mainPanel);
        selectMapSizeFrame.setVisible(true);
    }
}
