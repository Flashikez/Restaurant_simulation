/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event_sim_core.event;

import event_sim_core.Simulation_Core;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Systémový event používaný na spomaľovanie simulácie a na simuláciu spojitosti simulácie
 * @author MarekPC
 */
public class SystemEvent extends Event {

    public SystemEvent(Simulation_Core core, double time) {
        super(core, time);
    }

    @Override
    public void execute() {
        core.addEvent(new SystemEvent(core, time + core.getSystemEventPlanTime()));
        try {
            Thread.sleep((long) core.getSystemEventSleepTime());
        } catch (InterruptedException ex) {
            Logger.getLogger(SystemEvent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
