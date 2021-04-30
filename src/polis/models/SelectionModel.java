package polis.models;

import polis.gamegrid.GameGrid;
import polis.sprites.SelectionTile;
import polis.sprites.Sprite;

import java.util.*;

/**
 * Model voor selection tiles gebruik makend van de drag model.
 */

public class SelectionModel extends DragModel {

    private final Queue<SelectionTile> selectionTiles;
    private final Set<Integer> set = new HashSet<>();
    private Iterator<SelectionTile> selectionTileIterator;

    public SelectionModel(GridModel gridModel, Sprite[][] sprites) {
        super(gridModel, sprites);
        this.selectionTiles = new LinkedList<>();
    }

    /**
     * Houdt bij hoeveel polygonen er nodig zijn om te tekenen, en als nodig worden er polygonen gedelete
     * of toegevoegd in de plaats van altijd alle polygonen weg te doen en terug te tekenen.
     * @param beginx
     * @param endx
     * @param beginy
     * @param endy
     * @param size
     */
    @Override
    public void draw(int beginx, int endx, int beginy, int endy, int size) {
        int dist = Math.abs(beginx - endx) + Math.abs(beginy - endy) + 1;
        SelectionTile selectionTile;
        if (selectionTiles.size() > dist) {
            while (selectionTiles.size() != dist) {
                selectionTile = selectionTiles.remove();
                gameGrid.getChildren().remove(selectionTile);
            }
        } else if (selectionTiles.size() < dist) {
            while (selectionTiles.size() != dist) {
                selectionTile = new SelectionTile(size);
                selectionTiles.add(selectionTile);
                gameGrid.getChildren().add(selectionTile);
            }
        }
        coordsOfAlreadyPlaced.clear();
        selectionTileIterator = selectionTiles.iterator();
        super.draw(beginx, endx, beginy, endy, size);
        selectionTileIterator = null;
    }

    /**
     * Behaviour van een (of meer) selection tile(s).
     * @param beginx
     * @param beginy
     * @param size
     */

    @Override
    protected void helper(int beginx, int beginy, int size) {

        if (!coordsOfAlreadyPlaced.getOrDefault(beginx, set).contains(beginy)) {
            Point point = GameGrid.convertRowCol(beginx, beginy);
            SelectionTile selectionTile = selectionTileIterator.next();
            selectionTile.setColor((
                    (sprites[beginx][beginy] == null)
                            && (sprites[beginx + size - 1][beginy] == null)
                            && (sprites[beginx][beginy + size - 1] == null)
                            && (sprites[beginx + size - 1][beginy + size - 1] == null)
            ));
            selectionTile.setCoords(point.getX(), point.getY());
            selectionTile.setViewOrder(-beginx - beginy - 8.0);
            checkIn(beginx, beginy);
        }
    }

    public void removeAllSelections() {
        gameGrid.getChildren().removeAll(selectionTiles);
        selectionTiles.clear();
        coordsOfAlreadyPlaced.clear();
    }
}
