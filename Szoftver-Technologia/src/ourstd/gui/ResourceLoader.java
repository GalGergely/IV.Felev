package ourstd.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ResourceLoader {

    public final Image background = new ImageIcon("src\\ourstd\\png\\background.png").getImage();
    //Map
    public final Image placebleLand = new ImageIcon("src\\ourstd\\png\\placebleLand.png").getImage();
    public final Image land = new ImageIcon("src\\ourstd\\png\\land.png").getImage();
    public final Image mountain = new ImageIcon("src\\ourstd\\png\\mountain.png").getImage();
    public final Image water = new ImageIcon("src\\ourstd\\png\\water.png").getImage();
    public final Image castlePlayerOne = new ImageIcon("src\\ourstd\\png\\castle1.png").getImage();
    public final Image castlePlayerTwo = new ImageIcon("src\\ourstd\\png\\castle2.png").getImage();

    //Towers
    public final Image watchtowerPlayerOne = new ImageIcon("src\\ourstd\\png\\towers\\watchtower_1.png").getImage();
    public final Image watchtowerPlayerTwo = new ImageIcon("src\\ourstd\\png\\towers\\watchtower_2.png").getImage();
    public final Image tankPlayerOne = new ImageIcon("src\\ourstd\\png\\towers\\tank_1.png").getImage();
    public final Image tankPlayerTwo = new ImageIcon("src\\ourstd\\png\\towers\\tank_2.png").getImage();
    public final Image sniperPlayerOne = new ImageIcon("src\\ourstd\\png\\towers\\sniper_1.png").getImage();
    public final Image sniperPlayerTwo = new ImageIcon("src\\ourstd\\png\\towers\\sniper_2.png").getImage();
    public final Image sniperNeutral = new ImageIcon("src\\ourstd\\png\\towers\\sniper_neutral.png").getImage();
    public final Image tankNeutral = new ImageIcon("src\\ourstd\\png\\towers\\tank_neutral.png").getImage();
    public final Image watchtowerNeutral = new ImageIcon("src\\ourstd\\png\\towers\\watchtower_neutral.png").getImage();

    //Fighters
    public final Image assassinPlayerOne = new ImageIcon("src\\ourstd\\png\\units\\assassin_1.png").getImage();
    public final Image assassinPlayerTwo = new ImageIcon("src\\ourstd\\png\\units\\assassin_2.png").getImage();
    public final Image bomberPlayerOne = new ImageIcon("src\\ourstd\\png\\units\\bomber_1.png").getImage();
    public final Image bomberPlayerTwo = new ImageIcon("src\\ourstd\\png\\units\\bomber_2.png").getImage();
    public final Image climberPlayerOne = new ImageIcon("src\\ourstd\\png\\units\\climber_1.png").getImage();
    public final Image climberPlayerTwo = new ImageIcon("src\\ourstd\\png\\units\\climber_2.png").getImage();
    public final Image soldierPlayerOne = new ImageIcon("src\\ourstd\\png\\units\\soldier_1.png").getImage();
    public final Image soldierPlayerTwo = new ImageIcon("src\\ourstd\\png\\units\\soldier_2.png").getImage();
    public final Image swimmerPlayerOne = new ImageIcon("src\\ourstd\\png\\units\\swimmer_1.png").getImage();
    public final Image swimmerPlayerTwo = new ImageIcon("src\\ourstd\\png\\units\\swimmer_2.png").getImage();

    //Demaged Fighters
    public final Image assassinPlayerOneDemaged = new ImageIcon("src\\ourstd\\png\\units_demaged\\assassin_1.png").getImage();
    public final Image assassinPlayerTwoDemaged = new ImageIcon("src\\ourstd\\png\\units_demaged\\assassin_2.png").getImage();
    public final Image bomberPlayerOneDemaged = new ImageIcon("src\\ourstd\\png\\units_demaged\\bomber_1.png").getImage();
    public final Image bomberPlayerTwoDemaged = new ImageIcon("src\\ourstd\\png\\units_demaged\\bomber_2.png").getImage();
    public final Image climberPlayerOneDemaged = new ImageIcon("src\\ourstd\\png\\units_demaged\\climber_1.png").getImage();
    public final Image climberPlayerTwoDemaged = new ImageIcon("src\\ourstd\\png\\units_demaged\\climber_2.png").getImage();
    public final Image soldierPlayerOneDemaged = new ImageIcon("src\\ourstd\\png\\units_demaged\\soldier_1.png").getImage();
    public final Image soldierPlayerTwoDemaged = new ImageIcon("src\\ourstd\\png\\units_demaged\\soldier_2.png").getImage();
    public final Image swimmerPlayerOneDemaged = new ImageIcon("src\\ourstd\\png\\units_demaged\\swimmer_1.png").getImage();
    public final Image swimmerPlayerTwoDemaged = new ImageIcon("src\\ourstd\\png\\units_demaged\\swimmer_2.png").getImage();

    //hpbars
    public final Image hpbar1 = new ImageIcon("src\\ourstd\\png\\hpbars\\1.png").getImage();
    public final Image hpbar2 = new ImageIcon("src\\ourstd\\png\\hpbars\\2.png").getImage();
    public final Image hpbar3 = new ImageIcon("src\\ourstd\\png\\hpbars\\3.png").getImage();
    public final Image hpbar4 = new ImageIcon("src\\ourstd\\png\\hpbars\\4.png").getImage();
    public final Image hpbar5 = new ImageIcon("src\\ourstd\\png\\hpbars\\5.png").getImage();



     //sounds
    File uff = new File("src\\ourstd\\sounds\\uff.wav");
    File bigUff = new File("src\\ourstd\\sounds\\biguff.wav");
}
