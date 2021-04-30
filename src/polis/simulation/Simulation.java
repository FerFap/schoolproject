package polis.simulation;

import polis.gamegrid.GameGrid;
import polis.sprites.Sprite;

import java.io.InputStream;
import java.util.*;

import static polis.Config.NUM_CELLS;

/**
 * Abstracte representatie van de simulatie.
 */
public class Simulation {

    private static final Map<String, Double> simulationValues = new HashMap<>();
    private final GameGrid gameGrid;
    private final Sprite[][] sprites;
    private Set<Actor> actors;
    private Region region;
    private final Set<Actor> toEliminate;
    private Set<Actor> toKeep;

    public Simulation(GameGrid gameGrid, Sprite[][] sprites) {
        this.gameGrid = gameGrid;
        this.sprites = sprites;
        this.actors = new HashSet<>();
        this.toEliminate = new HashSet<>();
        loadSimulationValues("polis/engine.properties");
        loadSimulationValues("polis/levels.properties");
    }

    /**
     * Geeft een propertyfile waarde door.
     * @param string
     * @return
     */

    public static double get(String string) {
        return simulationValues.get(string);
    }

    /**
     * Removed alle actors die weg moeten en update die dat mogen verder leven.
     */

    public void update() {
        toKeep = new HashSet<>();
        for (Actor actor : actors) {
            Actor actor1 = actor.update(sprites);
            if (actor1 != null && actor.getTime() >= -1) {
                toKeep.add(actor1);
            }
        }
        gameGrid.getChildren().removeAll(actors);
        gameGrid.getChildren().addAll(toKeep);
        actors = toKeep;
    }

    public void sendImmigrant() {
        Actor immigrant = new Immigrant(this, region, 0, NUM_CELLS - NUM_CELLS / 3 - 1);
        gameGrid.getChildren().add(immigrant);
        actors.add(immigrant);
    }

    private void loadSimulationValues(String file) {
        try {
            InputStream reader = getClass().getClassLoader().getResourceAsStream(file);
            Properties properties = new Properties();
            properties.load(reader);
            properties.forEach((e, b) ->
                    simulationValues.put(
                            e.toString(), Double.parseDouble(b.toString())
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setRegion(Region region) {
        this.region = region;
    }

    public void addGood(Goods goods) {
        toKeep.add(goods);
    }

}
