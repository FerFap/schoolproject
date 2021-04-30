package polis.sprites;


import polis.simulation.Actor;
import polis.simulation.Simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstracte representatie van een huis.
 */
public class Residence extends Sprite {

    private final List<Actor> residents;

    public Residence() {
        super(2, 4, Simulation.get("residential.capacity.initial"));
        this.residents = new ArrayList<>();
    }

    public double getNumResidents() {
        return residents.size();
    }

    public double getCapacity() {
        return capacity;
    }

    public void deleteResidents() {
        for (Actor actor : residents) {
            actor.deleteIself();
        }
    }

    /**
     * Delete mensen die dood zijn.
     */
    public void checkForInactive(){
        List<Actor> toRemove = new ArrayList<>();
        for(Actor actor: residents){
            if(actor.getTime() < -1){
                toRemove.add(actor);
            }
        }
        residents.removeAll(toRemove);
    }

    public void addResident(Actor actor) {
        if (level == 0) {
            upgrade();
        }
        residents.add(actor);
        check();
        helper();
    }

    public void changeResident(Actor old, Actor replacement) {
        int index = residents.indexOf(old);
        if (index != -1) {
            residents.set(residents.indexOf(old), replacement);
            check();
            helper();
        } else {
            addResident(replacement);
        }
    }

    public boolean spaceAvailable() {
        return residents.size() + 1 <= capacity;
    }

    public void capacityMustChange(double factor) {
        capacity *= factor;
        check();
        helper();
    }

    private void helper() {
        for (int i = residents.size() - 1; (i >= 0) && residents.size() > capacity; i--) {
            residents.get(i).deleteIself();
            residents.remove(i);
        }
    }

    /**
     * Info voor informatiepaneel.
     * @return
     */
    @Override
    public String getInfo() {
        return String.format("Residents: %.1f / %.1f", (residents.size() * 1.0), capacity);
    }

    @Override
    public String getTitle(int r, int k) {
        return "Residential @ " + r + ":" + k;
    }

    @Override
    public String pictureLocation() {
        return "polis/tiles/residence-" + level + ".png";
    }
}
