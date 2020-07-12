/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event_sim_core.generators;

import javafx.util.Pair;

/**
 * Všeobecný empirický generátor, ako vstupy dostáva List párov<Generator,Double> ,kde Generátor je generátor, ktorý sa použije s pravdepodobnosťou Double
 * @author MarekPC
 */
public class Empirical_RNG extends Generator<Number> {

    private Pair<Generator, Double>[] probList;

    public Empirical_RNG(Pair<Generator, Double>... probs) {
        probList = probs;
        double sum = 0;
        for (Pair<Generator, Double> prob : probs) {
            sum += prob.getValue();
        }
        if (Math.abs(sum - 1.0) >= doubleCompare_Threshold) {
            throw new IllegalArgumentException("Probabilities sum is not 1.0");
        }

    }

    public Empirical_RNG(int seed, Pair<Generator, Double>... probs) {
        super(seed);
        probList = probs;
        double sum = 0;
        for (Pair<Generator, Double> prob : probs) {
            sum += prob.getValue();
        }
        if (Math.abs(sum - 1.0) >= doubleCompare_Threshold) {
            throw new IllegalArgumentException("Probabilities sum is not 1.0");
        }

    }

    @Override
    public Number getSample() {
        if (probList.length == 0) {
            throw new IllegalArgumentException("Empirical RNG size 0");
        }
        double generatedProb = super.rand.nextDouble();
        double cumm = 0;
        for (int i = 0; i < probList.length; i++) {

            cumm += probList[i].getValue();
            if (generatedProb <= cumm) {             

                return probList[i].getKey().getSample();
            }

        }
        return Double.MAX_VALUE;

    }

    @Override
    public String toString() {
        String retur = "*************************\nEmpirical generator :";
        for (Pair<Generator, Double> pair : probList) {
            retur +=" Probability: " + pair.getValue() + "   " + pair.getKey();
        }
        retur += "*****************************\n";
        return retur;

    }

}
