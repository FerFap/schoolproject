package polis.gamegrid;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import polis.models.GridModel;
import polis.models.Point;
import polis.sprites.Cursor;
import polis.sprites.GridPolygon;

import static polis.Config.*;

/**
 * Dit is de main pane waarin de map Polygon zit en alle sprites/actors.
 */

public class GameGrid extends Pane {

    private final Cursor cursor = new Cursor();
    private int r;
    private int k;

    /**
     * In de constructor worden alles goed gezet en wat er bij mouseEvents moet gebeuren.
     * @param gridModel
     */
    public GameGrid(GridModel gridModel) {

        setPrefWidth(CELL_SIZE * 2 * NUM_CELLS * SIZE);
        setPrefHeight(CELL_SIZE * NUM_CELLS * SIZE);
        setFocusTraversable(true);
        setBackground();
        getChildren().addAll(cursor);

        setOnMouseMoved(e -> {
            changeCoords(e);
            setPolygonState();
            gridModel.drawSelect();
        });

        setOnMouseClicked(e -> gridModel.mouseClicked());

        setOnDragDetected(e -> gridModel.setStartDrag());

        setOnMouseDragged(e -> {
            changeCoords(e);
            setPolygonState();
            gridModel.drawSelect();
        });

        setOnMouseDragReleased((MouseDragEvent e) -> {
            gridModel.drawSelect();
            gridModel.setEndDrag();
        });
    }

    /**
     * Statische methode die gebruikt wordt om rij en kolom om te zetten naar x, y coordinaten in het vlak.
     * @param r
     * @param k
     * @return
     */
    public static Point convertRowCol(int r, int k) {
        return new Point(CELL_SIZE * (NUM_CELLS - r + k) * SIZE, CELL_SIZE * (r + k) * SIZE / 2);
    }

    /**
     * Wordt gebruikt om de coordinaten van de muis/cursor aan te passen.
     * @param e
     */

    private void changeCoords(MouseEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        this.r = (2 * y - x + CELL_SIZE * NUM_CELLS * SIZE) / (2 * CELL_SIZE * SIZE);
        this.k = (x + 2 * y - CELL_SIZE * NUM_CELLS * SIZE) / (2 * CELL_SIZE * SIZE);
        this.r = Math.max(0, Math.min(this.r, NUM_CELLS - 1));
        this.k = Math.max(0, Math.min(this.k, NUM_CELLS - 1));
    }

    /**
     * Geeft de juiste kleur/coordinaten aan de mouse cursor.
     */
    private void setPolygonState() {
        Point point = convertRowCol(getR(), getK());
        cursor.setTranslateX(point.getX());
        cursor.setTranslateY(point.getY());
        cursor.setViewOrder(-getR() - getK() - 10.0);
    }

    public void setPolyColor(Color color) {
        cursor.setStroke(color);
    }

    public void setBackground(){
        for (int i = 0; i < NUM_CELLS; i++) {
            for (int j = 0; j < NUM_CELLS; j++) {
                Point point = convertRowCol(i, j);
                ImageView img = new ImageView(new Image("polis/tiles/backgrounds/grass.png"));
                img.setTranslateX(point.getX() - CELL_SIZE);
                img.setTranslateY(point.getY());
                getChildren().add(img);
            }
        }
    }

    /**
     * Getters voor rij en kolom.
     * @return
     */
    public int getR() {
        return this.r;
    }

    public int getK() {
        return this.k;
    }
}
