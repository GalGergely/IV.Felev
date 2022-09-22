package ourstd.model.units;

public class Tank extends Towers {

    public Tank() {
        this.setHp(this.getBalance().getTankHP());
        this.setDamage(this.getBalance().getTankDamage());
        this.setCost(this.getBalance().getTankCost());
        this.setRange(this.getBalance().getTankRange());
        this.setAttribute("tank");
        this.unitTypes =UnitTypes.TANK;
    }

    @Override
    public void trueUpgrade() {
         switch (this.getLevel()) {
            case 4 -> {
                this.setDamage(this.getDamage() + this.getBalance().getTankDamageUpgrade());
                upgradeCost();
            }
            case 7 -> {
                upgradeCost();
                this.setDamage(this.getDamage() + this.getBalance().getTankDamageUpgrade());
            }
            case 10 -> {
                this.setDamage(this.getDamage() + this.getBalance().getTankDamageUpgrade());
                this.setRange(this.getRange() + this.getBalance().getTankRangeUpgrade());
            }
            default -> upgradeCost();
        }
    }

    @Override
    public void upgradeCost() {
        this.setCost(this.getCost() + this.getBalance().getTankCostUpgrade());
    }

    @Override
    public String toString() {
        return "tank_";
    }
}
