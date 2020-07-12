/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia;

import event_sim_core.statictics.Basic_Statistics;

/**
 * Pomocná trieda, ktorá si drží všetky sledované štatistiky simulácie
 * @author MarekPC
 */
public class Statistics {

    public Basic_Statistics totalCustomerWaitingTime, customersWaitingTimeoverReplications;
    public Basic_Statistics totalCustomersIn, customersInOverReplications;
    public Basic_Statistics totalCustomersOut, customersOutOverReplications;
    public Basic_Statistics totalCustomersLeft, customersLeftOverReplications;
    public Basic_Statistics totalCustomersTotalIn, customersTotalInOverReplications;
    public Basic_Statistics percentLeftOverReplications;
    public Basic_Statistics _2tablesFreeoverReplications, _4tablesFreeoverReplications, _6tablesFreeoverReplications;
    public Basic_Statistics freeCooksOverReplications, freeWaitersOverReplications;
    public Basic_Statistics cooksFreeTimeOverReplications, waiterFreeTimeOverReplications;

    
    

    public Statistics() {
        this.totalCustomerWaitingTime = new Basic_Statistics();
        this.totalCustomersIn = new Basic_Statistics();
        this.totalCustomersOut = new Basic_Statistics();
        this.totalCustomersLeft = new Basic_Statistics();
        this.totalCustomersTotalIn = new Basic_Statistics();
        this.customersInOverReplications = new Basic_Statistics();
        this.customersLeftOverReplications = new Basic_Statistics();
        this.customersOutOverReplications = new Basic_Statistics();
        this.customersTotalInOverReplications = new Basic_Statistics();
        this.customersWaitingTimeoverReplications = new Basic_Statistics();
        this.percentLeftOverReplications = new Basic_Statistics();
        this._2tablesFreeoverReplications = new Basic_Statistics();
        this._4tablesFreeoverReplications = new Basic_Statistics();
        this._6tablesFreeoverReplications = new Basic_Statistics();
        this.freeCooksOverReplications = new Basic_Statistics();
        this.freeWaitersOverReplications = new Basic_Statistics();
        this.cooksFreeTimeOverReplications = new Basic_Statistics();
        this.waiterFreeTimeOverReplications = new Basic_Statistics();
    }

    public void reset() {
        this.totalCustomerWaitingTime.reset();
        this.totalCustomersIn.reset();
        this.totalCustomersOut.reset();
        this.totalCustomersLeft.reset();
        this.totalCustomersTotalIn.reset();
//        this._2tablesFreeoverReplications.reset();
//        this._4tablesFreeoverReplications.reset();
//        this._6tablesFreeoverReplications.reset();
//        this.freeCooksOverReplications.reset();
//        this.freeWaitersOverReplications.reset();
    }

    public void replicationDone() {
        customersInOverReplications.add(totalCustomersIn.sum());
        customersLeftOverReplications.add(totalCustomersLeft.sum());
        customersTotalInOverReplications.add(totalCustomersTotalIn.sum());
        customersWaitingTimeoverReplications.add(totalCustomerWaitingTime.average());
        percentLeftOverReplications.add(totalCustomersLeft.sum() / totalCustomersTotalIn.sum());
    }

    public void averageLengthReplicationDone(double _2tables, double _4tables, double _6tables, double freeCooks, double freeWaiters) {
        this._2tablesFreeoverReplications.add(_2tables);
        this._4tablesFreeoverReplications.add(_4tables);
        this._6tablesFreeoverReplications.add(_6tables);
        this.freeCooksOverReplications.add(freeCooks);
        this.freeWaitersOverReplications.add(freeWaiters);
    }

    public void averageFreeTimeReplicationDone(double cooksFreeTime, double waiterFreeTime) {
        this.cooksFreeTimeOverReplications.add(cooksFreeTime);
        this.waiterFreeTimeOverReplications.add(waiterFreeTime);

    }

}
