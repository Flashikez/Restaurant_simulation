/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.objects;

/**
 * Objednané jedlo
 * @author MarekPC
 */
public class Food {

    double timeToMake;
    boolean finished;
    boolean cooking;
    private Food_Order order;

    public int getCustomerId() {
        return order.getGroup().getId();
    }

    public Food(double timeToMake) {
        this.timeToMake = timeToMake;
        finished = cooking = false;

    }

    public Food_Order getOrder() {
        return order;
    }

    public void setOrder(Food_Order order) {
        this.order = order;
    }

    public boolean isCooking() {
        return cooking;
    }

    public void setCooking(boolean cooking) {
        this.cooking = cooking;
    }

    public double getTimeToMake() {
        return timeToMake;
    }

    public void setTimeToMake(double timeToMake) {
        this.timeToMake = timeToMake;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

}
