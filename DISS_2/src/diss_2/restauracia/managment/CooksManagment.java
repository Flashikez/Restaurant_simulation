/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.managment;

import diss_2.restauracia.objects.Cook;
import event_sim_core.statictics.AverageSizeStat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MarekPC
 */
public class CooksManagment {

    private List<Cook> cooks;
    private int freeCooks;
    public AverageSizeStat freeCooksStat;

    public CooksManagment(int numberOfCooks) {
        this.cooks = new ArrayList<>(numberOfCooks);
        for (int i = 0; i < numberOfCooks; i++) {
            this.cooks.add(new Cook(i));
        }
        this.freeCooks = numberOfCooks;
        this.freeCooksStat = new AverageSizeStat();
    }

    /**
     * Vráti volneho casnika , null ak neexistuje
     *
     * @return
     */
    /**
     * Vráti casnika ktory pracoval najmenej, ak ziadny nie je volny vrati null
     *
     * @return
     */
    public Cook getLeastWorkedCook() {

        double workedTime = Double.MAX_VALUE;
        Cook retur = null;
        for (Cook waiter : cooks) {
            if (!waiter.isWorking()) {
                if (workedTime > waiter.getWorkingTime()) {
                    workedTime = waiter.getWorkingTime();
                    retur = waiter;
                }
            }
        }
        return retur;
    }

    public int numberOfWorkingCooks() {
        return cooks.size() - numberOfFreeCooks();

//        int count = 0;
//        for (Cook waiter : cooks) {
//            if (waiter.isWorking()) {
//                count++;
//            }
//        }
//        return count;
    }

    public int numberOfFreeCooks() {
        return this.freeCooks;

//       int count = 0;
//        for (Cook waiter : cooks) {
//            if (!waiter.isWorking()) {
//                count++;
//            }
//        }
//        return count;
    }

    public void setWorking(Cook wait, double time) {

        freeCooksStat.add(freeCooks, time);
        freeCooks--;
        wait.setWorking(true);

    }

    public void setNotWorking(Cook wait, double time) {
        freeCooksStat.add(freeCooks, time);
        freeCooks++;
        wait.setWorking(false);
    }
    
    public List<Cook> getList(){
        return cooks;
    }
}
