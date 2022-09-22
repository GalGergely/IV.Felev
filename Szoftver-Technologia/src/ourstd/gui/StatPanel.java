package ourstd.gui;

import ourstd.model.Balance;
import ourstd.model.GamePhase;
import ourstd.model.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatPanel extends JPanel {
    private final Balance bl = new Balance();
    private final JLabel playerOne = new JLabel("Player One");
    private final JLabel playerTwo = new JLabel("Player Two");
    private final JLabel info = new JLabel("Info panel");
    private final GameWindow gw;
    private final int width;
    private final int height;
    //Buttonok:
    private final JButton endPlayerOneDefendPhase;
    private final Dimension endPlayerOneDefendPhaseDimension;
    private final JButton endPlayerTwoDefendPhase;
    private final Dimension endPlayerTwoDefendPhaseDimension;
    private final JButton endPlayerOneRecruitingPhase;
    private final Dimension endPlayerOneRecruitingPhaseDimension;
    private final JButton endPlayerTwoRecruitingPhase;
    private final Dimension endPlayerTwoRecruitingPhaseDimension;
    //Player one dolgai:
    private final JPanel playerOneInfoPanel;
    private final JLabel playerOneCastleHP;
    private final JLabel playerOneGold;
    private final JLabel playerOneName;
    private final JLabel playerOneNumOfTowers;
    private final JLabel playerOneNumOfFighters;
    private final Dimension playerOneInfoPanelDimension;
    //Player two dolgai:
    private final JPanel playerTwoInfoPanel;
    private final JLabel playerTwoCastleHP;
    private final JLabel playerTwoGold;
    private final JLabel playerTwoName;
    private final JLabel playerTwoNumOfTowers;
    private final JLabel playerTwoNumOfFighters;
    private final Dimension playerTwoInfoPanelDimension;
    //Info panel dolgai:
    private final Border blackBorder = BorderFactory.createLineBorder(Color.black);
    private final JPanel infoPanel;
    private final JLabel activePlayer;
    private final JLabel roundLabel;
    private final JLabel phaseLabel;
    private final JLabel infoLabel;
    private final Dimension infoPanelDimension;

    //Elrendezéshez:
    private final int padding;
    private final int textSize = 25;

    public StatPanel(GameWindow gw, int width, int height) {
        this.gw = gw;
        this.width = width;
        this.height = height;
        padding = this.width / 30;

        Dimension buttonSize = new Dimension(((width / 2) - (2 * padding)), textSize);

        endPlayerOneDefendPhase = new JButton("End defending phase");
        endPlayerOneDefendPhaseDimension = buttonSize;
        endPlayerTwoDefendPhase = new JButton("End defending phase");
        endPlayerTwoDefendPhase.setEnabled(false);
        endPlayerTwoDefendPhaseDimension = buttonSize;
        endPlayerOneRecruitingPhase = new JButton("End recruiting phase");
        endPlayerOneRecruitingPhase.setEnabled(false);
        endPlayerOneRecruitingPhaseDimension = buttonSize;
        endPlayerTwoRecruitingPhase = new JButton("End recruiting phase");
        endPlayerTwoRecruitingPhase.setEnabled(false);
        endPlayerTwoRecruitingPhaseDimension = buttonSize;

        playerOneGold = new JLabel();
        playerOneName = new JLabel();
        playerOneNumOfTowers = new JLabel();
        playerOneNumOfFighters = new JLabel();
        playerOneCastleHP = new JLabel();

        playerTwoGold = new JLabel();
        playerTwoName = new JLabel();
        playerTwoNumOfTowers = new JLabel();
        playerTwoNumOfFighters = new JLabel();
        playerTwoCastleHP = new JLabel();

        roundLabel = new JLabel();
        infoLabel = new JLabel();
        activePlayer = new JLabel();
        phaseLabel = new JLabel();

        int x = 0;
        int y = 0;
        int w = 0;
        int h = 0;

        playerOneInfoPanel = new JPanel(null);
        playerOneInfoPanel.setBackground(Color.decode("#93007d"));

        x = padding;
        y = padding;
        w = width;
        h = textSize;
        playerOne.setBounds(x, y, w, h);
        playerOneInfoPanel.add(playerOne);

        y = y + h + padding;
        playerOneName.setText("Name: " + gw.getGame().getPlayerOne().getName());
        playerOneName.setBounds(x, y, w, h);
        playerOneInfoPanel.add(playerOneName);

        y = y + h;
        playerOneCastleHP.setText("Castle HP: " + gw.getGame().getPlayerOne().getCastleHP());
        playerOneCastleHP.setBounds(x, y, w, h);
        playerOneInfoPanel.add(playerOneCastleHP);

        y = y + h;
        playerOneGold.setText("Gold: " + gw.getGame().getPlayerOne().getGold());
        playerOneGold.setBounds(x, y, w, h);
        playerOneInfoPanel.add(playerOneGold);

        y = y + h;
        playerOneNumOfTowers.setText("Number of towers on map: " + gw.getGame().getPlayerOne().getTowers().size());
        playerOneNumOfTowers.setBounds(x, y, w, h);
        playerOneInfoPanel.add(playerOneNumOfTowers);

        y = y + h;
        playerOneNumOfFighters.setText("Number of fighters on map: " + gw.getGame().getPlayerOne().getFighters().size());
        playerOneNumOfFighters.setBounds(x, y, w, h);
        playerOneInfoPanel.add(playerOneNumOfFighters);

        y = y + h + padding;
        w = endPlayerOneDefendPhaseDimension.width;
        h = endPlayerOneDefendPhaseDimension.height;
        endPlayerOneDefendPhase.setBounds(x, y, w, h);
        endPlayerOneDefendPhase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gw.getGame().setActivePlayer(2);
                gw.getGame().setGamePhase(GamePhase.PLAYER_TWO_DEFENDING_PHASE);
                updateLeftPanel(gw.getGame().getActivePlayer());
                updateRightPanel();
                endPlayerOneDefendPhase.setEnabled(false);
                endPlayerTwoDefendPhase.setEnabled(true);
            }
        });
        playerOneInfoPanel.add(endPlayerOneDefendPhase);

        x = x + endPlayerOneDefendPhaseDimension.width + padding;
        w = endPlayerOneRecruitingPhaseDimension.width;
        h = endPlayerOneRecruitingPhaseDimension.height;
        endPlayerOneRecruitingPhase.setBounds(x, y, w, h);
        endPlayerOneRecruitingPhase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gw.getGame().setActivePlayer(2);
                gw.getGame().setGamePhase(GamePhase.PLAYER_TWO_RECRUITING_PHASE);
                updateLeftPanel(gw.getGame().getActivePlayer());
                updateRightPanel();
                endPlayerOneRecruitingPhase.setEnabled(false);
                endPlayerTwoRecruitingPhase.setEnabled(true);
                // kor vegi aktivitasok
                gw.getGame().getPlayerOne().giveGold(bl.getGoldPerRound());
                gw.getGame().getPlayerTwo().giveGold(bl.getGoldPerRound());
            }
        });
        playerOneInfoPanel.add(endPlayerOneRecruitingPhase);

        playerOneInfoPanelDimension = new Dimension(width, (height / 4));
        playerOneInfoPanel.setPreferredSize(playerOneInfoPanelDimension);
        playerOneInfoPanel.setBorder(blackBorder);

        playerTwoInfoPanel = new JPanel(null);
        playerTwoInfoPanel.setBackground(Color.decode("#ffbf00"));

        x = padding;
        y = padding;
        w = width;
        h = textSize;
        playerTwo.setBounds(x, y, w, h);
        playerTwoInfoPanel.add(playerTwo);

        y = y + h + padding;
        playerTwoName.setText("Name: " + gw.getGame().getPlayerTwo().getName());
        playerTwoName.setBounds(x, y, w, h);
        playerTwoInfoPanel.add(playerTwoName);

        y = y + h;
        playerTwoCastleHP.setText("Castle HP: " + gw.getGame().getPlayerTwo().getCastleHP());
        playerTwoCastleHP.setBounds(x, y, w, h);
        playerTwoInfoPanel.add(playerTwoCastleHP);

        y = y + h;
        playerTwoGold.setText("Gold: " + gw.getGame().getPlayerTwo().getGold());
        playerTwoGold.setBounds(x, y, w, h);
        playerTwoInfoPanel.add(playerTwoGold);

        y = y + h;
        playerTwoNumOfTowers.setText("Number of towers on map: " + gw.getGame().getPlayerTwo().getTowers().size());
        playerTwoNumOfTowers.setBounds(x, y, w, h);
        playerTwoInfoPanel.add(playerTwoNumOfTowers);

        y = y + h;
        playerTwoNumOfFighters.setText("Number of fighters on map: " + gw.getGame().getPlayerTwo().getFighters().size());
        playerTwoNumOfFighters.setBounds(x, y, w, h);
        playerTwoInfoPanel.add(playerTwoNumOfFighters);

        y = y + h + padding;
        w = endPlayerTwoDefendPhaseDimension.width;
        h = endPlayerTwoDefendPhaseDimension.height;
        endPlayerTwoDefendPhase.setBounds(x, y, w, h);
        endPlayerTwoDefendPhase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gw.getGame().setActivePlayer(1);
                gw.getGame().setGamePhase(GamePhase.PLAYER_ONE_RECRUITING_PHASE);
                updateLeftPanel(gw.getGame().getActivePlayer());
                updateRightPanel();
                endPlayerTwoDefendPhase.setEnabled(false);
                endPlayerOneRecruitingPhase.setEnabled(true);
            }
        });
        playerTwoInfoPanel.add(endPlayerTwoDefendPhase);

        x = x + endPlayerTwoDefendPhaseDimension.width + padding;
        w = endPlayerTwoRecruitingPhaseDimension.width;
        h = endPlayerTwoRecruitingPhaseDimension.height;
        endPlayerTwoRecruitingPhase.setBounds(x, y, w, h);
        endPlayerTwoRecruitingPhase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gw.getGame().setActivePlayer(1);
                gw.getGame().setGamePhase(GamePhase.PLAYER_ONE_DEFENDING_PHASE);
                gw.getGame().plusOneRound();
                updateLeftPanel(gw.getGame().getActivePlayer());
                updateRightPanel();
                endPlayerTwoRecruitingPhase.setEnabled(false);
                endPlayerOneDefendPhase.setEnabled(true);

                for (int i = 0; i < gw.getGame().getPlayerOne().getFighters().size(); i++) {
                    gw.getGame().getPlayerOneFighterOnIndex(i).setMoveCounter(0);
                }
                for (int i = 0; i < gw.getGame().getPlayerTwo().getFighters().size(); i++) {
                    gw.getGame().getPlayerTwoFighterOnIndex(i).setMoveCounter(0);
                }
            }
        });
        playerTwoInfoPanel.add(endPlayerTwoRecruitingPhase);

        playerTwoInfoPanelDimension = new Dimension(width, (height / 4));
        playerTwoInfoPanel.setPreferredSize(playerTwoInfoPanelDimension);
        playerTwoInfoPanel.setBorder(blackBorder);

        infoPanel = new JPanel(null);
        x = padding;
        y = padding;
        w = width;
        h = textSize;
        info.setBounds(x, y, w, h);
        infoPanel.add(info);

        y = y + h + padding;
        roundLabel.setText("Round: " + gw.getGame().getRoundCounter());
        roundLabel.setBounds(x, y, w, h);
        infoPanel.add(roundLabel);

        y = y + h;
        activePlayer.setText("Active player: " + gw.getGame().getActivePlayer().getName());
        activePlayer.setBounds(x, y, w, h);
        infoPanel.add(activePlayer);

        y = y + h;
        phaseLabel.setText("Phase: " + gw.getGame().getGamePhaseString());
        phaseLabel.setBounds(x, y, w, h);
        infoPanel.add(phaseLabel);


        y= y + h;
        infoLabel.setText("Info: " + gw.getGame().getGamePhaseString());
        infoLabel.setBounds(x, y, w, h);
        infoPanel.add(infoLabel);

        // ha szar helyre kattintott
        if(gw.getGame().isOnUnit() == true) {
            System.out.println("bent vagyunk");
            y= y + h;
            JLabel MissClickedLabel = new JLabel();
            MissClickedLabel.setText("Cant place turet on a unit!");
            MissClickedLabel.setBounds(x, y, w, h);
            infoPanel.add(MissClickedLabel);
        }

        infoPanelDimension = new Dimension(width, (height / 2));
        infoPanel.setPreferredSize(infoPanelDimension);
        infoPanel.setBorder(blackBorder);

        setLayout(new BorderLayout());
        add(playerOneInfoPanel, BorderLayout.NORTH);
        add(playerTwoInfoPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
    }

    private void updateLeftPanel(Player player) {
        for (int i = 0; i < 8; i++) {
            gw.getUnitPanels().get(i).updateUnit(player.getUnitInfo().get(i), i);
        }
    }

    public void updateRightPanel() {
        playerOneGold.setText("Gold: " + gw.getGame().getPlayerOne().getGold());
        playerOneNumOfTowers.setText("Number of towers on map: " + gw.getGame().getPlayerOne().getTowers().size());
        playerOneNumOfFighters.setText("Number of fighters on map: " + gw.getGame().getPlayerOne().getFighters().size());
        playerOneCastleHP.setText("Castle HP: " + gw.getGame().getPlayerOne().getCastleHP());

        playerTwoGold.setText("Gold: " + gw.getGame().getPlayerTwo().getGold());
        playerTwoNumOfTowers.setText("Number of towers on map: " + gw.getGame().getPlayerTwo().getTowers().size());
        playerTwoNumOfFighters.setText("Number of fighters on map: " + gw.getGame().getPlayerTwo().getFighters().size());
        playerTwoCastleHP.setText("Castle HP: " + gw.getGame().getPlayerTwo().getCastleHP());

        activePlayer.setText("Active player: " + gw.getGame().getActivePlayer().getName());
        roundLabel.setText("Round: " + gw.getGame().getRoundCounter());
        phaseLabel.setText("Phase: " + gw.getGame().getGamePhaseString());
    }

    public void updatePlayerGold(Player activePlayer){
        if(activePlayer.getName().equals(gw.getGame().getPlayerOne().getName())){
            playerOneGold.setText("Gold: " + activePlayer.getGold());
        } else {
            playerTwoGold.setText("Gold: " + activePlayer.getGold());
        }
    }

    public JLabel getInfoLabel() {return infoLabel;}
}
