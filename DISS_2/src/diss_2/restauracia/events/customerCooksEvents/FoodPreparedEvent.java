/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.events.customerCooksEvents;

import diss_2.restauracia.Restauracia_Core;
import diss_2.restauracia.events.CookEvents;
import diss_2.restauracia.events.CustomerCookEvent;
import diss_2.restauracia.events.customerWaiterEvents.StartOfFoodDeliverEvent;
import diss_2.restauracia.events.customerWaiterEvents.StartOrderEvent;
import diss_2.restauracia.events.customerWaiterEvents.StartPayingEvent;
import diss_2.restauracia.events.customerWaiterEvents.Waiter_Event_Maker;
import diss_2.restauracia.objects.Activity;
import diss_2.restauracia.objects.Cook;
import diss_2.restauracia.objects.CustomerGroup;
import diss_2.restauracia.objects.Food;
import diss_2.restauracia.objects.Food_Order;
import diss_2.restauracia.objects.Waiter;
import event_sim_core.event.Event;

/**
 *
 * @author MarekPC
 */
public class FoodPreparedEvent extends CookEvents {

    public FoodPreparedEvent(Restauracia_Core myCore, double time, CustomerGroup customerGroup, Cook cook, Food assignedFood) {
        super(myCore, time, customerGroup, cook, assignedFood);
    }

    @Override
    public void execute() {
        myCore.cooksManagment.setNotWorking(cook, time);
//        assignedFood.setCooking(true);

        assignedFood.setFinished(true);
        Food_Order order = assignedFood.getOrder();
        if (order.isFinished()) {
            if (myCore.waitersManagment.isAnyWaiterFree()) {
                Waiter waiter = myCore.waitersManagment.getFreeWaiter();
                assignedFood.getOrder().getGroup().setFoodPreparedTime(time);
                myCore.waitersManagment.setWorking(waiter, time, Activity.FOOD_DELIVERY, order.getGroup());
                myCore.addEvent(new StartOfFoodDeliverEvent(myCore, time, order.getGroup(), waiter));
            } else {
                myCore.addTo_WaitingForFoodDeliveryQueue(order.getGroup(), time);
            }
        }

        if (!myCore.isEmpty_WaitingForCookQueue()) {
            Food f = myCore.removeFrom_WaitingForCookQueue(time);
//          
            f.setCooking(true);
            myCore.cooksManagment.setWorking(cook, time);
//            cook.setWorking(true);
            myCore.addEvent(new StartCookingEvent(myCore, time, f.getOrder().getGroup(), cook, f));
        }

    }

}
