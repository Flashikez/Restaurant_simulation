/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.events.customerWaiterEvents;

import diss_2.restauracia.Restauracia_Core;
import diss_2.restauracia.objects.Activity;
import diss_2.restauracia.objects.CustomerGroup;
import diss_2.restauracia.objects.Waiter;
import event_sim_core.event.Event;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MarekPC
 */
public class Waiter_Event_Maker {

    public static Event make_waiter_event(Restauracia_Core myCore, double time) {

        Event e = null;
        Waiter leastWorkedWaiter = myCore.waitersManagment.getLeastWorkedWaiter();
        if (leastWorkedWaiter == null) {
            return null;
        }

        if (!myCore.isEmpty_WaitingForOrderQueue()) {
            CustomerGroup group = myCore.removeFrom_WaitingForOrderQueue(time);
            myCore.waitersManagment.setWorking(leastWorkedWaiter, time, Activity.ORDER, group);
            //myCore.waitersManagment.getLeastWorkedWaiter()

            e = new StartOrderEvent(myCore, time, group, leastWorkedWaiter);
        } else if (!myCore.isEmpty_WaitingForFoodDeliverQueue()) {
            CustomerGroup group = myCore.removeFrom_WaitingForFoodDeliverQueue(time);
            myCore.waitersManagment.setWorking(leastWorkedWaiter, time, Activity.FOOD_DELIVERY, group);
            // start of foodDelireing
            e = new StartOfFoodDeliverEvent(myCore, time, group, leastWorkedWaiter);
        } else if (!myCore.isEmpty_WaitingForPayQueue()) {
            CustomerGroup group = myCore.removeFrom_WaitingForPayQueue(time);
            myCore.waitersManagment.setWorking(leastWorkedWaiter, time, Activity.PAY, group);
            // start of pay
            e = new StartPayingEvent(myCore, time, group, leastWorkedWaiter);
        } else {
            e = null;
        }
        return e;
    }

}
