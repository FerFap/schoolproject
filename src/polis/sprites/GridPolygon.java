package polis.sprites;

import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

import static polis.Config.*;

/**
 * Representatie van de map polygon.
 */
public class GridPolygon extends Polygon {

    public GridPolygon() {
        super(
                0, 0,
                CELL_SIZE * NUM_CELLS * SIZE, CELL_SIZE * NUM_CELLS * SIZE / 2.0,
                0, CELL_SIZE * NUM_CELLS * SIZE,
                -CELL_SIZE * NUM_CELLS * SIZE, CELL_SIZE * NUM_CELLS * SIZE / 2.0
        );
        setTranslateX(CELL_SIZE * NUM_CELLS * SIZE);
        setFill(COLOR_GRID);
        setStrokeWidth(10);
        setStrokeType(StrokeType.OUTSIDE);
        setStroke(Color.BLUEVIOLET);
        setMouseTransparent(true);
    }
}
