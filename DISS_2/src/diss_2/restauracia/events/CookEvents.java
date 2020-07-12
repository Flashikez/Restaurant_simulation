/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.events;

import diss_2.restauracia.Restauracia_Core;
import diss_2.restauracia.objects.Cook;
import diss_2.restauracia.objects.CustomerGroup;
import diss_2.restauracia.objects.Food;
import event_sim_core.event.Event;

/**
 * Abstraktná trieda pre eventy kuchárov 
 * @author MarekPC
 */
public abstract class CookEvents extends Event {

    protected Restauracia_Core myCore;
    protected CustomerGroup customerGroup;
    protected Cook cook;
    protected Food assignedFood;

    public CookEvents(Restauracia_Core myCore, double time, CustomerGroup customerGroup, Cook cook, Food assignedFood) {
        super(myCore, time);
        this.myCore = myCore;
        this.customerGroup = customerGroup;
        this.cook = cook;
        this.assignedFood = assignedFood;
    }

    @Override
    public abstract void execute();

}
