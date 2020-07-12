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
import diss_2.restauracia.objects.Cook;
import diss_2.restauracia.objects.CustomerGroup;
import diss_2.restauracia.objects.Food;
import diss_2.restauracia.objects.Waiter;
import event_sim_core.event.Event;

/**
 *
 * @author MarekPC
 */
public class StartCookingEvent extends CookEvents {

    public StartCookingEvent(Restauracia_Core myCore, double time, CustomerGroup customerGroup, Cook cook, Food assignedFood) {
        super(myCore, time, customerGroup, cook, assignedFood);
    }

    @Override
    public void execute() {
//        myCore.cooksManagment.setWorking(cook);
        //double timeToCook = myCore.generators.generateFoodPreparationTime(customerGroup.size());
        double timeToCook = assignedFood.getTimeToMake();
        cook.addToWorkingTime(timeToCook);
        assignedFood.setCooking(true);

        myCore.addEvent(new FoodPreparedEvent(myCore, time + timeToCook, customerGroup, cook, assignedFood));

//       Event waiterEvent = Waiter_Cook_EventMaker.make_waiter_event(myCore, time);
//        if (waiterEvent != null) {
//            myCore.addEvent(waiterEvent);
//        }

    }

}
