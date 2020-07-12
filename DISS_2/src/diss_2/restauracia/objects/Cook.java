/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.objects;

import java.beans.PropertyChangeSupport;

/**
 * Kuchár
 * @author MarekPC
 */
public class Cook {

    private int id;
    private double totalWorkedTime;
    private boolean working;


    public Cook(int id) {
        this.id = id;
        this.totalWorkedTime = 0;
        this.working = false;

    }

    public double getWorkingTime() {
        return this.totalWorkedTime;
    }

    public void addToWorkingTime(double val) {
        this.totalWorkedTime += val;

    }


    public void setWorking(boolean working) {

        this.working = working;

    }

    public boolean isWorking() {
        return working;
    }

    public int getId() {
        return id;
    }

    public double getTotalWorkedTime() {
        return totalWorkedTime;
    }

}
