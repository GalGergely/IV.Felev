package ourstd.model;

/**
 * Ez az osztaly a jatek balanceolasara szolgal. ha valami erteklet modositunk, itt kell.
 */
public class Balance {
    //Alapbeallitasok
    private final int rangeForCastles = 3;
    private final int MapSize = 20;
    private final int maxHp = 50;
    private final int damage = 20;
    private final int range = 1;
    private final int cost = 100;
    private final int damagedForPixels = 5;
    private final int baseVelocity = 2;
    private final int maxMovesOnRound = 2;
    private final int castelHP = 100;

    //Tower balance - Tank, Watchtower, Sniper
    private final int tankHP = 100;
    private final int tankMaxHP = tankHP;
    private final int tankDamage = 30;
    private final int tankRange = 1;
    private final int tankCost = 200;
    private final int tankDamageUpgrade = 10;
    private final int tankRangeUpgrade = 1;
    private final int tankCostUpgrade = 10;

    private final int watchTowerHP = 100;
    private final int watchTowerMaxHP = watchTowerHP;
    private final int watchTowerDamage = 20;
    private final int watchTowerRange = 1;
    private final int watchTowerCost = 150;
    private final int watchTowerDamageUpgrade = 10;
    private final int watchTowerRangeUpgrade = 1;
    private final int watchTowerCostUpgrade = 15;

    private final int sniperHP = 100;
    private final int sniperMaxHP = sniperHP;
    private final int sniperDamage = 10;
    private final int sniperRange = 1;
    private final int sniperCost = 100;
    private final int sniperDamageUpgrade = 15;
    private final int sniperRangeUpgrade = 1;
    private final int sniperCostUpgrade = 20;

    private final int goldPerRound = 200;
    private final int goldPerKilledUnit = 50;
    private final int startingGold = 500;

    //Fighter balance - Assassin, Bomber, Climber, Soldier, Swimmer
    private final int assassinHP = 60;
    private final int assassinMaxHP = assassinHP;
    private final int assassinDamage = 30;
    private final int assassinRange = 1;
    private final int assassinCost = 125;
    private final int assassinCostUpgrade = 25;
    private final int assassinDamageUpgrade = 20;
    private final int assassinMaxMovesOnRound = 3;

    private final int bomberHP = 100;
    private final int bomberMaxHP = bomberHP;
    private final int bomberDamage = 50;
    private final int bomberRange = 1;
    private final int bomberCost = 200;
    private final int bomberCostUpgrade = 50;
    private final int bomberDamageUpgrade = 25;
    private final int bomberMaxMovesOnRound = 20;

    private final int climberHP = 90;
    private final int climberMaxHP = climberHP;
    private final int climberDamage = 20;
    private final int climberRange = 1;
    private final int climberCost = 125;
    private final int climberCostUpgrade = 25;
    private final int climberDamageUpgrade = 30;
    private final int climberMaxMovesOnRound = 2;

    private final int soldierHP = 150;
    private final int soldierMaxHP = soldierHP;
    private final int soldierDamage = 20;
    private final int soldierRange = 1;
    private final int soldierCost = 200;
    private final int soldierCostUpgrade = 20;
    private final int soldierHPUpgrade = 25;
    private final int soldierDamageUpgrade = 40;
    private final int soldierMaxMovesOnRound = 1;

    private final int swimmerHP = 90;
    private final int swimmerMaxHP = swimmerHP;
    private final int swimmerDamage = 20;
    private final int swimmerRange = 1;
    private final int swimmerCost = 125;
    private final int swimmerCostUpgrade = 25;
    private final int swimmerDamageUpgrade = 30;
    private final int swimmerMaxMovesOnRound = 2;

    public int getCastleHP() { return castelHP; }

    public int getAssassinMaxMovesOnRound() { return assassinMaxMovesOnRound; }

    public int getBomberMaxMovesOnRound() { return bomberMaxMovesOnRound; }

    public int getClimberMaxMovesOnRound() { return climberMaxMovesOnRound; }

    public int getSoldierMaxMovesOnRound() { return soldierMaxMovesOnRound; }

    public int getSwimmerMaxMovesOnRound() { return swimmerMaxMovesOnRound; }

    public int getRangeForCastles() { return rangeForCastles; }

    public int getTankCostUpgrade() { return tankCostUpgrade; }

