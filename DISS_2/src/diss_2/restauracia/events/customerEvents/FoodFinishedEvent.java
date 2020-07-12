/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.events.customerEvents;

import diss_2.restauracia.Restauracia_Core;
import diss_2.restauracia.events.CustomerEvent;
import diss_2.restauracia.events.customerWaiterEvents.StartOfFoodDeliverEvent;
import diss_2.restauracia.events.customerWaiterEvents.StartOrderEvent;
import diss_2.restauracia.events.customerWaiterEvents.StartPayingEvent;
import diss_2.restauracia.events.customerWaiterEvents.Waiter_Event_Maker;
import diss_2.restauracia.objects.Activity;
import diss_2.restauracia.objects.CustomerGroup;
import diss_2.restauracia.objects.Waiter;
import event_sim_core.event.Event;

/**
 *
 * @author MarekPC
 */
public class FoodFinishedEvent extends CustomerEvent {

    public FoodFinishedEvent(Restauracia_Core myCore, double time, CustomerGroup customerGroup) {
        super(myCore, time, customerGroup);
    }

    @Override
    public void execute() {
        customerGroup.setFoodEatenTime(time);
        // alebo hned casnika pridelit??? asi nie, lebo by sa stratila priorita objednavky
//        myCore.addTo_WaitingForPayQueue(customerGroup, time);

        if (myCore.waitersManagment.isAnyWaiterFree()) {
            Waiter waiter = myCore.waitersManagment.getFreeWaiter();
            myCore.waitersManagment.setWorking(waiter, time, Activity.PAY, customerGroup);
            myCore.addEvent(new StartPayingEvent(myCore, time, customerGroup, waiter));
        } else {
            myCore.addTo_WaitingForPayQueue(customerGroup, time);
        }

    }

}
