package ourstd.model.map;

public class Land extends MapAttributes {
    /**
     * az elerhetoseget, az attributet es a maptype-t allija.
     */
    public Land() {
        this.attribute = "land";
        this.available = true;
        this.mapType=MapAttributesTypes.LAND;
    }
    public Land(boolean nextToCastle) {
        this.attribute = "land";
        this.mapType=MapAttributesTypes.LAND;
        this.available = true;
        this.nextToCastle = nextToCastle;
    }
}
