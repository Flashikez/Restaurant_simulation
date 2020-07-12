/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event_sim_core.generators;

import java.util.Random;

/**
 * Predok generátora
 * @author MarekPC
 * @param <T>
 */
public abstract class Generator<T extends Number> {

    protected Random rand;
    protected final double doubleCompare_Threshold = .0001;

    public abstract T getSample();
    @Override
    public abstract String toString();
    public Generator(){
        this.rand = new Random();
    }
      
    public Generator(int seed) {
        this.rand = new Random(seed);
    }



}
