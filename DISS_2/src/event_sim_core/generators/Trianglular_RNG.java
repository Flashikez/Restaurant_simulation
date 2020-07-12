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
 * Generátor trojuholníkového rozdelenia pravdepodobnosti
 * @author MarekPC
 */
public class Trianglular_RNG extends Generator<Double> {

    double a, b, c, F;

    public Trianglular_RNG(double a, double b, double c) {
        super();
        this.a = a;
        this.b = b;
        this.c = c;
        this.F = (c - a) / (b - a);

    }

    public Trianglular_RNG(double a, double b, double c, int seed) {
        super(seed);
        this.a = a;
        this.b = b;
        this.c = c;
        this.F = (c - a) / (b - a);
    }

    @Override
    public Double getSample() {
        double rand = super.rand.nextDouble();
        if (rand < F) {
            return a + Math.sqrt(rand * (b - a) * (c - a));
        } else {
            return b - Math.sqrt((1 - rand) * (b - a) * (b - c));
        }
    }

    public void test() {
        PrintWriter writer;
        try {
            writer = new PrintWriter("empiricTest.txt", "UTF-8");

            for (int i = 0; i < 10000000; i++) {
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
        return "Triangular genertor lowerBound = " + a + " upperBound =" + b + " modus = " + c + "\n";

    }

}
