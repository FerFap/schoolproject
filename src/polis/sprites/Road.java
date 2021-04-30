package polis.sprites;

/**
 * Abstracte representatie van een road.
 */
public class Road extends Sprite {

    public Road() {
        super(1, 16, 0);
    }

    @Override
    public String pictureLocation() {
        return "polis/tiles/road-" + level + ".png";
    }

    @Override
    public String getInfo() {
        return "";
    }

    @Override
    public String getTitle(int r, int k) {
        return "Road @ " + r + ":" + k;
    }

}
