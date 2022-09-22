package ourstd.model;

import ourstd.model.map.*;
import ourstd.model.units.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static ourstd.gui.GameWindow.tileSize;

public class Game {

    private final Balance bl = new Balance();

    private final int mapSize = bl.getMapSize();

    private Map map ;
    private final Player playerOne;
    private final Player playerTwo;
    private Player activePlayer;
    private int roundCounter = 1;
    private GamePhase gamePhase;
    private boolean isOnUnit = false;

    public Game() {
        map = new Map(mapSize);
        this.gamePhase = GamePhase.PLAYER_ONE_DEFENDING_PHASE;
        this.playerOne = new Player(map, 1);
        this.playerTwo = new Player(map, 2);
        this.activePlayer = this.playerOne;
        this.playerOne.setCastle(this.map.getCastleForPlayer(1));
        this.playerTwo.setCastle(this.map.getCastleForPlayer(2));
    }

    /**
     * megkeresi melyik unitot valasztották, es visszaadja azt
     *
     * @param selectedUnit - kiválasztott unit
     * @return az informaciot a kivalasztott unitrol
     */
    public Units findSelectedUnit(UnitTypes selectedUnit) {
        for (int i = 0; i < this.getActivePlayer().getUnitInfo().size(); i++) {
            if (this.getActivePlayer().getUnitInfo().get(i).getUnitTypes().equals(selectedUnit)) {
                return this.getActivePlayer().getUnitInfo().get(i);
            }
        }
        return null;
    }

    /**
     * Amennyiben minden kondicio teljesul, lerakta a turetet.
     *
     * @param x            x kordinata
     * @param y            y kordinata
     * @param selectedUnit kivalasztott unit
     */
    public void buyTurret(int x, int y, UnitTypes selectedUnit) {
        isOnUnit = isOnUnit(x, y);
        if (!isOnUnit) {
            if(!map.getGameBoard().get(x).get(y).isBlocked()){
                Map tmpMap = map;
                tmpMap.getGameBoard().get(x).get(y).setAvailable(false);
                Soldier tmpSoldier = new Soldier();
                tmpSoldier.setPosition(playerOne.getCastlePosition().getX(), playerOne.getCastlePosition().getY());
                Pathfinder pf = new Pathfinder(tmpMap, tmpSoldier, 1);
                Node[][] nodeMap = pf.getNodeMap();
                placeUnitAndRemoveGold(x, y, selectedUnit, nodeMap);
            }
        }
    }

    /**
     * megnezi hogy a turet lerakás nem zárja el az utatm ha nem, akkor lerakja a tornyot es elveszi a penzt
     *
     * @param x            x kordinata
     * @param y            y kordinata
     * @param selectedUnit kivalasztott unit
     * @param nodeMap      utvonalterv
     */
    private void placeUnitAndRemoveGold(int x, int y, UnitTypes selectedUnit, Node[][] nodeMap) {
        if (isCanMoveOnPath(nodeMap)) {
            map.getGameBoard().get(x).get(y).setAvailable(false);
            Units tower = findSelectedUnit(selectedUnit);
            Towers newTower;
            if (selectedUnit.equals(UnitTypes.SNIPER)) {
                newTower = new Sniper();
                initTowerBy(tower, newTower);
            } else if (selectedUnit.equals(UnitTypes.TANK)) {
                newTower = new Tank();
                initTowerBy(tower, newTower);
            } else {
                newTower = new Watchtower();
                initTowerBy(tower, newTower);
            }
            if (activePlayer.getGold() - newTower.getCost() >= 0) {
                activePlayer.removeGold(newTower.getCost());
                newTower.setPosition(new Position(x, y));
                activePlayer.buyTower(newTower);
                map.getGameBoard().get(x).get(y).setAvailable(false);
            }
        } else {
            map.getGameBoard().get(x).get(y).setAvailable(true);
            map.getGameBoard().get(x).get(y).setBlocked(true);
        }
    }

