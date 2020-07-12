/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event_sim_core;

import event_sim_core.event.Event;
import event_sim_core.event.SystemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Všeobecné jadro udalostne orientovanej simulácie, definuje abstraktné metódy, podporuje pauzu, stop, replikačný mód a implmenetuje samotný cyklus event kalendára
 * @author MarekPC
 */
public abstract class Simulation_Core {

    private double currentTime, maxTime;
    private int currentReplication, replicationsDone;
    private double systemEventPlanTime;
    private double systemEventSleepTime;

    private PriorityQueue<Event> eventQueue;
    private boolean  simulationStopped;
    private int totalReplications;
    private Random seedsGenerator;
    private boolean coolingOn, paused;
    private boolean isCooling;
    private boolean simulationDone;
    List<Sim_GUI> registeredGuis;

    public Simulation_Core(double maxTime, int iterations, long seed, boolean cooling) {
        //random seed
        if (seed == -1) {
            this.seedsGenerator = new Random();
        } else {
            this.seedsGenerator = new Random(seed);
        }

        this.currentReplication = 0;
        this.maxTime = maxTime;
        this.totalReplications = iterations;
        this.eventQueue = new PriorityQueue<>();
        this.coolingOn = cooling;
        this.paused = this.simulationStopped = false;
        this.registeredGuis = new ArrayList<>();
        this.systemEventPlanTime = 1.0;
        this.systemEventSleepTime = 1000;
        this.replicationsDone = 0;
        this.simulationDone = false;

    }

    public void setSystemEventPlanTime(double systemEventPlanTime) {
        this.systemEventPlanTime = systemEventPlanTime;
    }

    public void setSystemEventSleepTime(double systemEventSleepTime) {
        this.systemEventSleepTime = systemEventSleepTime;
    }

    public double getSystemEventPlanTime() {
        return systemEventPlanTime;
    }

    public double getSystemEventSleepTime() {
        return systemEventSleepTime;
    }

    public boolean simulationDone() {
        return simulationDone;
    }

    public void runSimulation() {
        beforeSimulation();

        for (; currentReplication < totalReplications; currentReplication++) {
//            System.out.println(currentReplication);
            this.eventQueue.clear();
            if (totalReplications == 1) {
                addEvent(new SystemEvent(this, 0.0));
            }
            this.currentTime = 0;
            beforeReplication();
            while (currentTime <= maxTime && !eventQueue.isEmpty()) {
                while (paused) {
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Simulation_Core.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                Event e = eventQueue.poll();
                currentTime = e.getTime();

                e.execute();
                afterEvent();
                if (simulationStopped) {
                    break;
                }

            }
            if (simulationStopped) {
                break;
            }
            if (coolingOn) {
                isCooling = true;
                while (!eventQueue.isEmpty()) {
                    while (paused) {
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Simulation_Core.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    Event e = eventQueue.poll();
                    if (eventQueue.isEmpty() && e instanceof SystemEvent) {
                        break;
                    }
                    currentTime = e.getTime();
                    e.execute();
                    afterEvent();
                    if (simulationStopped) {
                        break;
                    }

                }
                if (simulationStopped) {
                    break;
                }

            }
            replicationsDone++;
            afterReplication();
        }
        simulationDone = true;
        afterSimulation();
    }

    public void pauseSimulation() {
        this.paused = true;
    }

    public void continueSimulation() {
        this.paused = false;
    }

    public void stopSimulation() {
        this.simulationStopped = true;
    }

    public boolean isCooling() {
        return isCooling;
    }

    public void registerGUI(Sim_GUI gui) {
        this.registeredGuis.add(gui);
    }

    public boolean replicationsMode() {
        return totalReplications != 1;
    }

    public int currentReplication() {
        return currentReplication;
    }

    public void refreshGUI() {
        for (Sim_GUI registeredGui : registeredGuis) {
            registeredGui.refresh(this);
        }
    }

    public void refreshGUItime() {
        for (Sim_GUI registeredGui : registeredGuis) {
            registeredGui.refreshOnlyTime(this);
        }
    }

    public void afterSimulationGUInotify() {
        for (Sim_GUI registeredGui : registeredGuis) {
            registeredGui.afterSimulation(this);
        }
    }

    public int replicationsDone() {
        return replicationsDone;
    }

    public int getCurrentReplication() {
        return currentReplication;
    }

    public boolean timeElapsed() {
        return currentTime > maxTime;
    }

    public void addEvent(Event ev) {
        eventQueue.add(ev);
    }

    public int generateSeed() {
        return seedsGenerator.nextInt();
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public abstract void beforeSimulation();

    public abstract void afterSimulation();

    public abstract void afterEvent();

    public abstract void beforeReplication();

    public abstract void afterReplication();

}
