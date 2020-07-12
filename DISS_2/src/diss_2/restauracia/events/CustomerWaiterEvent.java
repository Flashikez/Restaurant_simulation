/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.events;

import diss_2.restauracia.Restauracia_Core;
import diss_2.restauracia.objects.CustomerGroup;
import diss_2.restauracia.objects.Waiter;
import event_sim_core.Simulation_Core;
import event_sim_core.event.Event;

/**
 * Abstraktná trieda pre eventy zákazníkov a čašníkov
 * @author MarekPC
 */
public abstract class CustomerWaiterEvent extends Event {

    protected Restauracia_Core myCore;
    protected CustomerGroup customerGroup;
    protected Waiter waiter;

    public CustomerWaiterEvent(Restauracia_Core myCore, double time, CustomerGroup customerGroup, Waiter waiter) {
        super(myCore, time);
        this.myCore = myCore;
        this.customerGroup = customerGroup;
        this.waiter = waiter;

    }

    @Override
    public abstract void execute();

}