    /**
     * megnézi tud e mozdulni a unit, van e szabad utvonala.
     *
     * @param nodeMap pathfinding terkepe
     * @return igaz, ha tud mozdulbi, hamis ha nem
     */
    private boolean isCanMoveOnPath(Node[][] nodeMap) {
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if (nodeMap[i][j].getType() == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * megnezi all e unit ott, ahova tornyot probalunk lerakni
     *
     * @param x x kordinata
     * @param y y kordinata
     * @return true ha áll ott, false ha nem
     */
    private boolean isOnUnit(int x, int y) {
        for (int i = 0; i < playerOne.getFighters().size(); i++) {
            if (playerOne.getFighters().get(i).getPosition().getX() == x && playerOne.getFighters().get(i).getPosition().getY() == y) {
                return true;
            }
        }
        for (int i = 0; i < playerTwo.getFighters().size(); i++) {
            if (playerTwo.getFighters().get(i).getPosition().getX() == x && playerTwo.getFighters().get(i).getPosition().getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * inicializálja a megfelelo tornyot.
     * @param tower torony
     * @param newTower új torony
     */
    private void initTowerBy(Units tower, Towers newTower) {
        newTower.setHp(tower.getHp());
        newTower.setMaxHp(tower.getMaxHp());
        newTower.setCost(tower.getCost());
        newTower.setDamage(tower.getDamage());
        newTower.setRange(tower.getRange());
        newTower.setLevel(tower.getLevel());
    }

    /**Megveszi a kiválasztott harcost, ha minden feltétel teljesül.
     * @param selectedUnit kivalasztott unit
     */
    public void buyFighter(UnitTypes selectedUnit) {
       for (int i=0;i<map.getSize();i++){
            for (int j=0;j< map.getSize();j++){
                if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.CASTLE1) && activePlayer.equals(getPlayerOne())){
                    activePlayer.setCastlePosition(new Position(i,j));

                }
                if (map.getGameBoard().get(i).get(j).getMapAttribute().equals(MapAttributesTypes.CASTLE2) && activePlayer.equals(getPlayerTwo())){
                    activePlayer.setCastlePosition(new Position(i,j));
                }
            }
        }
        Units fighter = findSelectedUnit(selectedUnit);
        Fighters newFighter = choseSelectedFighter(selectedUnit, fighter);
        if (activePlayer.getGold() - newFighter.getCost() >= 0) {
            activePlayer.removeGold(newFighter.getCost());
            newFighter.setPosition(new Position(activePlayer.getCastlePosition().getX(), activePlayer.getCastlePosition().getY()));
            newFighter.setPixelPosition(new Position(activePlayer.getCastlePosition().getX() * tileSize, activePlayer.getCastlePosition().getY() * tileSize));
            activePlayer.buyFighter(newFighter);
        }
    }

    /**
     * Eldonti milyen harcost választott a játékos.
     * @param selectedUnit kivalasztott unit
     * @param fighter harcos
     * @return uj harcos
     */
    private Fighters choseSelectedFighter(UnitTypes selectedUnit, Units fighter) {
        Fighters newFighter;
        if (selectedUnit.equals(UnitTypes.ASSASSIN)) {
            newFighter = new Assassin();
            initFighterBy(fighter, newFighter);
        } else if (selectedUnit.equals(UnitTypes.BOMBER)) {
            newFighter = new Bomber();
            initFighterBy(fighter, newFighter);
        } else if (selectedUnit.equals(UnitTypes.CLIMBER)) {
            newFighter = new Climber();
            initFighterBy(fighter, newFighter);
        } else if (selectedUnit.equals(UnitTypes.SOLDIER)) {
            newFighter = new Soldier();
            initFighterBy(fighter, newFighter);
        } else {
            newFighter = new Swimmer();
            initFighterBy(fighter, newFighter);
        }
        return newFighter;
    }

    private void initFighterBy(Units fighter, Fighters newFighter) {
        newFighter.setHp(fighter.getHp());
        newFighter.setMaxHp(fighter.getMaxHp());
        newFighter.setCost(fighter.getCost());
        newFighter.setDamage(fighter.getDamage());
        newFighter.setRange(fighter.getRange());
        newFighter.setLevel(fighter.getLevel());
    }

    /**
     * Eldonti van e eleg penze a playernek a vasarlashoz.
     * @param player jatekos
     * @param tower torony
     * @return meg tudja e venni a tornyot
     */
    public boolean canPlayerBuyThis(Player player, UnitTypes tower) {
        return player.getGold() - findSelectedUnit(tower).getCost() >= 0;
    }

    public void readMap(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
        int c;
        int mapSizeCounter = 0;
        String szo = "";
        while ((c = reader.read()) != -1) {
            char character = (char) c;
            szo += character;
            if (szo.equals("land") || szo.equals("mountain") || szo.equals("castle1") || szo.equals("river")) {
                szo = " ";
                mapSizeCounter++;
            }

        }
        setMap( new Map(mapSizeCounter, filePath));
       this.playerOne.setCastle(this.map.getCastleForPlayer(1));
        this.playerTwo.setCastle(this.map.getCastleForPlayer(2));
    }

    /**
     * eldonti le lehet e rakni a tornyot az adott kordinátákra
     *
     * @param x koordinata
     * @param y koordinata
     * @return 1 ha igen, 0 ha nem
     */
    public int decideIfTurretPlacable(int x, int y) {
        if (map.getMap().get(x).get(y).isAvailable() && !map.getMap().get(x).get(y).isNextToCastle()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * megnezi az adott harcos kap e sebzést
     *
     * @param fighter harcos
     * @param owner kie a hracos
     * @return takesdemage, hogy kap e sebzest
     */
    public boolean fighterTakesDamage(Fighters fighter, int owner) {
        boolean takesDamage = false;
        if (owner == 1) {
            for (int i = 0; i < this.getPlayerTwo().getTowers().size(); i++) {
                if (isFighterInTurretRange(fighter, this.getPlayerTwo().getTowers().get(i))) {
                    fighter.setHp(fighter.getHp() - this.getPlayerTwo().getTowers().get(i).getDamage());
                    takesDamage = true;
                }
            }
        }
        if (owner == 2) {
            for (int i = 0; i < this.getPlayerOne().getTowers().size(); i++) {
                if (isFighterInTurretRange(fighter, this.getPlayerOne().getTowers().get(i))) {
                    fighter.setHp(fighter.getHp() - this.getPlayerOne().getTowers().get(i).getDamage());
                    takesDamage = true;
                }
            }
        }
        if (fighter.getHp() < 0) {
            fighterDied(fighter, owner);
            takesDamage = false;
        }
        return takesDamage;
    }

    /**
     * megsebzi az adott jatekos várát a megadott sebzés mértékkel
     *
     * @param dmg mekkorat sebzodjon
     * @param playerNumber ki sebzodjon
     */
    public void damageCastle(int dmg, int playerNumber) {
        if (playerNumber == 1) {
            playerOne.getCastle().setHealthPoints(playerOne.getCastle().getHPinInt() - dmg);
        } else {
            playerTwo.getCastle().setHealthPoints(playerTwo.getCastle().getHPinInt() - dmg);
        }
    }

    /**
     * megoli a parameterul kapott harcost
     *
     * @param fighter harcos
     * @param owner tulaja
     */
    private void fighterDied(Fighters fighter, int owner) {
        if (owner == 1) {
            for (int i = 0; i < playerOne.getFighters().size(); i++) {
                if (playerOne.getFighters().get(i).equals(fighter)) {
                    playerOne.getFighters().remove(i);
                    playerTwo.giveGold(bl.getGoldPerKilledUnit());
                }
            }
        }
        if (owner == 2) {
            for (int i = 0; i < playerTwo.getFighters().size(); i++) {
                if (playerTwo.getFighters().get(i).equals(fighter)) {
                    playerTwo.getFighters().remove(i);
                    playerOne.giveGold(bl.getGoldPerKilledUnit());
                }
            }
        }
    }

    /**
     * megnezi a harcos támadási távolságon belul van e a toronynak
     *
     * @param fighter harcos
     * @param tower torony
     * @return true ha igen, false ha nem.
     */
    public boolean isFighterInTurretRange(Fighters fighter, Towers tower) {
        for (int j = -1 * tower.getRange(); j <= tower.getRange(); j++) {
            for (int k = -1 * tower.getRange(); k <= tower.getRange(); k++) {
                if (fighter.getPosition().getX() == tower.getPosition().getX() + j && fighter.getPosition().getY() == tower.getPosition().getY() + k) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getRoundCounter() {
        return this.roundCounter;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * beállóítja az aktív játékost.
     *
     * @param number ki legyen az aktiv player
     */
    public void setActivePlayer(int number) {
        if (number == 1) {
            activePlayer = playerOne;
        } else {
            activePlayer = playerTwo;
        }
    }

    public void plusOneRound() {
        this.roundCounter++;
    }

    public Fighters getPlayerOneFighterOnIndex(int i) {
        return this.getPlayerOne().getFighters().get(i);
    }

    public Fighters getPlayerTwoFighterOnIndex(int i) {
        return this.getPlayerTwo().getFighters().get(i);
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerNames(String p1Name, String p2Name) {
        playerOne.setName(p1Name);
        playerTwo.setName(p2Name);
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public String getGamePhaseString() {
        String ret;
        if (this.gamePhase == GamePhase.PLAYER_ONE_DEFENDING_PHASE) {
            ret = "Player One is defending now.";
        } else if (this.gamePhase == GamePhase.PLAYER_TWO_DEFENDING_PHASE) {
            ret = "Player Two is defending now.";
        } else if (this.gamePhase == GamePhase.PLAYER_ONE_RECRUITING_PHASE) {
            ret = "Player One is recruiting now.";
        } else if (this.gamePhase == GamePhase.PLAYER_TWO_RECRUITING_PHASE) {
            ret = "Player Two is recruiting now.";
        } else {
            ret = "End of the game.";
        }
        return ret;
    }

    public boolean isPlayerOneTheWinner() {
        return getPlayerTwo().getCastleHPinInt() <= 0;
    }

    public boolean isPlayerTwoTheWinner() {
        return getPlayerOne().getCastleHPinInt() <= 0;
    }

    public boolean isOnUnit() {
        return isOnUnit;
    }
}
