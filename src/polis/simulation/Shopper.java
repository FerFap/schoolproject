package polis.simulation;

import polis.models.GridModel;
import polis.sprites.Commerce;
import polis.sprites.Residence;
import polis.sprites.Sprite;

import java.util.List;

import static polis.Config.SHOPPER_COLOR;

/**
 * Abstracte representatie van een shopper.
 */
public class Shopper extends Actor {

    private final int homeX;
    private final int homeY;

    public Shopper(Simulation simulation, Residence residence, int beginx, int beginy) {
        super(SHOPPER_COLOR, (int) Simulation.get("shopper.age"), simulation, beginx, beginy, residence);
        this.homeX = beginx;
        this.homeY = beginy;
    }

    /**
     * Als een shopper geen winkel vindt dan wordt hij een sleeper en notify hij zijn huis (capaciteit neemt af).
     * Anders zoekt hij verder naar een winkel door links en rechts te kijken.
     * @param sprites
     * @param directions
     * @return
     */
    @Override
    protected Actor foundDestination(Sprite[][] sprites, List<Direction> directions) {
        Actor actor = this;
        if (time_elapsed <= 0) {
            actor = new Sleeper(simulation, residence, homeX, homeY);
            residence.changeResident(this, actor);
            residence.capacityMustChange(Simulation.get("factor.shop.not.found"));
        } else {
            for (Direction d : directions) {
                int r = this.r + d.getDx();
                int k = this.k + d.getDy();
                if (GridModel.validCoords(r, k) && sprites[r][k] instanceof Commerce) {
                    actor = helper(sprites[r][k]);
                    break;
                }
            }
        }
        return actor;
    }

    private Actor helper(Sprite sprite) {
        Actor actor = this;
        if (((Commerce) sprite).clientSpaceAvailable()) {
            actor = new Customer(simulation, homeX, homeY, (Commerce) sprite, residence);
            ((Commerce) sprite).addCustomer(actor);
            residence.changeResident(this, actor);
            residence.capacityMustChange(Simulation.get("factor.shop.found"));

        }
        return actor;
    }
}
