package ourstd.model;

import ourstd.model.map.Castle;
import ourstd.model.map.Map;
import ourstd.model.units.*;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static final Balance bl = new Balance();
    private final List<Towers> towers;
    private List<Fighters> fighters;
    private final List<Units> unitInfo;
    private Position castlePosition;

    private int gold = bl.getStartingGold();
    private String name;
    private final int witchPlayer;

    private Castle castle;

    /** beállítja a játekos castle poziciojat, a legeneralt poziciohoz.
     * @param map palya
     * @param witchPlayer melyik jatekos
     */
    public Player(Map map, int witchPlayer) {

        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if (map.getGameBoard().get(i).get(j).getAttribute().equals("castle" + witchPlayer)) {
                    castlePosition = new Position(i, j);
                }
            }
        }
        this.witchPlayer = witchPlayer;

        towers = new ArrayList<>();
        fighters = new ArrayList<>();
        unitInfo = new ArrayList<>();

        unitInfo.add(new Climber()); // 0
        unitInfo.add(new Soldier()); // 1
        unitInfo.add(new Assassin()); // 2
        unitInfo.add(new Bomber()); // 3
        unitInfo.add(new Swimmer()); // 4
        unitInfo.add(new Sniper()); // 5
        unitInfo.add(new Tank()); // 6
        unitInfo.add(new Watchtower()); // 7
    }

    public Position getCastlePosition() {
        return castlePosition;
    }
    public void setCastlePosition(Position p) {this.castlePosition =p;};

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGold() {
        return this.gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void giveGold(int amount) {
        this.gold += amount;
    }

    public void removeGold(int amount) {
        this.gold -= amount;
    }

    public void buyTower(Towers t) {
        towers.add(t);
    }

    public void buyFighter(Fighters f) {
        fighters.add(f);
    }

    public Castle getCastle() { return castle; }

    public void setCastle(Castle castle) { this.castle = castle; }

    @Override
    public String toString() {
        return "Player{" +
                " name='" + name + '\'' +
                ", gold=" + gold +
                ", towers=" + towers +
                ", fighters=" + fighters +
                '}';
    }

    public List<Towers> getTowers() {
        return towers;
    }

    public List<Units> getUnitInfo() {
        return unitInfo;
    }

    public List<Fighters> getFighters() {
        return fighters;
    }

    public void setFighters(List<Fighters> fighters) {
        this.fighters = fighters;
    }

    public int getWitchPlayer() {
        return witchPlayer;
    }

    public String getCastleHP() {
        return this.castle.getHealthPoints();
    }

    public int getCastleHPinInt() {
        return this.castle.getHPinInt();
    }
}
