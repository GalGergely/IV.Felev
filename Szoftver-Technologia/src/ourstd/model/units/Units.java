package ourstd.model.units;

import ourstd.model.Balance;
import ourstd.model.Position;

public abstract class Units {

    private final Balance balance = new Balance();

    private int hp = balance.getMaxHp();
    private int maxHp = balance.getMaxHp();
    private int damage = balance.getDamage();
    private int range = balance.getRange();
    private int cost = balance.getCost();
    private int level;
    private Position position = new Position(0,0);
    private Position pixelPosition = new Position(0,0);
    private String attribute = "attribute";
    protected UnitTypes unitTypes = UnitTypes.ATTRIBUTE;

    public Balance getBalance() {
        return balance;
    }

    public UnitTypes getUnitTypes(){return unitTypes;}

    protected Units() {
        level = 1;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) { this.level = level; }

    /** megemeli a szintjet egy adott szammal
     * @param number a mertek
     */
    public void incrementLevel(int number) {
        level = level + number;
        trueUpgrade();
    }

    public abstract void trueUpgrade();
    public abstract void upgradeCost();

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public String getAttribute() {
        return this.attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Position getPixelPosition() {
        return pixelPosition;
    }

    public void setPixelPosition(Position pixelPosition) {
        this.pixelPosition = pixelPosition;
    }

    public void setPixelPosition(int x, int y) {
        this.pixelPosition.setX(x);
        this.pixelPosition.setY(y);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void toStringUnits(){
        System.out.println("Unit attribute: " + this.getAttribute());
        System.out.println("Unit hp: " + this.getHp());
        System.out.println("Unit maxHP: " + this.getMaxHp());
        System.out.println("Unit damage: " + this.getDamage());
        System.out.println("Unit range: " + this.getRange());
        System.out.println("Unit cost: " + this.getCost());
        System.out.println("Unit level: " + this.getLevel());
    }
}
