package ourstd.gui;

import ourstd.model.GamePhase;
import ourstd.model.units.UnitTypes;
import ourstd.model.units.Units;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class UnitPanel extends JPanel {

    private final ResourceLoader rl = new ResourceLoader();
    private final Color backGroundColor = new Color(165, 115, 16); // Itt változtasd meg a háttérszínt
    //Swing components:
    private ImageIcon picture;
    private JLabel picLabel;
    private final JLabel levelLabel;
    private final JLabel rangeLabel;
    private final JLabel damageLabel;
    private final JLabel maxHP;
    private final JLabel buyLabel;
    private final JLabel upgradeLabel;
    private final JButton buyButton;
    private final JButton upgradeButton;
    private final int index;
    private final GameWindow gw;

    private final int width; // UnitPanel width
    private final int height; // UnitPanel height

    private int pictureSize; //Mekkora legyen a kis icon.
    private final int padding;
    private final int textSize = 15;
    private final Dimension buyButtonDimension = new Dimension(100, 25);
    private final Dimension upgradeButtonDimension = new Dimension(100, 25);

    public UnitPanel(int width, int height, int index, GameWindow gw) {
        this.width = width;
        this.height = height;
        this.index = index;
        this.gw = gw;
        padding = this.width / 30;

        levelLabel = new JLabel();
        rangeLabel = new JLabel();
        damageLabel = new JLabel();
        buyLabel = new JLabel();
        upgradeLabel = new JLabel();
        maxHP = new JLabel();
        buyButton = new JButton();
        upgradeButton = new JButton();

        setPreferredSize(new Dimension(this.width, this.height));
        calculatePictureSize();
        setUnit(this.gw.getGame().getPlayerOne().getUnitInfo().get(this.index));
        setBackground(backGroundColor);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(null);

        int x = padding;
        int y = padding;
        int w = pictureSize;
        int h = pictureSize;
        picLabel.setBounds(x, y, w, h);
        picLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(picLabel);

        x = width / 2;
        w = width;
        h = textSize;
        levelLabel.setBounds(x, y, w, h);
        add(levelLabel);

        x = padding;
        y = y + picLabel.getBounds().height + padding;
        w = width;
        h = textSize;
        rangeLabel.setBounds(x, y, w, h);
        add(rangeLabel);

        x = padding;
        y = y + rangeLabel.getBounds().height;
        h = textSize;
        damageLabel.setBounds(x, y, w, h);
        add(damageLabel);

        x = padding;
        y = y + damageLabel.getBounds().height;
        h = textSize;
        maxHP.setBounds(x, y, w, h);
        add(maxHP);

        x = padding;
        y = y + maxHP.getBounds().height;
        w = width;
        h = textSize;
        buyLabel.setBounds(x, y, w, h);
        add(buyLabel);

        x = width / 2;
        w = width / 2;
        h = textSize;
        upgradeLabel.setBounds(x, y, w, h);
        add(upgradeLabel);

        x = padding;
        y = y + buyLabel.getBounds().height;
        w = buyButtonDimension.width;
        h = buyButtonDimension.height;
        buyButton.setBounds(x, y, w, h);
        if (index == 0 || index == 1 || index == 2 || index == 3 || index == 4) {
            buyButton.setEnabled(false);
        }
        gw.getBuyButtons().add(buyButton);
        add(buyButton);

        x = width / 2;
        w = upgradeButtonDimension.width;
        h = upgradeButtonDimension.height;
        upgradeButton.setBounds(x, y, w, h);
        if (index == 0 || index == 1 || index == 2 || index == 3 || index == 4) {
            upgradeButton.setEnabled(false);
        }
        gw.getLevelUpButtons().add(upgradeButton);
        add(upgradeButton);
    }

    private void calculatePictureSize() {
        int realWidth = this.width - (2 * padding);
        pictureSize = (realWidth / 2) / 2;
    }

    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    public void updateUnit(Units unit, int i) {
        if (gw.getGame().getGamePhase() == GamePhase.PLAYER_ONE_DEFENDING_PHASE || gw.getGame().getGamePhase() == GamePhase.PLAYER_TWO_DEFENDING_PHASE) {
            picture = new ImageIcon(decideUnit(unit.getUnitTypes()));
            picLabel.setIcon(picture);
            if (unit.getLevel() != 10) {
                this.levelLabel.setText("Level " + unit.getLevel());
            } else {
                this.levelLabel.setText("Level MAX");
            }
            this.rangeLabel.setText("Range: " + unit.getRange());
            this.damageLabel.setText("Damage: " + unit.getDamage());
            this.maxHP.setText("Max HP: " + unit.getHp());
            this.buyLabel.setText("Buy for: " + unit.getCost());
            this.upgradeLabel.setText("Upgrade for: " + unit.getCost());
            //0 - 4 Fighter, 5 - 7 Tower
            if (index == 0 || index == 1 || index == 2 || index == 3 || index == 4) {
                gw.getBuyButtons().get(i).setEnabled(false);
                gw.getLevelUpButtons().get(i).setEnabled(false);
            } else {
                gw.getBuyButtons().get(i).setEnabled(true);
                gw.getLevelUpButtons().get(i).setEnabled(true);
            }
        } else if (gw.getGame().getGamePhase() == GamePhase.PLAYER_ONE_RECRUITING_PHASE || gw.getGame().getGamePhase() == GamePhase.PLAYER_TWO_RECRUITING_PHASE) {
            picture = new ImageIcon(decideUnit(unit.getUnitTypes()));
            picLabel.setIcon(picture);
            if (unit.getLevel() != 10) {
                this.levelLabel.setText("Level " + unit.getLevel());
            } else {
                this.levelLabel.setText("Level MAX");
            }
            this.rangeLabel.setText("Range: " + unit.getRange());
            this.damageLabel.setText("Damage: " + unit.getDamage());
            this.maxHP.setText("Max HP: " + unit.getHp());
            this.buyLabel.setText("Buy for: " + unit.getCost());
            this.upgradeLabel.setText("Upgrade for: " + unit.getCost());
            //0 - 4 Fighter, 5 - 7 Tower
            if (index == 5 || index == 6 || index == 7) {
                gw.getBuyButtons().get(i).setEnabled(false);
                gw.getLevelUpButtons().get(i).setEnabled(false);
            } else {
                gw.getBuyButtons().get(i).setEnabled(true);
                gw.getLevelUpButtons().get(i).setEnabled(true);
            }
        }
    }

    public String indexToString(int i){
        switch(i){
            case 1:
                return "climber";
            case 2:
                return "soldier";
            case 3:
                return "assassin";
            case 4:
                return "bomber";
            case 5:
                return "swimmer";
            case 6:
                return "sniper";
            case 7:
                return "tank";
            case 8:
                return "watchtower";
            default:
                return "";
        }
    }

    public void setUnit(Units unit) {
        this.picture = new ImageIcon(decideUnit(unit.getUnitTypes()));
        this.picLabel = new JLabel(picture);
        if (unit.getLevel() != 10) {
            this.levelLabel.setText("Level " + unit.getLevel());
        } else {
            this.levelLabel.setText("Level MAX");
        }
        this.rangeLabel.setText("Range: " + unit.getRange());
        this.damageLabel.setText("Damage: " + unit.getDamage());
        this.maxHP.setText("Max HP: " + unit.getHp());
        this.buyLabel.setText("Buy for: " + unit.getCost());
        this.upgradeLabel.setText("Upgrade for: " + unit.getCost());

        this.upgradeButton.setText("Level up");
        this.upgradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gw.getFrame().setCursor(Cursor.getDefaultCursor());
                if(gw.getGame().getActivePlayer().getGold() >= gw.getGame().getActivePlayer().getUnitInfo().get(index).getCost()){
                    gw.getGame().getActivePlayer().setGold(gw.getGame().getActivePlayer().getGold() - gw.getGame().getActivePlayer().getUnitInfo().get(index).getCost());
                    gw.getStatPanel().updatePlayerGold(gw.getGame().getActivePlayer());
                    if (gw.getGame().getActivePlayer().getUnitInfo().get(index).getLevel() <= 8) {
                        gw.getGame().getActivePlayer().getUnitInfo().get(index).incrementLevel(1);
                        updateUnit(gw.getGame().getActivePlayer().getUnitInfo().get(index), index);
                    } else {
                        gw.getGame().getActivePlayer().getUnitInfo().get(index).incrementLevel(1);
                        updateUnit(gw.getGame().getActivePlayer().getUnitInfo().get(index), index);
                        levelLabel.setText("Level MAX");
                        upgradeButton.setEnabled(false);
                        gw.getGame().getActivePlayer().getUnitInfo().get(index).toStringUnits();
                    }
                } else {
                    System.out.println("Nincs rá pénzed csóró.");
                }
            }
        });

        this.buyButton.setText("Buy");
        this.buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                if (gw.getGame().getActivePlayer().getUnitInfo().get(index).getUnitTypes().equals(UnitTypes.SNIPER) && gw.getGame().canPlayerBuyThis(gw.getGame().getActivePlayer(), UnitTypes.SNIPER)) {
                    Cursor c = toolkit.createCustomCursor(rl.sniperNeutral, new Point(0, 0), "img");
                    gw.getFrame().setCursor(c);
                    gw.setSelectedUnit(gw.getGame().getActivePlayer().getUnitInfo().get(index).getUnitTypes());
                } else if (gw.getGame().getActivePlayer().getUnitInfo().get(index).getUnitTypes().equals(UnitTypes.TANK) && gw.getGame().canPlayerBuyThis(gw.getGame().getActivePlayer(), UnitTypes.TANK)) {
                    Cursor c = toolkit.createCustomCursor(rl.tankNeutral, new Point(0, 0), "img");
                    gw.getFrame().setCursor(c);
                    gw.setSelectedUnit(gw.getGame().getActivePlayer().getUnitInfo().get(index).getUnitTypes());
                } else if (gw.getGame().getActivePlayer().getUnitInfo().get(index).getUnitTypes().equals(UnitTypes.WATCHTOWER) && gw.getGame().canPlayerBuyThis(gw.getGame().getActivePlayer(), UnitTypes.WATCHTOWER)) {
                    Cursor c = toolkit.createCustomCursor(rl.watchtowerNeutral, new Point(0, 0), "img");
                    gw.getFrame().setCursor(c);
                    gw.setSelectedUnit(gw.getGame().getActivePlayer().getUnitInfo().get(index).getUnitTypes());
                } else if (gw.getGame().getActivePlayer().getUnitInfo().get(index).getUnitTypes().equals(UnitTypes.ASSASSIN)) {
                    gw.getGame().buyFighter(UnitTypes.ASSASSIN);
                } else if (gw.getGame().getActivePlayer().getUnitInfo().get(index).getUnitTypes().equals(UnitTypes.CLIMBER)) {
                    gw.getGame().buyFighter(UnitTypes.CLIMBER);
                } else if (gw.getGame().getActivePlayer().getUnitInfo().get(index).getUnitTypes().equals(UnitTypes.BOMBER)) {
                    gw.getGame().buyFighter(UnitTypes.BOMBER);
                } else if (gw.getGame().getActivePlayer().getUnitInfo().get(index).getUnitTypes().equals(UnitTypes.SOLDIER)) {
                    gw.getGame().buyFighter(UnitTypes.SOLDIER);
                } else if (gw.getGame().getActivePlayer().getUnitInfo().get(index).getUnitTypes().equals(UnitTypes.SWIMMER)) {
                    gw.getGame().buyFighter(UnitTypes.SWIMMER);
                }
                gw.getStatPanel().updatePlayerGold(gw.getGame().getActivePlayer());
            }
        });
    }

    public Image decideUnit(UnitTypes kind) {
        Image ret = null;
        if (gw.getGame().getActivePlayer().getWitchPlayer() == 1) {
            if (kind.equals(UnitTypes.ASSASSIN)) {
                ret = rl.assassinPlayerOne;
            } else if (kind.equals(UnitTypes.BOMBER)) {
                ret = rl.bomberPlayerOne;
            } else if (kind.equals(UnitTypes.CLIMBER)) {
                ret = rl.climberPlayerOne;
            } else if (kind.equals(UnitTypes.SOLDIER)) {
                ret = rl.soldierPlayerOne;
            } else if (kind.equals(UnitTypes.SWIMMER)) {
                ret = rl.swimmerPlayerOne;
            } else if (kind.equals(UnitTypes.SNIPER)) {
                ret = rl.sniperPlayerOne;
            } else if (kind.equals(UnitTypes.WATCHTOWER)) {
                ret = rl.watchtowerPlayerOne;
            } else if (kind.equals(UnitTypes.TANK)) {
                ret = rl.tankPlayerOne;
            }
        } else {
            if (kind.equals(UnitTypes.ASSASSIN)) {
                ret = rl.assassinPlayerTwo;
            } else if (kind.equals(UnitTypes.BOMBER)) {
                ret = rl.bomberPlayerTwo;
            } else if (kind.equals(UnitTypes.CLIMBER)) {
                ret = rl.climberPlayerTwo;
            } else if (kind.equals(UnitTypes.SOLDIER)) {
                ret = rl.soldierPlayerTwo;
            } else if (kind.equals(UnitTypes.SWIMMER)) {
                ret = rl.swimmerPlayerTwo;
            } else if (kind.equals(UnitTypes.SNIPER)) {
                ret = rl.sniperPlayerTwo;
            } else if (kind.equals(UnitTypes.WATCHTOWER)) {
                ret = rl.watchtowerPlayerTwo;
            } else if (kind.equals(UnitTypes.TANK)) {
                ret = rl.tankPlayerTwo;
            }
        }
        ret = getScaledImage(ret, pictureSize, pictureSize); //Kis kép mérete
        return ret;
    }
}
