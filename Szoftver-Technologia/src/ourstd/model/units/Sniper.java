package ourstd.model.units;

public class Sniper extends Towers {

    public Sniper() {
        this.setHp(this.getBalance().getSniperHP());
        this.setDamage(this.getBalance().getSniperDamage());
        this.setCost(this.getBalance().getSniperCost());
        this.setRange(this.getBalance().getSniperRange());
        this.setAttribute("sniper");
        this.unitTypes =UnitTypes.SNIPER;
    }

    @Override
    public void trueUpgrade() {
        switch (this.getLevel()) {
            case 4 -> {
                this.setDamage(this.getDamage() + this.getBalance().getSniperDamageUpgrade());
                this.setRange(this.getRange() + this.getBalance().getSniperRangeUpgrade());
                upgradeCost();
            }
            case 7 -> {
                this.setRange(this.getRange() + this.getBalance().getSniperRangeUpgrade());
                this.setDamage(this.getDamage() + this.getBalance().getSniperDamageUpgrade());
                upgradeCost();
            }
            case 10 -> this.setRange(this.getRange() + this.getBalance().getSniperRangeUpgrade());
            default -> upgradeCost();
        }
    }

    @Override
    public void upgradeCost() {
        this.setCost(this.getCost() + this.getBalance().getSniperCostUpgrade());
    }

    @Override
    public String toString() {
        return "sniper_";
    }
}
