/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.events.customerWaiterEvents;

import diss_2.restauracia.Restauracia_Core;
import diss_2.restauracia.events.CustomerWaiterEvent;
import diss_2.restauracia.events.customerEvents.FoodFinishedEvent;
import diss_2.restauracia.objects.CustomerGroup;
import diss_2.restauracia.objects.Waiter;
import event_sim_core.event.Event;
import java.util.List;

/**
 *
 * @author MarekPC
 */
public class EndOfFoodDeliverEvent extends CustomerWaiterEvent {

    public EndOfFoodDeliverEvent(Restauracia_Core myCore, double time, CustomerGroup customerGroup, Waiter waiter) {
        super(myCore, time, customerGroup, waiter);
    }

    @Override
    public void execute() {
        customerGroup.setFoodDeliveredTime(time);
        myCore.waitersManagment.setNotWorking(waiter,time);
        
   
        Event waiterEvent = Waiter_Event_Maker.make_waiter_event(myCore, time);
        if (waiterEvent != null) {
            myCore.addEvent(waiterEvent);
        }

        double timetoEat = myCore.generators.generateEatingTime(customerGroup.size());
        myCore.addEvent(new FoodFinishedEvent(myCore, time + timetoEat, customerGroup));

    }

}
