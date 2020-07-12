/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event_sim_core.generators;

/**
 * "Generátor", ktorý vždy vráti rovnakú hodnotu, slúži pre všeobecnosť empirického generátora
 * @author MarekPC
 */
public class Deterministic_RNG extends Generator<Double> {
    
    double num;
    public Deterministic_RNG(double nump) {
        this.num = nump;
    }

    @Override
    public Double getSample() {
        return num;
    }

    @Override
    public String toString() {
        return "Deterministic generator value = "+num+"\n";
    }

}
