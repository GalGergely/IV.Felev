package ourstd.model.units;

public class Assassin extends Fighters {

    public Assassin() {
        this.setAttribute("assassin");
        this.unitTypes =UnitTypes.ASSASSIN;
        this.setHp(this.getBalance().getAssassinHP());
        this.setMaxHp(this.getBalance().getAssassinMaxHP());
        this.setDamage(this.getBalance().getAssassinDamage());
        this.setRange(this.getBalance().getAssassinRange());
        this.setCost(this.getBalance().getAssassinCost());
        this.setMaxMovesOnRound(this.getBalance().getAssassinMaxMovesOnRound());
        this.setMoveCounter(this.getMaxMovesOnRound());
    }

    @Override
    public void upgradeCost() {
        this.setCost(this.getCost() + this.getBalance().getAssassinCostUpgrade());
    }

    //Upgrade: level 5-ön megkapja a dmg upgradet
    //         level 10-en megkapaja a dmg upgradet + dupla olyan gyors lesz
    @Override
    public void trueUpgrade(){
        switch (this.getLevel()) {
            case 5 -> {
                this.setDamage(this.getDamage() + this.getBalance().getAssassinDamageUpgrade());
                upgradeCost();
            }
            case 10 -> {
                this.setDamage(this.getDamage() + this.getBalance().getAssassinDamageUpgrade());
                this.setMaxMovesOnRound(this.getMaxMovesOnRound() + this.getBalance().getAssassinMaxMovesOnRound());
            }
            default -> upgradeCost();
        }
    }
}
