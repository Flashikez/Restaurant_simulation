/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event_sim_core.event;

import event_sim_core.Simulation_Core;

/**
 * Hlavný predok všetkých eventov, ak chce byť event vložiteľný do kalendára, musí mať tohto predka
 * @author MarekPC
 */
public abstract class Event implements Comparable<Event>{

    protected Simulation_Core core;
    protected double time;

    public abstract void execute();
    

    public Event(Simulation_Core core, double time) {
        this.core = core;
        this.time = time;
    }

    public double getTime() {
        return this.time;
    }

    @Override
    public int compareTo(Event t) {
       return Double.compare(this.time, t.time);
        
    }

}
