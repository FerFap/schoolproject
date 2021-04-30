package polis.models;

import polis.gamegrid.GameGrid;
import polis.sprites.Sprite;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Dit is een template die gebruikt kan worden om van een beginpunt x1, y1 een taxicab afstand te vinden naar
 * een punt x2, y2.
 */

public abstract class DragModel {

    protected final GridModel gridModel;
    protected final Sprite[][] sprites;
    // Houdt een map van x coordinaten die gemapt worden op y coordinaten die in gebruik zijn (hitbox).
    protected Map<Integer, Set<Integer>> coordsOfAlreadyPlaced;
    protected GameGrid gameGrid;

    public DragModel(GridModel gridModel, Sprite[][] sprites) {
        this.gridModel = gridModel;
        this.sprites = sprites;
        coordsOfAlreadyPlaced = new HashMap<>();
    }

    public void setGameGrid(GameGrid gameGrid) {
        this.gameGrid = gameGrid;
    }

    /**
     * De template om de juiste coordinaten te genereren,
     * maakt gebruikt van 2 helperfuncties placeX en placeY.
     * @param beginx
     * @param endx
     * @param beginy
     * @param endy
     * @param size
     */

    public void draw(int beginx, int endx, int beginy, int endy, int size) {
        int dx = (beginx > endx) ? -1 : 1;
        int dy = (beginy > endy) ? -1 : 1;
        beginx = placeX(beginx, endx, beginy, dx, size);
        placeY(beginx, endy, beginy, dy, size);
    }

    protected int placeX(int beginx, int endx, int beginy, int dx, int size) {
        do {
            helper(beginx, beginy, size);
            beginx += dx;
        } while (beginx - dx != endx);

        return beginx - dx;
    }

    protected int placeY(int beginx, int endy, int beginy, int dy, int size) {
        do {
            helper(beginx, beginy, size);
            beginy += dy;
        } while (beginy - dy != endy);

        return beginy - dy;
    }

    protected void setSprite(int x, int y, Sprite sprite) {
        sprites[x][y] = sprite;
        gameGrid.getChildren().add(sprite);
    }

    protected void checkIn(int x, int y) {
        if (!coordsOfAlreadyPlaced.containsKey(x)) {
            coordsOfAlreadyPlaced.put(x, new HashSet<>());
        }
        coordsOfAlreadyPlaced.get(x).add(y);
    }

    /**
     * Moet geimplementeerd worden om de 'special behaviour' van je datatype te definieren.
     * @param beginx
     * @param beginy
     * @param size
     */

    protected abstract void helper(int beginx, int beginy, int size);
}
