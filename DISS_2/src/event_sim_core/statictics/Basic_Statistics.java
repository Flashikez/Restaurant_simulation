/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event_sim_core.statictics;

import javafx.util.Pair;

/**
 * Základná štatistika
 * @author MarekPC
 */
public class Basic_Statistics {

    String name;
    double sum;
    double sumSquared;
    int count;

    public Basic_Statistics(String name) {
        this.name = name;
    }

    public Basic_Statistics() {

    }

    public void add(double value) {
        count++;
        sum += value;
        sumSquared += value * value;
    }

    public int count() {
        return this.count;
    }

    public double sum() {
        return this.sum;
    }

    public double sumSquared() {
        return this.sumSquared;
    }

    public double average() {
        return sum / count;
    }

    public void addNoCount(double value) {
        sumSquared += value * value;
        sum += value;
    }

    public void addCount(double value, int cou) {
        sumSquared += value * value;
        sum += value;
        count += cou;

    }

    public void reset() {
        this.sum = 0;
        this.sumSquared = 0;
        this.count = 0;
    }

    public Pair<Double, Double> confidenceInterval() {
        double mean = average();
        double standartDeviation = Math.sqrt((sumSquared / count) - mean * mean);
        double deviationDividedSqrtCount = standartDeviation / Math.sqrt(count);
        double _95normal = 1.645;
        double first = mean - (_95normal * (deviationDividedSqrtCount));
        double second = mean + (_95normal * (deviationDividedSqrtCount));
        return new Pair<>(first, second);
    }

    public String confidenceIntervalString(boolean percent) {
        Pair<Double, Double> p = confidenceInterval();
        if (percent) {
            return " IS (90%): <" + p.getKey() * 100 + " : " + p.getValue() * 100 + ">";
        } else {
            return " IS (90%): <" + p.getKey() + " : " + p.getValue() + ">";
        }
    }

    public String averageAndInterval() {
        return average() + "\t" + confidenceIntervalString(false);
    }

    public String averageAndIntervalPercent() {
        return average() * 100 + "\t" + confidenceIntervalString(true);
    }

}
