/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event_sim_core.generators;

/**
 * Generátor spojitého rovnomerného rozdelenia
 * @author MarekPC
 */
public class Cont_Uniform_RNG extends Generator<Double> {

    double upperBound, lowerBound;

    public Cont_Uniform_RNG(double upperBound, double lowerBound) {
        super();
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public Cont_Uniform_RNG(double upperBound, double lowerBound, int seed) {
        super(seed);
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    @Override
    public Double getSample() {
        return lowerBound + (upperBound - lowerBound) * rand.nextDouble();
    }

    @Override
    public String toString() {
        return "Continous uniform generator loweBound = " + lowerBound + " upperBound =" + upperBound + "\n";
    }
}
