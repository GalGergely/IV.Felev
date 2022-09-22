package ourstd.model;

public class Position {
    private int x;
    private int y;

    /** x és y kordinatat tartalmaz, ez alapjan szamol a palya tilei kozott.
     * @param x x
     * @param y y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }

    public boolean equals(Position position, int tileSize, int baseVelocity) {
        boolean one = difference(position.getX() * tileSize, this.getX()) <= baseVelocity;
        boolean two = difference(position.getY() * tileSize, this.getY()) <= baseVelocity;
        return one && two;
    }

    public int difference(int a, int b) {
        if (a > b) {
            return a - b;
        } else if (b > a) {
            return b - a;
        } else {
            return 0;
        }
    }
}
