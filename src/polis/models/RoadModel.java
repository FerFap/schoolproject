package polis.models;

import polis.gamegrid.GameGrid;
import polis.sprites.Road;
import polis.sprites.Sprite;

import java.util.HashSet;
import java.util.Set;

import static polis.Config.NUM_CELLS;

/**
 * Houdt de staat bij van de wegen en plaatst wegen gebruik makend van de template DragModel.
 */

public class RoadModel extends DragModel {

    public RoadModel(GridModel gridModel, Sprite[][] sprites) {
        super(gridModel, sprites);
    }


    public void setUnremovableRoad() {
        int y = NUM_CELLS - NUM_CELLS / 3 - 1;
        for (int x = 0; x < NUM_CELLS / 2; x++) {
            Sprite road = new Road();
            road.setRemovable(false);
            road.setViewOrder(-x - y - 2.0);
            Point point = GameGrid.convertRowCol(x, y);
            road.setX(point.getX());
            road.setY(point.getY());
            setSprite(x, y, road);
            checkIn(x, y);
            update(x, y);
        }
    }

    /**
     * Update 4 wegen rond de weg die juist geplaatst werd.
     * @param x
     * @param y
     */

    public void update(int x, int y) {
        calculateRoadPicture(x, y);
        calculateRoadPicture(x - 1, y);
        calculateRoadPicture(x, y - 1);
        calculateRoadPicture(x, y + 1);
        calculateRoadPicture(x + 1, y);
    }

    /**
     * Berekent welke foto nodig is voor een weg gebruikmakend van de github formules
     * @param x
     * @param y
     */

    private void calculateRoadPicture(int x, int y) {
        Set<Integer> map = new HashSet<>();
        boolean left = coordsOfAlreadyPlaced.getOrDefault(x, map).contains(y - 1);
        boolean right = coordsOfAlreadyPlaced.getOrDefault(x, map).contains(y + 1);
        boolean up = coordsOfAlreadyPlaced.getOrDefault(x - 1, map).contains(y);
        boolean down = coordsOfAlreadyPlaced.getOrDefault(x + 1, map).contains(y);
        changeRoad(x, y, left, right, up, down);
    }

    private void changeRoad(int x, int y, boolean left, boolean right, boolean up, boolean down) {
        if (GridModel.validCoords(x, y) && sprites[x][y] instanceof Road) {
            int img = ((up) ? 1 : 0) + ((right) ? 2 : 0) + ((down) ? 4 : 0) + ((left) ? 8 : 0);
            sprites[x][y].setLevel(img);
        }
    }

    public void checkOut(int x, int y) {
        coordsOfAlreadyPlaced.get(x).remove(y);
    }

    /**
     * Behaviour van een weg.
     * @param beginx
     * @param beginy
     * @param size
     */

    @Override
    protected void helper(int beginx, int beginy, int size) {
        if (sprites[beginx][beginy] == null) {
            Sprite road = new Road();
            Point point = GameGrid.convertRowCol(beginx, beginy);
            road.setX(point.getX());
            road.setY(point.getY());
            setSprite(beginx, beginy, road);
            checkIn(beginx, beginy);
            update(beginx, beginy);
            road.setViewOrder(-beginx - beginy - 2.0);
        }
    }
}
