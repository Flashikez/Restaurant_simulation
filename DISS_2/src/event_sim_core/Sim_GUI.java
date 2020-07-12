/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event_sim_core;

/**
 * Interfce, ktorý si GUI implementuj ak chce byť informovaný o zmene stavu simulácie
 * @author MarekPC
 */
public interface Sim_GUI {
     void refresh(Simulation_Core core);
     void afterSimulation(Simulation_Core core);
     void refreshOnlyTime(Simulation_Core core);
}
