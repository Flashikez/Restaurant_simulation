/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event_sim_core.statictics;

/**
 * Statistika priemernej veľkosti niečoho, napríklad počet volných stolov, čašníkov,...
 * @author MarekPC
 */
public class AverageSizeStat {

    private double lastTime;
    private double weightedSum;

    public AverageSizeStat() {
        this.lastTime = 0;
        this.weightedSum = 0;

    }

    public void add(int currentSize, double time) {
        weightedSum += currentSize * (time - lastTime);
        lastTime = time;
    }

    public double average(double endTime) {
        return weightedSum / endTime;
    }

}
