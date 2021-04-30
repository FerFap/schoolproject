package polis.simulation;

import polis.models.GridModel;
import polis.sprites.Commerce;
import polis.sprites.Industry;
import polis.sprites.Residence;
import polis.sprites.Sprite;

import java.util.List;

import static polis.Config.GOODS_COLOR;

/**
 * Abstracte representatie van goederen.
 */
public class Goods extends Actor {

    private final Industry industry;

    public Goods(Simulation simulation, int beginx, int beginy, Industry industry) {
        super(GOODS_COLOR, (int) Simulation.get("goods.age"), simulation, beginx, beginy, new Residence());
        this.industry = industry;
    }

    /**
     * Als een grondstof geent ijd meer heeft dan notify hij zijn bedrijf en verdwijnt hij.
     * Anders gaan hij random bewegen en een commerce verderzoeken door links en rechts te kijken.
     * @param sprites
     * @param directions
     * @return
     */
    @Override
    protected Actor foundDestination(Sprite[][] sprites, List<Direction> directions) {
        Actor actor = this;
        if (time_elapsed <= 0) {
            industry.goodNotDelivered();
            actor = null;
        } else {
            for (Direction d : directions) {
                int r = this.r + d.getDx();
                int k = this.k + d.getDy();
                if (GridModel.validCoords(r, k) && sprites[r][k] instanceof Commerce && ((Commerce) sprites[r][k]).goodsSpaceAvailable()) {
                    ((Commerce) sprites[r][k]).addGood();
                    industry.goodDelivered();
                    actor = null;
                    break;
                }
            }
        }
        return actor;
    }


}
