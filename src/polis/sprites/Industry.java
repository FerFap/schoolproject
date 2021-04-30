package polis.sprites;


import polis.simulation.Actor;
import polis.simulation.Simulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstracte representatie van een industriezone.
 */
public class Industry extends Sprite {

    private final Set<Actor> workers;

    public Industry() {
        super(2, 4, Simulation.get("industrial.capacity.initial"));
        workers = new HashSet<>();
    }

    /**
     * Informatie voor het informatiepaneel.
     * @return
     */
    @Override
    public String getInfo() {
        return String.format("Jobs: %.1f / %.1f", workers.size() * 1.0, capacity);
    }

    @Override
    public String getTitle(int r, int k) {
        return "Industrial @ " + r + ":" + k;
    }

    @Override
    public String pictureLocation() {
        return "polis/tiles/industry-" + level + ".png";
    }

    public void addWorker(Actor actor) {
        if (level == 0) {
            upgrade();
        }
        workers.add(actor);
    }

    public void removeWorker(Actor actor) {
        workers.remove(actor);
    }

    public boolean spaceAvailable() {
        return workers.size() + 1 <= capacity;
    }

    public void goodDelivered() {
        capacity *= Simulation.get("factor.goods.delivered");
        check();
    }

    public void goodNotDelivered() {
        capacity *= Simulation.get("factor.goods.not.delivered");
        check();
    }

    public double getJobCapacity() {
        return capacity;
    }

    public double getNumWorkers() {
        return workers.size();
    }

    /**
     * Removed werkers die dood zijn.
     */
    public void checkForInactive(){
        List<Actor> toRemove = new ArrayList<>();
        for(Actor actor: workers){
            if(actor.getTime() < -1){
                toRemove.add(actor);
            }
        }
        workers.remove(toRemove);
    }
}