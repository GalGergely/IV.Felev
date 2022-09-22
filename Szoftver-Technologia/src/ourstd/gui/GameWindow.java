package ourstd.gui;

import ourstd.model.Game;
import ourstd.model.Player;
import ourstd.model.Position;
import ourstd.model.map.*;
import ourstd.model.units.UnitTypes;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameWindow {

    //JComponents
    private final JFrame frame;

    private final ArrayList<UnitPanel> UnitPanels = new ArrayList<>();
    private ArrayList<JButton> buyButtons = new ArrayList<>();
    private ArrayList<JButton> levelUpButtons = new ArrayList<>();
    private final StatPanel statPanel;

    private static final ResourceLoader rl = new ResourceLoader();
    public static int tileSize;
    protected static Game game;
    protected static Dimension mapDimension;
    private static Pathfinder pf;

    private final Dimension leftPanel;
    private final Dimension rightPanel;

    private UnitTypes selectedUnit;
    private static boolean toggledMap = true;

    /**
     * A reszletekert a kodban hasznalt kommenteket keresd.
     * @param p1Name player 1 nev
     * @param p2Name player 2 nev
     * @param filename fajlnev
     * @throws IOException IOException
     * @throws NullPointerException NullPointerException
     */
    public GameWindow(String p1Name, String p2Name, String filename) throws IOException,NullPointerException {
        game = new Game();

        if (filename != null){
            game.readMap(filename);
        }

        game.setPlayerNames(p1Name, p2Name);
        game.setActivePlayer(1);

        frame = new JFrame("Game Window");
        frame.setUndecorated(true);
        frame.setResizable(false);
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();
        device.setFullScreenWindow(frame);
        frame.addMouseListener(new MyMouseListener());

        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        //Ekkora a map:
        mapDimension = new Dimension(screenSize.height, screenSize.height);

        //Itt kiszámoljuk, hogy mekkora a bal és a jobb oldal:
        int notMap = screenSize.width - mapDimension.width;
        int leftPanelWidth = notMap / 2;
        int rightPanelWidth = notMap / 2;
        leftPanel = new Dimension(leftPanelWidth, screenSize.height);
        rightPanel = new Dimension(rightPanelWidth, screenSize.height);

        //Kiszámoljuk a tileSize-ot:
        tileSize = mapDimension.height / game.getMap().getSize();

        //A játék bal oldala:
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        UnitPanel uPanel;
        JPanel unitsPanel = new JPanel(new GridLayout(5, 2));
        for (int i = 0; i < 8; i++) {
            uPanel = new UnitPanel((leftPanel.width / 2), (leftPanel.height / 4), i, this);
            UnitPanels.add(uPanel); //ArrayList
            unitsPanel.add(uPanel); //JPanel
        }
        JPanel exitPanel = new JPanel(null);
        exitPanel.setPreferredSize(new Dimension((leftPanel.width / 2), (leftPanel.height / 4)));
        int widthForComponents = 75;
        int heightForComponents = 25;
        exitButton.setBounds(((leftPanel.width / 2)/2) - widthForComponents,((leftPanel.height / 4)/2) - heightForComponents,widthForComponents ,heightForComponents);
        exitPanel.add(exitButton);
        unitsPanel.add(exitPanel);

        JPanel toggleDeposibility = new JPanel(null);
        toggleDeposibility.setPreferredSize(new Dimension((leftPanel.width / 2), (leftPanel.height / 4)));
        JLabel turretLabel = new JLabel("Toggle where to place turret:",SwingConstants.CENTER);
        JButton toggleOn = new JButton("On");
        JButton toggleOff = new JButton("Off");
        toggleOff.setEnabled(false);

        widthForComponents = ((leftPanel.width / 2)/2);
        turretLabel.setBounds(0,((leftPanel.height / 4)/3) - heightForComponents, widthForComponents*2 ,heightForComponents);

        widthForComponents = 70;
        toggleOn.setBounds(((leftPanel.width / 10)),((leftPanel.height / 4)/2) - heightForComponents, widthForComponents ,heightForComponents);
        toggleOff.setBounds((leftPanel.width / 10) + widthForComponents + 5,((leftPanel.height / 4)/2) - heightForComponents, widthForComponents ,heightForComponents);

        toggleOn.addActionListener(e -> {
            toggleOn.setEnabled(false);
            toggleOff.setEnabled(true);
            toggledMap = !toggledMap;
        });

        toggleOff.addActionListener(e -> {
            toggleOff.setEnabled(false);
            toggleOn.setEnabled(true);
            toggledMap = !toggledMap;
        });

        toggleDeposibility.add(turretLabel);
        toggleDeposibility.add(toggleOn);
        toggleDeposibility.add(toggleOff);
        unitsPanel.add(toggleDeposibility);

        //Játék közepe, a map:
        DrawPanel gameTablePanel = new DrawPanel(game.getMap(), this);
        gameTablePanel.setPreferredSize(mapDimension);

        //A játék jobb oldala:
        statPanel = new StatPanel(this, rightPanel.width, rightPanel.height);

        pane.add(unitsPanel, BorderLayout.LINE_START);
        pane.add(gameTablePanel, BorderLayout.CENTER);
        pane.add(statPanel, BorderLayout.LINE_END);
        frame.setVisible(true);
    }

    public void setSelectedUnit(UnitTypes selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Game getGame() {
        return game;
    }

    public List<UnitPanel> getUnitPanels() {
        return UnitPanels;
    }

    public List<JButton> getBuyButtons() {
        return buyButtons;
    }

    public List<JButton> getLevelUpButtons() {
        return levelUpButtons;
    }

    public StatPanel getStatPanel() {
        return this.statPanel;
    }

    /** Jatek vege
     * @param whoWon a nyertes jatekos
     */
    public void gameEnd(Player whoWon){
        this.frame.dispose();
        WinnerWindow winnerWindow;
        winnerWindow = new WinnerWindow(whoWon.getName());
    }

    public static class DrawPanel extends JPanel implements ActionListener {

        protected final Map m;
        private final GameWindow gw;

        public DrawPanel(Map map, GameWindow gw) {
            this.m = map;
            this.gw = gw;
            Timer timer = new Timer(1, this);
            timer.start();
        }

        @Override
        public void paint(Graphics g) {
            int w = mapDimension.height / m.getSize();
            int h = mapDimension.height / m.getSize();
            for (int i = 0; i < m.getSize(); i++) {
                for (int j = 0; j < m.getSize(); j++) {
                    Image img = decideBoardPiece(i, j);
                   /* if (game.getMap().getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.CASTLE1)){
                        System.out.println("itt talaltam castlet:" +i +"  " + j);
                    }
                    if (game.getMap().getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.CASTLE2)){
                        System.out.println("itt talaltam castlet:" +i +"  " + j);
                    }*/
                    paintComponent(g, i * w, j * h, img, w, h);
                }
            }
            //player 1 minden tower kirajzolas
            for (int i = 0; i < game.getPlayerOne().getTowers().size(); i++) {
                if (game.getPlayerOne().getTowers().get(i).getUnitTypes().equals(UnitTypes.WATCHTOWER)) {
                    paintComponent(g, game.getPlayerOne().getTowers().get(i).getPosition().getX() * w, game.getPlayerOne().getTowers().get(i).getPosition().getY() * h, rl.watchtowerPlayerOne, w, h);
                } else if (game.getPlayerOne().getTowers().get(i).getUnitTypes().equals(UnitTypes.TANK)) {
                    paintComponent(g, game.getPlayerOne().getTowers().get(i).getPosition().getX() * w, game.getPlayerOne().getTowers().get(i).getPosition().getY() * h, rl.tankPlayerOne, w, h);
                } else if (game.getPlayerOne().getTowers().get(i).getUnitTypes().equals(UnitTypes.SNIPER)) {
                    paintComponent(g, game.getPlayerOne().getTowers().get(i).getPosition().getX() * w, game.getPlayerOne().getTowers().get(i).getPosition().getY() * h, rl.sniperPlayerOne, w, h);
                }
            }
            // player 1 minden unit kirazolas
            for (int i = 0; i < game.getPlayerOne().getFighters().size(); i++) {
                if (game.getPlayerOneFighterOnIndex(i).getDamagedForPixels()==game.getPlayerOneFighterOnIndex(i).getDamagePixelCounter()){
                    if (game.getPlayerOneFighterOnIndex(i).getUnitTypes().equals(UnitTypes.ASSASSIN)) {
                       // System.out.println(game.getPlayerOneFighterOnIndex(i).getPosition().getX() + " az asszasin x kordinataja es az Y: "+ game.getPlayerOneFighterOnIndex(i).getPosition().getX());
                        paintComponent(g, game.getPlayerOneFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerOneFighterOnIndex(i).getPixelPosition().getY(), rl.assassinPlayerOne, w, h);
                    } else if (game.getPlayerOneFighterOnIndex(i).getUnitTypes().equals(UnitTypes.SOLDIER)) {
                        paintComponent(g, game.getPlayerOneFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerOneFighterOnIndex(i).getPixelPosition().getY(), rl.soldierPlayerOne, w, h);
                    } else if (game.getPlayerOneFighterOnIndex(i).getUnitTypes().equals(UnitTypes.BOMBER)) {
                        paintComponent(g, game.getPlayerOneFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerOneFighterOnIndex(i).getPixelPosition().getY(), rl.bomberPlayerOne, w, h);
                    } else if (game.getPlayerOneFighterOnIndex(i).getUnitTypes().equals(UnitTypes.SWIMMER)) {
                        paintComponent(g, game.getPlayerOneFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerOneFighterOnIndex(i).getPixelPosition().getY(), rl.swimmerPlayerOne, w, h);
                    } else if (game.getPlayerOneFighterOnIndex(i).getUnitTypes().equals(UnitTypes.CLIMBER)) {
                        paintComponent(g, game.getPlayerOneFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerOneFighterOnIndex(i).getPixelPosition().getY(), rl.climberPlayerOne, w, h);
                    }
                } else {
                    if (game.getPlayerOneFighterOnIndex(i).getUnitTypes().equals(UnitTypes.ASSASSIN)) {
                        paintComponent(g, game.getPlayerOneFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerOneFighterOnIndex(i).getPixelPosition().getY(), rl.assassinPlayerOneDemaged, w, h);
                    } else if (game.getPlayerOneFighterOnIndex(i).getUnitTypes().equals(UnitTypes.SOLDIER)) {
                        paintComponent(g, game.getPlayerOneFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerOneFighterOnIndex(i).getPixelPosition().getY(), rl.soldierPlayerOneDemaged, w, h);
                    } else if (game.getPlayerOneFighterOnIndex(i).getUnitTypes().equals(UnitTypes.BOMBER)) {
                        paintComponent(g, game.getPlayerOneFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerOneFighterOnIndex(i).getPixelPosition().getY(), rl.bomberPlayerOneDemaged, w, h);
                    } else if (game.getPlayerOneFighterOnIndex(i).getUnitTypes().equals(UnitTypes.SWIMMER)) {
                        paintComponent(g, game.getPlayerOneFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerOneFighterOnIndex(i).getPixelPosition().getY(), rl.swimmerPlayerOneDemaged, w, h);
                    } else if (game.getPlayerOneFighterOnIndex(i).getUnitTypes().equals(UnitTypes.CLIMBER)) {
                        paintComponent(g, game.getPlayerOneFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerOneFighterOnIndex(i).getPixelPosition().getY(), rl.climberPlayerOneDemaged, w, h);
                    }
                    game.getPlayerOneFighterOnIndex(i).addOneToDamagePixelCounter();
                }

            }
            // player 2 minden unit kirazolas
            for (int i = 0; i < game.getPlayerTwo().getFighters().size(); i++) {
                if (game.getPlayerTwoFighterOnIndex(i).getDamagedForPixels()==game.getPlayerTwoFighterOnIndex(i).getDamagePixelCounter()) {
                    if (game.getPlayerTwoFighterOnIndex(i).getUnitTypes().equals(UnitTypes.ASSASSIN)) {
                        //System.out.println(game.getPlayerTwoFighterOnIndex(i).getPosition().getX() + " az asszasin player two x kordinataja es az Y: "+ game.getPlayerTwoFighterOnIndex(i).getPosition().getX());
                        paintComponent(g, game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getY(), rl.assassinPlayerTwo, w, h);
                    } else if (game.getPlayerTwoFighterOnIndex(i).getUnitTypes().equals(UnitTypes.SOLDIER)) {
                        paintComponent(g, game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getY(), rl.soldierPlayerTwo, w, h);
                    } else if (game.getPlayerTwoFighterOnIndex(i).getUnitTypes().equals(UnitTypes.BOMBER)) {
                        paintComponent(g, game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getY(), rl.bomberPlayerTwo, w, h);
                    } else if (game.getPlayerTwoFighterOnIndex(i).getUnitTypes().equals(UnitTypes.SWIMMER)) {
                        paintComponent(g, game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getY(), rl.swimmerPlayerTwo, w, h);
                    } else if (game.getPlayerTwoFighterOnIndex(i).getUnitTypes().equals(UnitTypes.CLIMBER)) {
                        paintComponent(g, game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getY(), rl.climberPlayerTwo, w, h);
                    }
                } else {
                    if (game.getPlayerTwoFighterOnIndex(i).getUnitTypes().equals(UnitTypes.ASSASSIN)) {
                        paintComponent(g, game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getY(), rl.assassinPlayerTwoDemaged, w, h);
                    } else if (game.getPlayerTwoFighterOnIndex(i).getUnitTypes().equals(UnitTypes.SOLDIER)) {
                        paintComponent(g, game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getY(), rl.soldierPlayerTwoDemaged, w, h);
                    } else if (game.getPlayerTwoFighterOnIndex(i).getUnitTypes().equals(UnitTypes.BOMBER)) {
                        paintComponent(g, game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getY(), rl.bomberPlayerTwoDemaged, w, h);
                    } else if (game.getPlayerTwoFighterOnIndex(i).getUnitTypes().equals(UnitTypes.SWIMMER)) {
                        paintComponent(g, game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getY(), rl.swimmerPlayerTwoDemaged, w, h);
                    } else if (game.getPlayerTwoFighterOnIndex(i).getUnitTypes().equals(UnitTypes.CLIMBER)) {
                        paintComponent(g, game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getX(), game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getY(), rl.climberPlayerTwoDemaged, w, h);
                    }
                    game.getPlayerTwoFighterOnIndex(i).addOneToDamagePixelCounter();
                }

            }
            //player 2 minden tower kirajzolas
            for (int i = 0; i < game.getPlayerTwo().getTowers().size(); i++) {
                if (game.getPlayerTwo().getTowers().get(i).getUnitTypes().equals(UnitTypes.WATCHTOWER)) {
                    paintComponent(g, game.getPlayerTwo().getTowers().get(i).getPosition().getX() * w, game.getPlayerTwo().getTowers().get(i).getPosition().getY() * h, rl.watchtowerPlayerTwo, w, h);
                } else if (game.getPlayerTwo().getTowers().get(i).getUnitTypes().equals(UnitTypes.TANK)) {
                    paintComponent(g, game.getPlayerTwo().getTowers().get(i).getPosition().getX() * w, game.getPlayerTwo().getTowers().get(i).getPosition().getY() * h, rl.tankPlayerTwo, w, h);
                } else if (game.getPlayerTwo().getTowers().get(i).getUnitTypes().equals(UnitTypes.SNIPER)) {
                    paintComponent(g, game.getPlayerTwo().getTowers().get(i).getPosition().getX() * w, game.getPlayerTwo().getTowers().get(i).getPosition().getY() * h, rl.sniperPlayerTwo, w, h);
                }
            }
            //paint castle hp bars
            for (int i = 0; i < m.getSize(); i++) {
                for (int j = 0; j < m.getSize(); j++) {
                    Image img = decideBoardPiece(i, j);
                    if(img.equals(rl.castlePlayerOne) || img.equals(rl.castlePlayerTwo))
                    {
                        Image dependableHPbar = decideWhatHPBar(m.getGameBoard().get(i).get(j));
                        paintComponent(g, i * w, (j * h) + h/10*9, dependableHPbar, w, h/2);
                    }
                }
            }
        }

        /** A castle eletereje alapjan eldonti meelyik hpo bart kell rajzolni
         * @param castle a var
         * @return egy keppel
         */
        private Image decideWhatHPBar(MapAttributes castle) {
            float tmp = castle.getMaxHealthPoints() / 5;
            if(castle.getHPinInt() > tmp*4) {
                return rl.hpbar1;
            } else if (castle.getHPinInt() > tmp*3) {
                return rl.hpbar2;
            } else if (castle.getHPinInt() > tmp*2) {
                return rl.hpbar3;
            } else if (castle.getHPinInt() > tmp) {
                return rl.hpbar4;
            } else {
                return rl.hpbar5;
            }
        }

        /** Eldonti melyik boardpiecet kell kirajzolni
         * @param i x
         * @param j y
         * @return a kep
         */
        private Image decideBoardPiece(int i, int j) {
            MapAttributes ma = m.getMap().get(i).get(j);
            if(toggledMap){
                if (ma.getMapAttribute().equals(MapAttributesTypes.LAND)) {
                    return rl.land;
                } else if (ma.getMapAttribute().equals(MapAttributesTypes.CASTLE1)) {
                    return rl.castlePlayerOne;
                } else if (ma.getMapAttribute().equals(MapAttributesTypes.CASTLE2)) {
                    return rl.castlePlayerTwo;
                } else if (ma.getMapAttribute().equals(MapAttributesTypes.WATER)) {
                    return rl.water;
                } else {
                    return rl.mountain;
                }
            } else {
                if (ma.getMapAttribute().equals(MapAttributesTypes.LAND)) {
                    if (ma.isBlocked()) {
                        return rl.land;
                    } else if(ma.isAvailable() && !ma.isNextToCastle()) {
                        return rl.placebleLand;
                    } else {
                        return rl.land;
                    }
                } else if (ma.getMapAttribute().equals(MapAttributesTypes.CASTLE1)) {
                    return rl.castlePlayerOne;
                } else if (ma.getMapAttribute().equals(MapAttributesTypes.CASTLE2)) {
                    return rl.castlePlayerTwo;
                } else if (ma.getMapAttribute().equals(MapAttributesTypes.WATER)) {
                    return rl.water;
                } else {
                    return rl.mountain;
                }
            }
        }

        private void paintComponent(Graphics g, int x, int y, Image img, int width, int height) {
            g.drawImage(img, x, y, width, height, null);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean takesDamage = false;
            Position whereToMove;
            for (int i = 0; i < game.getPlayerOne().getFighters().size(); i++) {
                if(game.getPlayerOneFighterOnIndex(i).getReachedEnd()){
                    game.damageCastle( game.getPlayerOne().getFighters().get(i).getDamage(),2);
                    if(game.isPlayerOneTheWinner()){
                        gw.gameEnd(game.getPlayerOne());
                    }
                    gw.getStatPanel().updateRightPanel();
                    game.getPlayerOne().getFighters().remove(i);
                    try{
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(rl.bigUff));
                        clip.start();
                    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException eeeeee){
                        eeeeee.printStackTrace();
                    }
                } else {
                    if (game.getPlayerOneFighterOnIndex(i).getMoveCounter() < game.getPlayerOneFighterOnIndex(i).getMaxMovesOnRound()) {
                        whereToMove = whereToMovePlayerOne(i);
                        setVelocityPlayerOne(whereToMove, i);
                        if (!game.getPlayerOneFighterOnIndex(i).getPixelPosition().equals(whereToMove, tileSize, game.getPlayerOneFighterOnIndex(i).getBaseVelocity())) {
                            int x = game.getPlayerOneFighterOnIndex(i).getPixelPosition().getX() + game.getPlayerOneFighterOnIndex(i).getXVelocity();
                            int y = game.getPlayerOneFighterOnIndex(i).getPixelPosition().getY() + game.getPlayerOneFighterOnIndex(i).getYVelocity();
                            game.getPlayerOneFighterOnIndex(i).setPixelPosition(x, y);

                        } else {
                            game.getPlayerOneFighterOnIndex(i).setPosition(whereToMove.getX(), whereToMove.getY());
                            game.getPlayerOneFighterOnIndex(i).addToMoveCounter(1);
                            takesDamage = game.fighterTakesDamage(game.getPlayerOneFighterOnIndex(i), 1);
                            if(takesDamage) {
                                game.getPlayerOneFighterOnIndex(i).setDamagePixelCounter(0);
                                try{
                                    Clip clip = AudioSystem.getClip();
                                    clip.open(AudioSystem.getAudioInputStream(rl.uff));
                                    clip.start();
                                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException eeeeee){
                                    eeeeee.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < game.getPlayerTwo().getFighters().size(); i++) {
                if(game.getPlayerTwoFighterOnIndex(i).getReachedEnd()){
                    game.damageCastle( game.getPlayerTwo().getFighters().get(i).getDamage(),1);
                    if(game.isPlayerTwoTheWinner()){
                        gw.gameEnd(game.getPlayerTwo());
                    }
                    gw.getStatPanel().updateRightPanel();
                    game.getPlayerTwo().getFighters().remove(i);
                    try{
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(rl.bigUff));
                        clip.start();
                    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException eeeeee){
                        eeeeee.printStackTrace();
                    }
                } else {
                    if (game.getPlayerTwoFighterOnIndex(i).getMoveCounter() < game.getPlayerTwoFighterOnIndex(i).getMaxMovesOnRound()) {
                        whereToMove = whereToMovePlayerTwo(i);
                        setVelocityPlayerTwo(whereToMove, i);
                        if (!game.getPlayerTwoFighterOnIndex(i).getPixelPosition().equals(whereToMove, tileSize, game.getPlayerTwoFighterOnIndex(i).getBaseVelocity())) {
                            int x = game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getX() + game.getPlayerTwoFighterOnIndex(i).getXVelocity();
                            int y = game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getY() + game.getPlayerTwoFighterOnIndex(i).getYVelocity();
                            game.getPlayerTwoFighterOnIndex(i).setPixelPosition(x, y);

                        } else {
                            game.getPlayerTwoFighterOnIndex(i).setPosition(whereToMove.getX(), whereToMove.getY());
                            game.getPlayerTwoFighterOnIndex(i).addToMoveCounter(1);
                            takesDamage = game.fighterTakesDamage(game.getPlayerTwoFighterOnIndex(i), 2);
                            if(takesDamage) {
                                game.getPlayerTwoFighterOnIndex(i).setDamagePixelCounter(0);
                                try{
                                    Clip clip = AudioSystem.getClip();
                                    clip.open(AudioSystem.getAudioInputStream(rl.uff));
                                    clip.start();
                                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException eeeeee){
                                    eeeeee.printStackTrace();
                                }
                            }
                        }
                    }
                }
                }
            repaint();
        }

        private void setVelocityPlayerOne(Position whereToMove, int i) {
            if (whereToMove.getX() * tileSize < game.getPlayerOneFighterOnIndex(i).getPixelPosition().getX()) {
                game.getPlayerOneFighterOnIndex(i).setXVelocity(-1 * game.getPlayerOneFighterOnIndex(i).getBaseVelocity());
            } else if (whereToMove.getX() * tileSize > game.getPlayerOneFighterOnIndex(i).getPixelPosition().getX()) {
                game.getPlayerOneFighterOnIndex(i).setXVelocity(game.getPlayerOneFighterOnIndex(i).getBaseVelocity());
            } else {
                game.getPlayerOneFighterOnIndex(i).setXVelocity(0);
            }
            if (whereToMove.getY() * tileSize < game.getPlayerOneFighterOnIndex(i).getPixelPosition().getY()) {
                game.getPlayerOneFighterOnIndex(i).setYVelocity(-1 * game.getPlayerOneFighterOnIndex(i).getBaseVelocity());
            } else if (whereToMove.getY() * tileSize > game.getPlayerOneFighterOnIndex(i).getPixelPosition().getY()) {
                game.getPlayerOneFighterOnIndex(i).setYVelocity(game.getPlayerOneFighterOnIndex(i).getBaseVelocity());
            } else {
                game.getPlayerOneFighterOnIndex(i).setYVelocity(0);
            }
        }

        private void setVelocityPlayerTwo(Position whereToMove, int i) {
            if (whereToMove.getX() * tileSize < game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getX()) {
                game.getPlayerTwoFighterOnIndex(i).setXVelocity(-1 * game.getPlayerTwoFighterOnIndex(i).getBaseVelocity());
            } else if (whereToMove.getX() * tileSize > game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getX()) {
                game.getPlayerTwoFighterOnIndex(i).setXVelocity(game.getPlayerTwoFighterOnIndex(i).getBaseVelocity());
            } else {
                game.getPlayerTwoFighterOnIndex(i).setXVelocity(0);
            }
            if (whereToMove.getY() * tileSize < game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getY()) {
                game.getPlayerTwoFighterOnIndex(i).setYVelocity(-1 * game.getPlayerTwoFighterOnIndex(i).getBaseVelocity());
            } else if (whereToMove.getY() * tileSize > game.getPlayerTwoFighterOnIndex(i).getPixelPosition().getY()) {
                game.getPlayerTwoFighterOnIndex(i).setYVelocity(game.getPlayerTwoFighterOnIndex(i).getBaseVelocity());
            } else {
                game.getPlayerTwoFighterOnIndex(i).setYVelocity(0);
            }
        }

        /**
         * @param i iteracio
         * @return hova kell lepnie az adott unitank
         */
        private Position whereToMovePlayerOne(int i) {
            Position returnPosition = new Position(0, 0);
            pf = new Pathfinder(game.getMap(), game.getPlayerOneFighterOnIndex(i), 1);
            Node[][] nodeMap = pf.getNodeMap();
                boolean isPathFound = false;
                for (int j = -1; j <= 1; j++) {
                    for (int k = -1; k <= 1; k++) {
                        if (nodeMap[pf.getFighter().getPosition().getX() + j][pf.getFighter().getPosition().getY() + k].getType() == 5) {
                            returnPosition.setX((pf.getFighter().getPosition().getX() + j));
                            returnPosition.setY((pf.getFighter().getPosition().getY() + k));
                            isPathFound = true;
                        } else if (nodeMap[pf.getFighter().getPosition().getX() + j][pf.getFighter().getPosition().getY() + k].getType() == 1) {
                            returnPosition.setX((pf.getFighter().getPosition().getX()+ j));
                            returnPosition.setY((pf.getFighter().getPosition().getY()+ k));
                            isPathFound = true;
                        }
                    }
                }
                if (!isPathFound) {
                    game.getPlayerOneFighterOnIndex(i).setReachedEnd(true);
                    returnPosition.setX((pf.getFighter().getPosition().getX()));
                    returnPosition.setY((pf.getFighter().getPosition().getY()));
                }
            return returnPosition;
        }

        /**
         * @param i iteracio
         * @return hova kell lepnie az adott unitank
         */
        private Position whereToMovePlayerTwo(int i) {
            Position returnPosition = new Position(0, 0);
            pf = new Pathfinder(game.getMap(), game.getPlayerTwoFighterOnIndex(i), 2);
            Node[][] nodeMap = pf.getNodeMap();
            boolean isPathFound = false;
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (nodeMap[pf.getFighter().getPosition().getX() + j][pf.getFighter().getPosition().getY() + k].getType() == 5) {
                        returnPosition.setX((pf.getFighter().getPosition().getX() + j));
                        returnPosition.setY((pf.getFighter().getPosition().getY() + k));
                        isPathFound = true;
                    } else if (nodeMap[pf.getFighter().getPosition().getX() + j][pf.getFighter().getPosition().getY() + k].getType() == 1) {
                        returnPosition.setX((pf.getFighter().getPosition().getX() + j));
                        returnPosition.setY((pf.getFighter().getPosition().getY() + k));
                        isPathFound = true;
                    }
                }
            }
            if (!isPathFound) {
                game.getPlayerTwoFighterOnIndex(i).setReachedEnd(true);
                returnPosition.setX((pf.getFighter().getPosition().getX()));
                returnPosition.setY((pf.getFighter().getPosition().getY()));
            }
            return returnPosition;
        }
    }

    public  class MyMouseListener extends JFrame implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            decideWitchTileIsClicked(e.getX(), e.getY());
            frame.setCursor(Cursor.getDefaultCursor());
            printInfoPanel(e.getX(),e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO document why this method is empty
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO document why this method is empty
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO document why this method is empty
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO document why this method is empty
        }

        /** eldonti melyik palyareszre kattintottunk
         * @param x x
         * @param y y
         */
        private void decideWitchTileIsClicked(int x, int y) {
            if(selectedUnit == null){
                System.out.println("Melyik turretet akarod lerakni te barom?");
                return;
            }
            x -= leftPanel.width;
            if (x > 0  && y > 0 && x < mapDimension.width) {
                x = (x / tileSize);
                y = (y / tileSize);
                if (game.decideIfTurretPlacable(x, y) == 1 && selectedUnit != null) {
                    game.buyTurret(x, y, selectedUnit);
                    statPanel.updateRightPanel();
                } else {
                    System.out.println("Nooooooooooooooooo");
                }
            }
            selectedUnit = null;
        }

        private void printInfoPanel(int x ,int y){
            statPanel.getInfoLabel().setText("");
            x -= leftPanel.width;
            if (x > 0  && y > 0 && x < mapDimension.width) {
                x = (x / tileSize);
                y = (y / tileSize);
            }
            for (int i=0;i<game.getPlayerOne().getTowers().size();i++){
                if (game.getPlayerOne().getTowers().get(i).getPosition().getX()==x && game.getPlayerOne().getTowers().get(i).getPosition().getY()==y){
                    statPanel.getInfoLabel().setText("Owner: " + game.getPlayerOne().getName() + " Hp:" + game.getPlayerOne().getTowers().get(i).getHp());
                }
            }

            for (int i=0;i<game.getPlayerTwo().getTowers().size();i++){
                if (game.getPlayerTwo().getTowers().get(i).getPosition().getX()==x && game.getPlayerTwo().getTowers().get(i).getPosition().getY()==y){
                    statPanel.getInfoLabel().setText("Owner: " + game.getPlayerTwo().getName() + " Hp:" + game.getPlayerTwo().getTowers().get(i).getHp());
                }
            }
        }
    }
}

