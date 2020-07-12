/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.events;

import diss_2.restauracia.Restauracia_Core;
import diss_2.restauracia.objects.CustomerGroup;
import event_sim_core.Simulation_Core;
import event_sim_core.event.Event;

/**
 * Abstraktná trieda pre eventy zákazníkov
 * @author MarekPC
 */
public abstract class CustomerEvent extends Event {

    protected CustomerGroup customerGroup;
    protected Restauracia_Core myCore;

    public CustomerEvent(Restauracia_Core myCore, double time, CustomerGroup customerGroup) {
        super(myCore, time);
        this.customerGroup = customerGroup;
        this.myCore = myCore;
    }

    @Override
    public abstract void execute();

}
