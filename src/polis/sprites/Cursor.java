package polis.sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import static polis.Config.CELL_SIZE;
import static polis.Config.SIZE;

/**
 * Polygon voor de cursor.
 */
public class Cursor extends Polygon {
    public Cursor() {
        super(
                0, 0,
                CELL_SIZE * SIZE, 0.5 * CELL_SIZE * SIZE,
                0, CELL_SIZE * SIZE,
                -CELL_SIZE * SIZE, 0.5 * CELL_SIZE * SIZE
        );
        setMouseTransparent(true);
        setFill(Color.TRANSPARENT);
        setStrokeWidth(5);
        setStroke(Color.WHITE);
    }
}
