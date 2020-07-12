/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event_sim_core.generators;

/**
 * Diskrétny rovnomerný generátor
 * @author MarekPC
 */
public class Discrete_Uniform_RNG extends Generator<Double> {

    int lowerBound, upperBound;

    @Override
    public Double getSample() {
        return (double) rand.nextInt((upperBound - lowerBound) + 1) + lowerBound;

    }

    public Discrete_Uniform_RNG(int lowerBound, int upperBound, int seed) {
        super(seed);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public Discrete_Uniform_RNG(int lowerBound, int upperBound) {
        super();
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public String toString() {
        return "Discrete uniform generator loweBound = " + lowerBound + " upperBound =" + upperBound + "\n";
    }

}
