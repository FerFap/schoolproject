package polis.sprites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import polis.simulation.Simulation;

import java.util.Map;

import static polis.Config.SIZE;

/**
 * Boven klasse voor alle sprites (residence, commerce, industry, road)
 */
public abstract class Sprite extends ImageView {

    private final static Map<String, Image> map = Map.ofEntries(
            Map.entry("polis/tiles/commerce-0.png", new Image("polis/tiles/commerce-0.png")),
            Map.entry("polis/tiles/commerce-1.png", new Image("polis/tiles/commerce-1.png")),
            Map.entry("polis/tiles/commerce-2.png", new Image("polis/tiles/commerce-2.png")),
            Map.entry("polis/tiles/commerce-3.png", new Image("polis/tiles/commerce-3.png")),
            Map.entry("polis/tiles/industry-0.png", new Image("polis/tiles/industry-0.png")),
            Map.entry("polis/tiles/industry-1.png", new Image("polis/tiles/industry-1.png")),
            Map.entry("polis/tiles/industry-2.png", new Image("polis/tiles/industry-2.png")),
            Map.entry("polis/tiles/industry-3.png", new Image("polis/tiles/industry-3.png")),
            Map.entry("polis/tiles/residence-0.png", new Image("polis/tiles/residence-0.png")),
            Map.entry("polis/tiles/residence-1.png", new Image("polis/tiles/residence-1.png")),
            Map.entry("polis/tiles/residence-2.png", new Image("polis/tiles/residence-2.png")),
            Map.entry("polis/tiles/residence-3.png", new Image("polis/tiles/residence-3.png")),
            Map.entry("polis/tiles/road-0.png", new Image("polis/tiles/road-0.png")),
            Map.entry("polis/tiles/road-1.png", new Image("polis/tiles/road-1.png")),
            Map.entry("polis/tiles/road-2.png", new Image("polis/tiles/road-2.png")),
            Map.entry("polis/tiles/road-3.png", new Image("polis/tiles/road-3.png")),
            Map.entry("polis/tiles/road-4.png", new Image("polis/tiles/road-4.png")),
            Map.entry("polis/tiles/road-5.png", new Image("polis/tiles/road-5.png")),
            Map.entry("polis/tiles/road-6.png", new Image("polis/tiles/road-6.png")),
            Map.entry("polis/tiles/road-7.png", new Image("polis/tiles/road-7.png")),
            Map.entry("polis/tiles/road-8.png", new Image("polis/tiles/road-8.png")),
            Map.entry("polis/tiles/road-9.png", new Image("polis/tiles/road-9.png")),
            Map.entry("polis/tiles/road-10.png", new Image("polis/tiles/road-10.png")),
            Map.entry("polis/tiles/road-11.png", new Image("polis/tiles/road-11.png")),
            Map.entry("polis/tiles/road-12.png", new Image("polis/tiles/road-12.png")),
            Map.entry("polis/tiles/road-13.png", new Image("polis/tiles/road-13.png")),
            Map.entry("polis/tiles/road-14.png", new Image("polis/tiles/road-14.png")),
            Map.entry("polis/tiles/road-15.png", new Image("polis/tiles/road-15.png"))
    );

    private final static Map<String, String> names = Map.of(
            "Residence", "residential",
            "Commerce", "commercial",
            "Industry", "industrial"
    );
    protected int level;
    protected int levels;
    private int size;
    private boolean removable;
    protected double capacity;

    public Sprite(int size, int levels, double capacity) {
        setValues(size, levels);
        this.removable = true;
        this.capacity = capacity;
    }

    private void setValues(int size, int levels) {
        this.level = 0;
        this.size = size;
        this.levels = levels;
        changeImage();
        setMouseTransparent(true);
    }

    public void setCoords(int x, int y) {
        setX(x);
        setY(y);
    }

    public void upgrade() {
        level = (level + 1) % levels;
        changeImage();
    }

    public void downgrade() {
        level = (level - 1) % levels;
        changeImage();
    }

    public void setLevel(int level) {
        this.level = level;
        changeImage();
    }

    public void changeImage() {
        Image image = map.get(pictureLocation());
        setImage(image);
        setFitWidth(image.getWidth() * SIZE);
        setFitHeight(image.getHeight() * SIZE);
        setTranslateX(-0.5 * image.getWidth() * SIZE);
        setTranslateY((0.5 * image.getWidth() - image.getHeight()) * SIZE);
    }

    /**
     * Checkt voor de zones wat er met hun level moet gebeuren afhankelijk van hun capaciteit.
     */
    protected void check() {
        switch (level) {
            case 1: {
                if (capacity >= Simulation.get(names.get(this.getClass().getSimpleName()) + ".level1to2")) {
                    upgrade();
                } else if (capacity <= Simulation.get(names.get(this.getClass().getSimpleName()) + ".capacity.minimal")) {
                    capacity = Simulation.get(names.get(this.getClass().getSimpleName()) + ".capacity.minimal");
                }
                break;
            }
            case 2: {
                if (capacity >= Simulation.get(names.get(this.getClass().getSimpleName()) + ".level2to3")) {
                    upgrade();
                } else if (capacity <= Simulation.get(names.get(this.getClass().getSimpleName()) + ".level2to1")) {
                    downgrade();
                }
                break;
            }
            case 3: {
                if (capacity >= Simulation.get(names.get(this.getClass().getSimpleName()) + ".level2to3")) {
                    capacity = Simulation.get(names.get(this.getClass().getSimpleName()) + ".level2to3");
                } else if (capacity <= Simulation.get(names.get(this.getClass().getSimpleName()) + ".level3to2")) {
                    downgrade();
                }
                break;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean bool) {
        this.removable = bool;
    }

    public abstract String getInfo();

    protected abstract String pictureLocation();

    public abstract String getTitle(int r, int k);
}
