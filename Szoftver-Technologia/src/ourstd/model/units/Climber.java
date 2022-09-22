package ourstd.model.units;

public class Climber extends Fighters {

    public Climber() {
        this.setAttribute("climber");
        this.unitTypes =UnitTypes.CLIMBER;
        this.setHp(this.getBalance().getClimberHP());
        this.setMaxHp(this.getBalance().getClimberMaxHP());
        this.setDamage(this.getBalance().getClimberDamage());
        this.setRange(this.getBalance().getClimberRange());
        this.setCost(this.getBalance().getClimberCost());
        this.setMaxMovesOnRound(this.getBalance().getClimberMaxMovesOnRound());
        this.setMoveCounter(this.getMaxMovesOnRound());
    }

    @Override
    public void upgradeCost() {
        this.setCost(this.getCost() + this.getBalance().getClimberCostUpgrade());
    }

    //Upgrade: level 5-ön dupla olyan gyors lesz
    //         level 10-en megkapaja a dmg upgradet
    @Override
    public void trueUpgrade(){
        switch (this.getLevel()) {
            case 5 -> {
                this.setMaxMovesOnRound(this.getMaxMovesOnRound() + this.getBalance().getClimberMaxMovesOnRound());
                upgradeCost();
            }
            case 10 -> this.setDamage(this.getDamage() + this.getBalance().getClimberDamageUpgrade());

            //itt lehetne az a vegso upgradeja, h ha ralepne egy mountainra akkor az nem számítana lépésnek
            default -> upgradeCost();
        }
    }
}
