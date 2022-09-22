package ourstd.model.map;

import ourstd.model.Balance;

public class Castle extends MapAttributes {

    private final int number;
    private final Balance bl = new Balance();
    private int healthPoints = bl.getCastleHP();
    private final int maxHealthPoints = bl.getCastleHP();

    /** a varat beallitja az adott jatekoshoz.
     * @param n melyik jatekos vára
     */
    public Castle(int n) {
        this.attribute = "castle" + n;
        if (n ==1){
            this.mapType = MapAttributesTypes.CASTLE1;
        }
        else{
            this.mapType = MapAttributesTypes.CASTLE2;
        }
        number = n;
    }
    @Override
    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    @Override
    public String getHealthPoints() { return "" + this.healthPoints; }

    @Override
    public int getHPinInt() { return this.healthPoints; }

    public void setHealthPoints(int newHP){
        this.healthPoints = newHP;
    }

    public int getNumber(){
        return this.number;
    }
}
