/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event_sim_core.statictics;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Queue, ktoré si drží informáciu o priemernej dĺžke 
 * @author MarekPC
 */
public class StatQueue<T> extends LinkedList<T> {

    private double weightedSum;
    private double lastTime;

    public StatQueue() {
        super();

        this.weightedSum = 0;
        this.lastTime = 0;
    }

    public boolean addToQueue(T t, double time) {

        weightedSum += super.size() * (time - lastTime);
//        weightedSum += time * super.size();
        boolean ret = super.add(t);
        lastTime = time;
        return ret;
    }

    public T removeFromQueue(double time) {
        weightedSum += super.size() * (time - lastTime);
        T t = super.poll();
//        if(super.size() == 0){
//            System.out.println("nula");
//        }
//        weightedSum += time * super.size();
        lastTime = time;

        return t;
    }

    public double averageQueueLength(double endTime) {
        return weightedSum / (endTime);

    }

}