    public int getWatchTowerCostUpgrade() { return watchTowerCostUpgrade; }

    public int getSniperCostUpgrade() { return sniperCostUpgrade; }

    public int getTankMaxHP() { return tankMaxHP; }

    public int getTankDamageUpgrade() { return tankDamageUpgrade; }

    public int getTankRangeUpgrade() { return tankRangeUpgrade; }

    public int getWatchTowerMaxHP() { return watchTowerMaxHP; }

    public int getWatchTowerDamageUpgrade() { return watchTowerDamageUpgrade; }

    public int getWatchTowerRangeUpgrade() { return watchTowerRangeUpgrade; }

    public int getSniperMaxHP() { return sniperMaxHP; }

    public int getSniperDamageUpgrade() { return sniperDamageUpgrade; }

    public int getSniperRangeUpgrade() { return sniperRangeUpgrade; }

    public int getAssassinDamageUpgrade() { return assassinDamageUpgrade; }

    public int getBomberDamageUpgrade() { return bomberDamageUpgrade; }

    public int getClimberDamageUpgrade() { return climberDamageUpgrade; }

    public int getSoldierHPUpgrade() { return soldierHPUpgrade; }

    public int getSoldierDamageUpgrade() { return soldierDamageUpgrade; }

    public int getSwimmerDamageUpgrade() { return swimmerDamageUpgrade; }

    public int getAssassinCostUpgrade() { return assassinCostUpgrade; }

    public int getBomberCostUpgrade() { return bomberCostUpgrade; }

    public int getClimberCostUpgrade() { return climberCostUpgrade; }

    public int getSoldierCostUpgrade() { return soldierCostUpgrade; }

    public int getSwimmerCostUpgrade() { return swimmerCostUpgrade; }

    public int getAssassinHP() { return assassinHP; }

    public int getAssassinMaxHP() { return assassinMaxHP; }

    public int getAssassinDamage() { return assassinDamage; }

    public int getAssassinRange() { return assassinRange; }

    public int getAssassinCost() { return assassinCost; }

    public int getSwimmerHP() { return swimmerHP; }

    public int getSwimmerMaxHP() { return swimmerMaxHP; }

    public int getSwimmerDamage() { return swimmerDamage; }

    public int getSwimmerRange() { return swimmerRange; }

    public int getSwimmerCost() { return swimmerCost; }

    public int getBomberHP() { return bomberHP; }

    public int getBomberMaxHP() { return bomberMaxHP; }

    public int getBomberDamage() { return bomberDamage; }

    public int getBomberRange() { return bomberRange; }

    public int getBomberCost() { return bomberCost; }

    public int getClimberHP() { return climberHP; }

    public int getClimberMaxHP() { return climberMaxHP; }

    public int getClimberDamage() { return climberDamage; }

    public int getClimberRange() { return climberRange; }

    public int getClimberCost() { return climberCost; }

    public int getSoldierHP() { return soldierHP; }

    public int getSoldierMaxHP() { return soldierMaxHP; }

    public int getSoldierDamage() { return soldierDamage; }

    public int getSoldierRange() { return soldierRange; }

    public int getSoldierCost() { return soldierCost; }

    public int getGoldPerRound() { return goldPerRound; }

    public int getGoldPerKilledUnit() { return goldPerKilledUnit; }

    public int getMapSize() { return MapSize; }

    public int getMaxHp() { return maxHp; }

    public int getDamage() { return damage; }

    public int getRange() { return range; }

    public int getCost() { return cost; }

    public int getDamagedForPixels() { return damagedForPixels; }

    public int getBaseVelocity() { return baseVelocity; }

    public int getMaxMovesOnRound() { return maxMovesOnRound; }

    public int getTankRange() { return tankRange; }

    public int getWatchTowerRange() {
        return watchTowerRange;
    }

    public int getSniperRange() { return sniperRange; }

    public int getTankDamage() { return tankDamage; }

    public int getWatchTowerDamage() { return watchTowerDamage; }

    public int getSniperDamage() { return sniperDamage; }

    public int getTankHP() { return tankHP; }

    public int getWatchTowerHP() { return watchTowerHP; }

    public int getSniperHP() { return sniperHP; }

    public int getTankCost() { return tankCost; }

    public int getWatchTowerCost() { return watchTowerCost; }

    public int getSniperCost() { return sniperCost; }

    public int getStartingGold() { return startingGold; }
}
