package polis.simulation;

import java.util.ArrayList;
import java.util.List;

import static polis.Config.CELL_SIZE;

/**
 * Enum dat de 4 windrichtingen defineert.
 */
public enum Direction {

    NORTH(CELL_SIZE / 4, CELL_SIZE / 8, -1, 0),
    EAST(-CELL_SIZE / 4, CELL_SIZE / 8, 0, 1),
    WEST(CELL_SIZE / 4, -CELL_SIZE / 8, 0, -1),
    SOUTH(-CELL_SIZE / 4, -CELL_SIZE / 8, 1, 0);

    private final int xShift;
    private final int yShift;
    private final int dx;
    private final int dy;

    Direction(int xShift, int yShift, int dx, int dy) {
        this.xShift = xShift;
        this.yShift = yShift;
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Geeft linker en rechter richting van een windrichting en de richting zelf terug.
     * @param direction
     * @return
     */
    public static List<Direction> giveLeftRightForDirection(Direction direction) {
        List<Direction> directions = new ArrayList<>();

        if (!Direction.NORTH.equals(direction)) {
            directions.add(Direction.SOUTH);
        }

        if (!Direction.SOUTH.equals(direction)) {
            directions.add(Direction.NORTH);
        }

        if (!Direction.EAST.equals(direction)) {
            directions.add(Direction.WEST);
        }

        if (!Direction.WEST.equals(direction)) {
            directions.add(Direction.EAST);
        }

        return directions;
    }

    public static Direction giveOpposite(Direction direction) {
        Direction res = null;
        switch (direction) {
            case SOUTH:
                res = NORTH;
                break;
            case WEST:
                res = EAST;
                break;
            case NORTH:
                res = SOUTH;
                break;
            case EAST:
                res = WEST;
                break;
        }
        return res;
    }

    public int getxShift() {
        return xShift;
    }

    public int getyShift() {
        return yShift;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
