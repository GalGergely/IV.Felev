package ourstd.model.map;

public abstract class MapAttributes {
    boolean available;
    boolean blocked = false;
    boolean nextToCastle = false;
    String attribute = "Attribute";
    MapAttributesTypes mapType = MapAttributesTypes.ATTRIBUTE;
    void consoleLog() {
        System.out.print(attribute);
    }

    public boolean isNextToCastle() {
        return nextToCastle;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getAttribute() {
        return this.attribute;
    }

    public MapAttributesTypes getMapAttribute(){return this.mapType;}

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getMaxHealthPoints() { return 0; }
    public String getHealthPoints() { return ""; }
    public int getHPinInt() { return 0; }
}
