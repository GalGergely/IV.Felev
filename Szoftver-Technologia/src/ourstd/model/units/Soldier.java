package ourstd.model.units;

public class Soldier extends Fighters {

    public Soldier() {
        this.setAttribute("soldier");
        this.unitTypes =UnitTypes.SOLDIER;
        this.setHp(this.getBalance().getSoldierHP());
        this.setMaxHp(this.getBalance().getSoldierMaxHP());
        this.setDamage(this.getBalance().getSoldierDamage());
        this.setRange(this.getBalance().getSoldierRange());
        this.setCost(this.getBalance().getSoldierCost());
        this.setMaxMovesOnRound(this.getBalance().getSoldierMaxMovesOnRound());
        this.setMoveCounter(this.getMaxMovesOnRound());
    }

    @Override
    public void upgradeCost() {
        this.setCost(this.getCost() + this.getBalance().getSoldierCostUpgrade());
    }

    //Upgrade: level 5-ön hp upgradet kap
    //         level 10-en hp upgradet kap + dmg upgradet kap
    @Override
    public void trueUpgrade(){
        switch (this.getLevel()) {
            case 5 -> {
                this.setHp(this.getBalance().getSoldierHP() + this.getBalance().getSoldierHPUpgrade());
                this.setMaxHp(this.getHp());
                upgradeCost();
            }
            case 10 -> {
                this.setHp(this.getHp() + this.getBalance().getSoldierHPUpgrade());
                this.setMaxHp(this.getHp());
                this.setDamage(this.getDamage() + this.getBalance().getSoldierDamageUpgrade());
            }
            default -> upgradeCost();
        }
    }
}
