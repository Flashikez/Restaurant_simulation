/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event_sim_core.generators;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exponenciálny generátor
 * @author MarekPC
 */
public class Exponential_RNG extends Generator<Double> {

    private double lambda;

    public Exponential_RNG(double lambda) {
        super();
        this.lambda = lambda;
    }

    public Exponential_RNG(double lambda, int seed) {
        super(seed);
        this.lambda = lambda;
    }

    @Override
    public Double getSample() {
        return Math.log(1.0 - rand.nextDouble()) / (-lambda);
    }

    public void test() {
        PrintWriter writer;
        try {
            writer = new PrintWriter("the-file-name.txt", "UTF-8");

            for (int i = 0; i < 1000000; i++) {
                writer.println(this.getSample());
            }
            System.out.println("TEST DONE");
            writer.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Exponential_RNG.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Exponential_RNG.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String toString() {
        return "Exponential  generator lambda = "+lambda+"\n";
    }

}
