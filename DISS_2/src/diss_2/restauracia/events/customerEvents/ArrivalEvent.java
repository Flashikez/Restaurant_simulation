/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.events.customerEvents;

import diss_2.restauracia.Restauracia_Core;
import diss_2.restauracia.events.CustomerEvent;
import diss_2.restauracia.events.customerWaiterEvents.StartOrderEvent;
import diss_2.restauracia.events.customerWaiterEvents.Waiter_Event_Maker;
import diss_2.restauracia.objects.Activity;
import diss_2.restauracia.objects.CustomerGroup;
import diss_2.restauracia.objects.Table;
import diss_2.restauracia.objects.Waiter;
import event_sim_core.event.Event;

/**
 *
 * @author MarekPC
 */
public class ArrivalEvent extends CustomerEvent {

    public ArrivalEvent(Restauracia_Core core, double time, CustomerGroup group) {
        super(core, time, group);
    }

    @Override
    public void execute() {

        customerGroup.setArrivalTime(time);
        int groupSize = customerGroup.size();
        myCore.statistics.totalCustomersTotalIn.add(groupSize);
//        if (!myCore.timeElapsed()) {
        double generatedTime = myCore.generators.generateArrivalTime(groupSize);
        if (time + generatedTime <= myCore.getMaxTime()) {
            myCore.addEvent(new ArrivalEvent(myCore, time + generatedTime, new CustomerGroup(groupSize, ++myCore.customerIdCounter)));
        }
//        }

        Table table = myCore.tablesManagment.getFreeTable_lowestSizePossible(groupSize);
        // podarilo sa obsadit stol
        if (table != null) {
            myCore.statistics.totalCustomersIn.add(customerGroup.getGroupSize());
            myCore.tablesManagment.setOccupied(table, time, customerGroup);
            customerGroup.setTable(table);
            // Ak je casnik volny zahaj event zaciatokObjednavky
            Waiter assigned = myCore.waitersManagment.getLeastWorkedWaiter();
            if (assigned != null) {
                myCore.waitersManagment.setWorking(assigned, time, Activity.ORDER, customerGroup);
                myCore.addEvent(new StartOrderEvent(myCore, time, customerGroup, assigned));

                // ak casnik nie je volnmy zahaj event zaciatok cakania na casnika    
            } else {

                myCore.addTo_WaitingForOrderQueue(customerGroup, time);

            }

            // stol o potrebnej velkosti nebol volny
        } else {
            myCore.statistics.totalCustomersLeft.add(groupSize);
            myCore.dispatchCustomer(customerGroup, false);
        }

    }

}
