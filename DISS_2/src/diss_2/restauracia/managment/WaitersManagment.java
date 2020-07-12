/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.managment;

import diss_2.restauracia.objects.Activity;
import diss_2.restauracia.objects.CustomerGroup;
import diss_2.restauracia.objects.Waiter;
import event_sim_core.statictics.AverageSizeStat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MarekPC
 */
public class WaitersManagment {

    private List<Waiter> waiters;
    private int freeWaiters;
    public AverageSizeStat freeWaitersStat;

    public WaitersManagment(int numberOfWaiters) {
        this.waiters = new ArrayList<>(numberOfWaiters);
        for (int i = 0; i < numberOfWaiters; i++) {
            this.waiters.add(new Waiter(i));
        }
        this.freeWaiters = numberOfWaiters;
        this.freeWaitersStat = new AverageSizeStat();
    }

    /**
     * Vráti volneho casnika , null ak neexistuje
     *
     * @return
     */
    public boolean isAnyWaiterFree() {
        return freeWaiters != 0;

//        for (Waiter waiter : waiters) {
//            if(!waiter.isWorking()){
//                return true;
//            }
//            
//        }
//        return false;
    }

    public Waiter getFreeWaiter() {

        for (Waiter waiter : waiters) {
            if (!waiter.isWorking()) {
                return waiter;
            }
        }
        return null;
    }

    /**
     * Vráti casnika ktory pracoval najmenej, ak ziadny nie je volny vrati null
     *
     * @return
     */
    public Waiter getLeastWorkedWaiter() {

        double workedTime = Double.MAX_VALUE;
        Waiter retur = null;
        for (Waiter waiter : waiters) {
            if (!waiter.isWorking()) {
                if (workedTime > waiter.getTotalWorkedTime()) {
                    workedTime = waiter.getTotalWorkedTime();
                    retur = waiter;
                }

            }

        }
        return retur;
    }

    public int getNumberOfFreeWaiters() {
        return freeWaiters;
//        int count = 0;
//        for (Waiter waiter : waiters) {
//            if(!waiter.isWorking()){
//                count ++;
//            }
//            
//        }
//        return count;
    }

    public int getNumberOfWorkingWaiters() {
        return this.waiters.size() - freeWaiters;

//        int count = 0;
//        for (Waiter waiter : waiters) {
//            if(waiter.isWorking()){
//                count++;
//            }
//        }
//        return count;
    }

    public void setWorking(Waiter wait, double time, Activity act, CustomerGroup group) {
        freeWaitersStat.add(freeWaiters, time);
        freeWaiters--;
        wait.setWorking(true, act, group);

    }

    public void setNotWorking(Waiter wait, double time) {
        freeWaitersStat.add(freeWaiters, time);
        freeWaiters++;
        wait.setWorking(false, null, null);

    }

    public List<Waiter> getList() {
        return this.waiters;
    }

}
