/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Objednávka, ktorá zastrešuje istý počet jedál
 * @author MarekPC
 */
public class Food_Order {

    private List<Food> orderedFood;
    private CustomerGroup group;

    private Food_Order(List<Food> order,CustomerGroup gro) {
        orderedFood = order;
        this.group = gro;
    }

    public List<Food> getOrderedFood() {
        return orderedFood;
    }

    public void setOrderedFood(List<Food> orderedFood) {
        this.orderedFood = orderedFood;
    }

    public CustomerGroup getGroup() {
        return group;
    }

    public void setGroup(CustomerGroup group) {
        this.group = group;
    }

    
    public boolean isFinished() {
        for (Food food : orderedFood) {
            if (!food.isFinished()) {
                return false;
            }

        }
        return true;
    }
    public int size(){
        return orderedFood.size() ;
    }
    public Food getUnfinishedNotPreparingFood() {
        for (Food food : orderedFood) {
            if (!food.isFinished() && !food.isCooking()) {
                return food;
            }

        }
        return null;
    }
    public List<Food> list(){
        return orderedFood;
    }

    public Food get(int i) {
        return orderedFood.get(i);
    }

    public static class CREATOR {

        public static Food_Order constructFromTimeList(List<Double> times,CustomerGroup group) {
            List<Food> order = new ArrayList<>();
            for (Double time : times) {
                order.add(new Food(time));
            }
            return new Food_Order(order,group);

        }
    }

}
