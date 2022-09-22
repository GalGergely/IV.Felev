package ourstd.model.units;

public class Swimmer extends Fighters {

    public Swimmer() {
        this.setAttribute("swimmer");
        this.unitTypes =UnitTypes.SWIMMER;
        this.setHp(this.getBalance().getSwimmerHP());
        this.setMaxHp(this.getBalance().getSwimmerMaxHP());
        this.setDamage(this.getBalance().getSwimmerDamage());
        this.setRange(this.getBalance().getSwimmerRange());
        this.setCost(this.getBalance().getSwimmerCost());
        this.setMaxMovesOnRound(this.getBalance().getSwimmerMaxMovesOnRound());
        this.setMoveCounter(this.getMaxMovesOnRound());
    }
    @Override
    public void upgradeCost() {
        this.setCost(this.getCost() + this.getBalance().getSwimmerCostUpgrade());
    }

    //Upgrade: level 5-ön dupla olyan gyors lesz
    //         level 10-en megkapaja a dmg upgradet
    @Override
    public void trueUpgrade(){
        switch (this.getLevel()) {
            case 5 -> {
                this.setMaxMovesOnRound(this.getMaxMovesOnRound() + this.getBalance().getSwimmerMaxMovesOnRound());
                upgradeCost();
            }
            case 10 -> this.setDamage(this.getDamage() + this.getBalance().getSwimmerDamageUpgrade());

            //itt lehetne az a vegso upgradeja, h ha ralepne egy water re akkor az nem számítana lépésnek
            default -> upgradeCost();
        }
    }
}
