package polis.simulation;

import polis.sprites.Commerce;
import polis.sprites.Residence;
import polis.sprites.Sprite;

import java.util.List;

import static polis.Config.COLOR_STATIONARY;

/**
 * Representatie van een klant.
 */
public class Customer extends Actor {

    private final Commerce commerce;

    public Customer(Simulation simulation, int beginx, int beginy, Commerce commerce, Residence home) {
        super(COLOR_STATIONARY, (int) Simulation.get("customer.age"), simulation, beginx, beginy, home);
        this.commerce = commerce;
    }

    /**
     * Als een klant geen tijd meer heeft dan wordt hij een sleeper en pakt hij 1 als het mogelijk is.
     * @param sprites
     * @param directions
     * @return
     */
    @Override
    protected Actor foundDestination(Sprite[][] sprites, List<Direction> directions) {
        Actor actor = this;
        if (time_elapsed <= 0) {
            actor = new Sleeper(simulation, residence, r, k);
            residence.changeResident(this, actor);
            if (commerce.removeCustomer(this)) {
                commerce.goodTrade();
            } else {
                commerce.badTrade();
            }
        }
        return actor;
    }

    @Override
    protected void move(Sprite[][] sprites) {
        return;
    }
}
