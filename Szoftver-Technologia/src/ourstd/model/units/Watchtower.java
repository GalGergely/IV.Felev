package ourstd.model.units;

public class Watchtower extends Towers {

    public Watchtower() {
        this.setHp(this.getBalance().getWatchTowerHP());
        this.setDamage(this.getBalance().getWatchTowerDamage());
        this.setCost(this.getBalance().getWatchTowerCost());
        this.setRange(this.getBalance().getWatchTowerRange());
        this.setAttribute("watchtower");
        this.unitTypes =UnitTypes.WATCHTOWER;
    }

    @Override
    public void trueUpgrade() {
        switch (this.getLevel()) {
            case 4 -> {
                this.setDamage(this.getDamage() + this.getBalance().getWatchTowerDamageUpgrade());
                upgradeCost();
            }
            case 7 -> {
                upgradeCost();
                this.setDamage(this.getDamage() + this.getBalance().getWatchTowerDamageUpgrade());
                this.setRange(this.getRange() + this.getBalance().getWatchTowerRangeUpgrade());
            }
            case 10 -> {
                this.setDamage(this.getDamage() + this.getBalance().getWatchTowerDamageUpgrade());
                this.setRange(this.getRange() + this.getBalance().getWatchTowerRangeUpgrade());
            }
            default -> upgradeCost();
        }
    }

    @Override
    public void upgradeCost() {
        this.setCost(this.getCost() + this.getBalance().getWatchTowerCostUpgrade());
    }

    @Override
    public String toString() {
        return "watchtower_";
    }
}
