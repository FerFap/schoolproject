package polis.simulation;

import polis.sprites.Residence;
import polis.sprites.Sprite;

import java.util.List;

import static polis.Config.COLOR_STATIONARY;

/**
 * Abstracte representatie van een slaper.
 */
public class Sleeper extends Actor {


    public Sleeper(Simulation simulation, Sprite sprite, int beginx, int beginy) {
        super(COLOR_STATIONARY, (int) Simulation.get("sleeper.age"), simulation, beginx, beginy, (Residence) sprite);
    }

    /**
     * Een slaper wordt een werkzoekende na enkele ticks.
     * @param sprites
     * @param directions
     * @return
     */
    @Override
    protected Actor foundDestination(Sprite[][] sprites, List<Direction> directions) {
        Actor actor = this;
        if (time_elapsed <= 0) {
            actor = new JobSeeker(simulation, residence, r, k);
            residence.changeResident(this, actor);
        }
        return actor;
    }

    @Override
    protected void move(Sprite[][] sprites) {
    }
}
