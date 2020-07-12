/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.events.customerWaiterEvents;

import diss_2.restauracia.Restauracia_Core;
import diss_2.restauracia.events.CustomerWaiterEvent;
import diss_2.restauracia.objects.CustomerGroup;
import diss_2.restauracia.objects.Waiter;
import event_sim_core.event.Event;

/**
 *
 * @author MarekPC
 */
public class StartPayingEvent extends CustomerWaiterEvent {

    public StartPayingEvent(Restauracia_Core myCore, double time, CustomerGroup customerGroup, Waiter waiter) {
        super(myCore, time, customerGroup, waiter);
    }

    @Override
    public void execute() {
        customerGroup.setStartOfPayingTime(time);
//        myCore.waitersManagment.setWorking(waiter);
        double payingTime = myCore.generators.generatePayingTime();
        waiter.addToWorkedTime(payingTime);
        myCore.addEvent(new EndOfPayingEvent(myCore, time + payingTime, customerGroup, waiter));
//        Event waiterEvent = Waiter_Cook_EventMaker.make_waiter_event(myCore, time);
//        if (waiterEvent != null) {
//            myCore.addEvent(waiterEvent);
//        }

    }

}
