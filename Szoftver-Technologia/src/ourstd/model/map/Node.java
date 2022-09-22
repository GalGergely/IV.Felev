package ourstd.model.map;

import ourstd.model.Position;

/**
 * 0 = kezdopont, 1 = cel, 2 = nem lehet ralepni, 3 = ra lehet lepni, 4 = megnezett, 5 = utvonal
 */
public class Node {
    private int cellType = 0;
    private int hops;
    private final int x;
    private final int y;
    private int lastX;
    private int lastY;

    public Node(int type, int x, int y) {
        cellType = type;
        this.x = x;
        this.y = y;
        hops = -1;
    }

    /**
     * kiszamolja az Euklidészi tavolsagot a celnodeig
     * @param finish cel
     * @return Euklidészi tavolsag
     */
    public double getEuclidDist(Position finish) {
        int xDif = Math.abs(x - finish.getX());
        int yDif = Math.abs(y - finish.getY());
        return Math.sqrt((double)(xDif * xDif) + (double)(yDif * yDif));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public int getType() {
        return cellType;
    }

    public void setType(int type) {
        cellType = type;
    }

    public int getHops() {
        return hops;
    }

    public void setHops(int hops) {
        this.hops = hops;
    }

    public void setLastNode(int x, int y) {
        lastX = x;
        lastY = y;
    }

    @Override
    public String toString() {
        return "" + cellType + "";
    }
}