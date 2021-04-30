package polis.simulation;

import polis.models.GridModel;
import polis.sprites.Commerce;
import polis.sprites.Industry;
import polis.sprites.Residence;
import polis.sprites.Sprite;

import java.util.List;

import static polis.Config.JOB_SEEKER_COLOR;

/**
 * Abstracte representatie van een werkzoekende.
 */
public class JobSeeker extends Actor {

    private final int homeX;
    private final int homeY;

    public JobSeeker(Simulation simulation, Residence residence, int beginx, int beginy) {
        super(JOB_SEEKER_COLOR, (int) Simulation.get("jobseeker.age"), simulation, beginx, beginy, residence);
        this.homeX = beginx;
        this.homeY = beginy;
    }

    /**
     * Als een wekzoekende geen tijd meer heeft wordt hij een sleeper en notify hij zijn huis (capaciteit neemt af).
     * Anders gaat hij doorzoeken naar een job door links en rechts te kijken.
     * @param sprites
     * @param directions
     * @return
     */

    @Override
    protected Actor foundDestination(Sprite[][] sprites, List<Direction> directions) {
        Actor actor = this;
        if (time_elapsed <= 0) {
            actor = new Sleeper(simulation, residence, homeX, homeY);
            residence.capacityMustChange(Simulation.get("factor.job.not.found"));
            residence.changeResident(this, actor);
        } else {
            for (Direction d : directions) {
                int r = this.r + d.getDx();
                int k = this.k + d.getDy();
                if (GridModel.validCoords(r, k) && (sprites[r][k] instanceof Industry || sprites[r][k] instanceof Commerce)) {
                    actor = helper(sprites[r][k]);
                    break;
                }
            }
        }
        return actor;
    }

    private Actor helper(Sprite sprite) {
        Actor actor = this;
        if (sprite instanceof Industry && ((Industry) sprite).spaceAvailable()) {
            actor = new Worker(simulation, residence, r, k, homeX, homeY, sprite);
            ((Industry) sprite).addWorker(actor);
            residence.changeResident(this, actor);
            residence.capacityMustChange(Simulation.get("factor.job.found"));
        } else if (sprite instanceof Commerce && ((Commerce) sprite).workerSpaceAvailable()) {
            actor = new Trader(simulation, residence, homeX, homeY, sprite);
            ((Commerce) sprite).addWorker(actor);
            residence.changeResident(this, actor);
            residence.capacityMustChange(Simulation.get("factor.job.found"));
        }
        return actor;
    }
}
