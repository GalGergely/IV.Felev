package ourstd.model.units;

public class Bomber extends Fighters {

    public Bomber() {
        this.setAttribute("bomber");
        this.unitTypes =UnitTypes.BOMBER;
        this.setHp(this.getBalance().getBomberHP());
        this.setMaxHp(this.getBalance().getBomberMaxHP());
        this.setDamage(this.getBalance().getBomberDamage());
        this.setRange(this.getBalance().getBomberRange());
        this.setCost(this.getBalance().getBomberCost());
        this.setMaxMovesOnRound(this.getBalance().getBomberMaxMovesOnRound());
        this.setMoveCounter(this.getMaxMovesOnRound());
    }

    @Override
    public void upgradeCost() {
        this.setCost(this.getCost() + this.getBalance().getBomberCostUpgrade());
    }

    //Upgrade: level 5-ön megkapja a dmg upgradet
    //         level 10-en megkapaja a dmg upgradet + dupla olyan gyors lesz
    @Override
    public void trueUpgrade(){
        switch (this.getLevel()) {
            case 5 -> {
                this.setDamage(this.getDamage() + this.getBalance().getBomberDamageUpgrade());
                upgradeCost();
            }
            case 10 -> {
                this.setDamage(this.getDamage() + this.getBalance().getBomberDamageUpgrade());
                this.setMaxMovesOnRound(this.getMaxMovesOnRound() + this.getBalance().getBomberMaxMovesOnRound());
            }
            default -> upgradeCost();
        }
    }
}
