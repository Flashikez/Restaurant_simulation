/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.objects;

/**
 * Čašník
 * @author MarekPC
 */
public class Waiter {

    private int id;
    private double totalWorkedTime;
    private boolean working;
    private Activity currentActivity;
    private CustomerGroup currentGroup;

    public Waiter(int id) {
        this.id = id;
        totalWorkedTime = 0;
        working = false;
    }

    public double getTotalWorkedTime() {
        return this.totalWorkedTime;
    }

    public void addToWorkedTime(double val) {
        this.totalWorkedTime += val;
    }

    public void setWorking(boolean working, Activity activity, CustomerGroup group) {
        this.working = working;
        this.currentActivity = activity;
        this.currentGroup = group;
    }

    public String getActivity() {
        if (currentActivity != null) {
            return currentActivity.toString();
        } else {
            return "";
        }
    }

    public int getGroupId() {
        if (currentGroup == null) {
            return -1;
        }
        return currentGroup.getId();

    }

    public boolean isWorking() {
        return working;
    }

    public int getId() {
        return this.id;
    }

}
