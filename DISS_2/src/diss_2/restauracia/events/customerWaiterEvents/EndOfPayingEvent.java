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
import java.util.List;

/**
 *
 * @author MarekPC
 */
public class EndOfPayingEvent extends CustomerWaiterEvent {

    public EndOfPayingEvent(Restauracia_Core myCore, double time, CustomerGroup customerGroup, Waiter waiter) {
        super(myCore, time, customerGroup, waiter);
    }

    @Override
    public void execute() {
        myCore.waitersManagment.setNotWorking(waiter,time);
        myCore.statistics.totalCustomersOut.add(customerGroup.getGroupSize());
        customerGroup.setExitTime(time);
        myCore.tablesManagment.setFree(customerGroup.getTable(), time);
        if (!customerGroup.getOrder().isFinished()) {
            System.exit(255);
        }
//        double s = customerGroup.getOrder().list().stream().mapToDouble(e -> e.getTimeToMake()).max().getAsDouble();
//        if (customerGroup.getFoodDeliveredTime() != s) {
//            myCore.totalCookingTime.add(s);
//        }

        Event waiterEvent = Waiter_Event_Maker.make_waiter_event(myCore, time);
        if (waiterEvent != null) {
            myCore.addEvent(waiterEvent);
        }
        myCore.dispatchCustomer(customerGroup, true);
    }

}
