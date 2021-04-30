package polis.simulation;

import polis.sprites.Commerce;
import polis.sprites.Residence;
import polis.sprites.Sprite;

import java.util.List;

import static polis.Config.COLOR_STATIONARY;

/**
 * Abstracte representatie van een handelaar.
 */
public class Trader extends Actor {
    private Commerce commerce;

    public Trader(Simulation simulation, Residence residence, int beginx, int beginy, Sprite sprite) {
        super(COLOR_STATIONARY, (int) Simulation.get("trader.age"), simulation, beginx, beginy, residence);
        this.commerce = (Commerce) sprite;
        this.commerce = (Commerce) sprite;
    }

    /**
     * Een handelaar zit in een winkel en help mensen zolang hij tijd heeft en wordt daarna een shopper.
     * @param sprites
     * @param directions
     * @return
     */
    @Override
    protected Actor foundDestination(Sprite[][] sprites, List<Direction> directions) {
        Actor actor = this;
        if (time_elapsed <= 0) {
            actor = new Shopper(simulation, residence, r, k);
            residence.changeResident(this, actor);
            commerce.removeWorker(this);
        }
        return actor;
    }

    @Override
    protected void move(Sprite[][] sprites) {
        return;
    }
}
