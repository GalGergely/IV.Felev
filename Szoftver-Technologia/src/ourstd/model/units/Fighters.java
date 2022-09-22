package ourstd.model.units;

import ourstd.model.Balance;

public abstract class Fighters extends Units {

    private final Balance balance = new Balance();

    private final int baseVelocity = balance.getBaseVelocity();
    private int xVelocity = balance.getBaseVelocity();
    private int yVelocity = balance.getBaseVelocity();
    private int maxMovesOnRound = balance.getMaxMovesOnRound();
    private final int damagedForPixels = balance.getDamagedForPixels();
    private int moveCounter;
    private int damagePixelCounter;
    private boolean reachedEnd = false;

    protected Fighters() {
        this.damagePixelCounter = 5;
        this.moveCounter = balance.getMaxMovesOnRound();
    }

    public int getDamagePixelCounter() {
        return damagePixelCounter;
    }

    public boolean getReachedEnd() {
        return reachedEnd;
    }

    public void setReachedEnd(boolean reachedEnd) {
        this.reachedEnd = reachedEnd;
    }

    public void setDamagePixelCounter(int damagePixelCounter) {
        this.damagePixelCounter = damagePixelCounter;
    }
    public void addOneToDamagePixelCounter() {
        this.damagePixelCounter++;
    }

    public int getDamagedForPixels() {
        return damagedForPixels;
    }

    public int getXVelocity() {
        return this.xVelocity;
    }

    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public int getYVelocity() {
        return this.yVelocity;
    }

    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    public int getBaseVelocity() {
        return this.baseVelocity;
    }

    public int getMaxMovesOnRound() {
        return this.maxMovesOnRound;
    }

    public void setMaxMovesOnRound(int maxMovesOnRound) {
        this.maxMovesOnRound = maxMovesOnRound;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public void addToMoveCounter(int number) {
        this.moveCounter += number;
    }
}
