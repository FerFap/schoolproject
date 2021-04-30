package polis.simulation;

import polis.models.GridModel;
import polis.sprites.Residence;
import polis.sprites.Sprite;

import java.util.List;

import static polis.Config.IMMIGRANT_COLOR;

/**
 * Abstracte representatie van een Immigrant
 */
public class Immigrant extends Actor {

    private final Region region;

    public Immigrant(Simulation simulation, Region region, int beginx, int beginy) {
        super(IMMIGRANT_COLOR, (int) Simulation.get("immigrant.age"), simulation, beginx, beginy, null);
        this.region = region;
    }

    /**
     * Als een Immigrant geen woning vindt dan vertraagt de regio en verdwijnt hij, anders zoekt hij verder door
     * links en rechts te kijken.
     * @param sprites
     * @param directions
     * @return
     */

    @Override
    protected Actor foundDestination(Sprite[][] sprites, List<Direction> directions) {
        Actor actor = this;
        if (time_elapsed <= 0) {
            region.slowDown();
            actor = null;
        } else {
            for (Direction d : directions) {
                int r = this.r + d.getDx();
                int k = this.k + d.getDy();
                if (GridModel.validCoords(r, k) && sprites[r][k] instanceof Residence && ((Residence) sprites[r][k]).spaceAvailable()) {
                    actor = new Sleeper(simulation, sprites[r][k], this.r, this.k);
                    ((Residence) sprites[r][k]).addResident(actor);
                    break;
                }
            }
        }
        return actor;
    }
}
