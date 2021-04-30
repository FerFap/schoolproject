package polis.simulation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import polis.gamegrid.GameGrid;
import polis.models.GridModel;
import polis.models.Point;
import polis.sprites.Residence;
import polis.sprites.Road;
import polis.sprites.Sprite;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static polis.Config.CELL_SIZE;

/**
 * Abstracte voorstelling van een actor voor alle klassen die dit overerven.
 */

public abstract class Actor extends Circle {

    protected Direction direction = Direction.SOUTH;
    protected int time_elapsed;
    protected int r;
    protected int k;
    protected Simulation simulation;
    protected Residence residence;

    public Actor(Color color, int time_elapsed, Simulation simulation, int beginx, int beginy, Residence residence) {
        super(0, CELL_SIZE / 2, CELL_SIZE / 6);
        this.time_elapsed = time_elapsed;
        this.simulation = simulation;
        this.residence = residence;
        setMouseTransparent(true);
        setFocusTraversable(false);
        setFill(color);
        update(beginx, beginy);
    }

    /**
     * Update de plaats van een actor.
     * @param r
     * @param k
     */

    protected void update(int r, int k) {
        this.r = r;
        this.k = k;
        Point point = GameGrid.convertRowCol(r, k);
        setTranslateX(point.getX() + direction.getxShift());
        setTranslateY(point.getY() + direction.getyShift());
        setViewOrder(-r - k - 2.5);
    }

    /**
     * Update de zin van een actor.
     * @param direction
     */

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Update de leeftijd van een actor.
     * @param sprites
     * @return
     */

    public Actor update(Sprite[][] sprites) {
        --time_elapsed;
        move(sprites);
        List<Direction> directions = Direction.giveLeftRightForDirection(direction);
        directions.remove(direction);
        Collections.shuffle(directions);
        return foundDestination(sprites, directions);
    }

    /**
     * Beweegt een actor in een van de 3 toelaatbare richtingen en als laatste keuze in de terugrichting.
     * @param sprites
     */

    protected void move(Sprite[][] sprites) {
        List<Direction> directions = Direction.giveLeftRightForDirection(direction);
        Collections.shuffle(directions, new Random());
        directions.add(Direction.giveOpposite(direction));
        int i = 0;
        while (i < 4 && !helper(r, k, directions.get(i++), sprites)) ;
    }

    protected boolean helper(int r, int k, Direction direction, Sprite[][] sprites) {
        r += direction.getDx();
        k += direction.getDy();
        if (GridModel.validCoords(r, k) && sprites[r][k] instanceof Road) {
            setDirection(direction);
            update(r, k);
            return true;
        }
        return false;
    }

    /**
     * Moet door de kinderen geimplementeerd worden zodat ze terug kunnen geven welk object behouden wordt,
     * zichzelf of een nieuw object.
     * @param sprites
     * @param directions
     * @return
     */

    protected abstract Actor foundDestination(Sprite[][] sprites, List<Direction> directions);

    public int getTime() {
        return time_elapsed;
    }

    public void deleteIself() {
        time_elapsed = -10;
    }
}
