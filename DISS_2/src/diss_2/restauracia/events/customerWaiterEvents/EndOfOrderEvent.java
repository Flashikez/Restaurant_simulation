/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.events.customerWaiterEvents;

import diss_2.restauracia.Restauracia_Core;
import diss_2.restauracia.events.CustomerWaiterEvent;
import diss_2.restauracia.events.customerCooksEvents.StartCookingEvent;
import diss_2.restauracia.objects.Cook;
import diss_2.restauracia.objects.CustomerGroup;
import diss_2.restauracia.objects.Food;
import diss_2.restauracia.objects.Food_Order;
import diss_2.restauracia.objects.Waiter;
import event_sim_core.event.Event;
import java.util.List;

/**
 *
 * @author MarekPC
 */
public class EndOfOrderEvent extends CustomerWaiterEvent {

    public EndOfOrderEvent(Restauracia_Core myCore, double time, CustomerGroup customerGroup, Waiter waiter) {
        super(myCore, time, customerGroup, waiter);
    }

    @Override
    public void execute() {
        customerGroup.setEndOfOrderTime(time);
        myCore.waitersManagment.setNotWorking(waiter,time);

        Event waiterEvent = Waiter_Event_Maker.make_waiter_event(myCore, time);

        if (waiterEvent != null) {
            myCore.addEvent(waiterEvent);
        }
//        myCore.waitersManagment.setWorking(waiter);
//        myCore.addEvent(new StartPayingEvent(myCore, time, customerGroup, waiter));

//        if (myCore.isEmpty_WaitingForCookQueue()) {
        List<Double> l = myCore.generators.generateFoodPreparationTime(customerGroup.size());
        Food_Order order = Food_Order.CREATOR.constructFromTimeList(l, customerGroup);
        customerGroup.setOrder(order);
        List<Food> orderList = order.list();

        for (Food food : orderList) {
            food.setOrder(order);

        }
        int numOfFreeCooks = myCore.cooksManagment.numberOfFreeCooks();
//        int foodNum = 0;
//        if (numOfFreeCooks < orderList.size()) {
//            myCore.addTo_WaitingForCookQueue(, time);
//        }
        int assignedCooks = 0;
        for (Food food : orderList) {
            if (assignedCooks < numOfFreeCooks) {
                Cook freeCook = myCore.cooksManagment.getLeastWorkedCook();
                food.setCooking(true);
                myCore.cooksManagment.setWorking(freeCook, time);
//                freeCook.setWorking(true);
                myCore.addEvent(new StartCookingEvent(myCore, time, customerGroup, freeCook, food));
                assignedCooks++;
            } else {
                myCore.addTo_WaitingForCookQueue(food, time);

            }

        }

//        } else {
//            myCore.addTo_WaitingForCookQueue(customerGroup, time);
//        }
    }
}
