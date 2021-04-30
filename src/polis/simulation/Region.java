package polis.simulation;

import java.util.Random;

/**
 * Abstracte representatie van de regio.
 */
public class Region {
    private static final Random rand = new Random();
    private final Simulation simulation;
    private double rate;
    private double count;
    private boolean running;

    public Region(Simulation simulation) {
        this.simulation = simulation;
        this.running = false;
        this.rate = Simulation.get("region.initial.rate");
        this.count = rand.nextInt((int) rate);
    }

    public void sendImmigrant() {
        if (running) {
            rate *= Simulation.get("region.factor.recovery");
            check();
            count--;
            simulation.update();
            if (count < 0) {
                simulation.sendImmigrant();
                count = rand.nextInt((int) rate);
            }
        }
    }

    public void setRunning() {
        running = !running;
    }

    public void slowDown() {
        rate *= Simulation.get("region.factor.slow.down");
        check();
    }

    private void check() {
        double slowest = Simulation.get("region.slowest.rate");
        double fastest = Simulation.get("region.initial.rate");
        if (rate < fastest) {
            rate = fastest;
        } else if (rate > slowest) {
            rate = slowest;
        }
    }
}
