package polis.simulation;

import polis.sprites.Industry;
import polis.sprites.Residence;
import polis.sprites.Sprite;

import java.util.List;

import static polis.Config.COLOR_STATIONARY;

/**
 * Abstracte representatie van een worker.
 */
public class Worker extends Actor {

    private final Industry industry;
    private final int homeX;
    private final int homeY;

    public Worker(Simulation simulation, Residence residence, int beginx, int beginy, int homex, int homey, Sprite ind) {
        super(COLOR_STATIONARY, (int) Simulation.get("worker.age"), simulation, beginx, beginy, residence);
        this.homeX = homex;
        this.homeY = homey;
        this.industry = (Industry) ind;
    }

    /**
     * Een worker zit in een fabriek en produceert goederen en daarna wordt hij een shopper.
     * @param sprites
     * @param directions
     * @return
     */
    @Override
    protected Actor foundDestination(Sprite[][] sprites, List<Direction> directions) {
        Actor actor = this;
        if (time_elapsed <= -1) {
            industry.removeWorker(this);
            actor = new Shopper(simulation, residence, homeX, homeY);
            residence.changeResident(this, actor);
        } else if (time_elapsed % ((int) (Simulation.get("steps.per.goods") + 1) - 1) == 0) {
            simulation.addGood(new Goods(simulation, r, k, industry));
        }
        return actor;
    }

    @Override
    protected void move(Sprite[][] sprites) {
        return;
    }
}
