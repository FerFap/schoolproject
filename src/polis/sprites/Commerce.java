package polis.sprites;


import polis.simulation.Actor;
import polis.simulation.Simulation;

import java.util.*;

/**
 * Abstracte representatie van een winkel.
 */
public class Commerce extends Sprite {

    private double numGoods;
    private double maxGoods;
    private final Map<Actor, Double> workers;
    private final Set<Actor> clients;

    public Commerce() {
        super(2, 4, Math.ceil(Simulation.get("commercial.capacity.initial") / Simulation.get("customers.per.trader")));
        this.numGoods = 0;
        this.workers = new HashMap<>();
        this.clients = new HashSet<>();
        this.maxGoods = this.capacity * Simulation.get("customers.per.trader") * Simulation.get("goods.per.customer");
    }

    /**
     * Informatie voor het paneel.
     * @return
     */
    @Override
    public String getInfo() {
        int work = workers.keySet().size();
        return String.format("Jobs: %.1f / %.1f\nGoods: %.1f / %.1f\nClients: %.1f / %.1f", work * 1.0, capacity, numGoods, maxGoods, clients.size() * 1.0, work * Simulation.get("customers.per.trader"));
    }

    @Override
    public String getTitle(int r, int k) {
        return "Commercial @ " + r + ":" + k;
    }

    @Override
    public String pictureLocation() {
        return "polis/tiles/commerce-" + level + ".png";
    }

    public double getClients() {
        return clients.size();
    }

    public void addWorker(Actor actor) {
        if (level == 0) {
            upgrade();
        }
        workers.put(actor, Simulation.get("customers.per.trader"));
        check();
        this.maxGoods = this.capacity * Simulation.get("customers.per.trader") * Simulation.get("goods.per.customer");
    }

    public void addCustomer(Actor actor) {
        Actor thisWorker = null;
        for (Actor worker : workers.keySet()) {
            if (workers.get(worker) > 0) {
                thisWorker = worker;
            }
        }
        workers.put(thisWorker, workers.get(thisWorker) - 1);
        clients.add(actor);
    }

    public void addGood() {
        numGoods++;
    }

    public boolean removeCustomer(Actor actor) {
        clients.remove(actor);
        double maxTime = 0;
        Actor w = null;
        for (Actor worker : workers.keySet()) {
            if (worker.getTime() > maxTime && workers.get(worker) < 3) {
                w = worker;
                maxTime = worker.getTime();
            }
        }
        if (w != null) {
            workers.put(w, workers.get(w) + 1);
        }
        if (numGoods >= 1) {
            numGoods--;
            return true;
        }
        return false;
    }

    public void removeWorker(Actor actor) {
        workers.remove(actor);
        check();
    }

    public void goodTrade() {
        capacity *= Simulation.get("factor.good.trade");
        check();
        this.maxGoods = this.capacity * Simulation.get("customers.per.trader") * Simulation.get("goods.per.customer");
    }

    public void badTrade() {
        capacity *= Simulation.get("factor.bad.trade");
        check();
        this.maxGoods = this.capacity * Simulation.get("customers.per.trader") * Simulation.get("goods.per.customer");
    }

    public boolean workerSpaceAvailable() {
        return workers.size() + 1 <= capacity;
    }

    public boolean goodsSpaceAvailable() {
        return numGoods + 1 <= maxGoods;
    }

    public boolean clientSpaceAvailable() {
        return clients.size() + 1 <= getClientCapacity();
    }

    public double getGoods() {
        check();
        this.maxGoods = this.capacity * Simulation.get("customers.per.trader") * Simulation.get("goods.per.customer");
        return numGoods;
    }

    public double getGoodsCapacity() {
        check();
        this.maxGoods = this.capacity * Simulation.get("customers.per.trader") * Simulation.get("goods.per.customer");
        return maxGoods;
    }

    public double getJobCapacity() {
        check();
        this.maxGoods = this.capacity * Simulation.get("customers.per.trader") * Simulation.get("goods.per.customer");
        return capacity;
    }

    public double getNumWorkers() {
        check();
        this.maxGoods = this.capacity * Simulation.get("customers.per.trader") * Simulation.get("goods.per.customer");
        return workers.keySet().size();
    }

    public double getClientCapacity() {
        double res = 0;
        for (Actor actor : workers.keySet()) {
            res += workers.get(actor);
        }
        return res * Simulation.get("customers.per.trader");
    }

    /**
     * Removed clients en workers die dood zijn.
     */
    public void checkForInactive(){
        List<Actor> toRemove = new ArrayList<>();
        for(Actor actor: workers.keySet()){
            if(actor.getTime() < -1){
                toRemove.add(actor);
            }
        }

        for(Actor actor: clients){
            if(actor.getTime() < -1){
                toRemove.add(actor);
            }
        }

        for(Actor actor: toRemove){
            if(workers.containsKey(actor)){
                workers.remove(actor);
            } else {
                clients.remove(actor);
            }
        }
    }
}
