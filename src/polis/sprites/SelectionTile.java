package polis.sprites;

import javafx.scene.shape.Polygon;

import static polis.Config.*;

/**
 * Abstracte representatie van een SelectionTile
 */
public class SelectionTile extends Polygon {

    public SelectionTile(int factor) {
        super(0, 0,
                CELL_SIZE * SIZE * factor, 0.5 * CELL_SIZE * SIZE * factor,
                0, CELL_SIZE * SIZE * factor,
                -CELL_SIZE * SIZE * factor, 0.5 * CELL_SIZE * SIZE * factor
        );
        setMouseTransparent(true);
    }

    public void setCoords(int x, int y) {
        setTranslateX(x);
        setTranslateY(y);
    }

    public void setColor(boolean bool) {
        setFill(bool ? COLOR_SELECT_BLUE : COLOR_SELECT_RED);
    }
}
