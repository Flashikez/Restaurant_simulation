/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.objects;

/**
 * Zákaznícka skupina o počte grupSize, drží si všetky sledované časy
 * @author MarekPC
 */
public class CustomerGroup {

    private int id;
    private int groupSize;
    private double arrivalTime, exitTime;
    private double startOfOrderTime, endOfOrderTime;
    private double foodPreparedTime, foodDeliveredTime;
    private double foodEatenTime;
    private double startOfPayingTime;
    private Table table;
    private Food_Order order;

    public Food_Order getOrder() {
        return order;
    }

    public void setOrder(Food_Order order) {
        this.order = order;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public double getStartOfPayingTime() {
        return startOfPayingTime;
    }

    public void setStartOfPayingTime(double startOfPayingTime) {
        this.startOfPayingTime = startOfPayingTime;
    }

    public double getFoodPreparedTime() {
        return foodPreparedTime;
    }

    public void setFoodPreparedTime(double foodPreparedTime) {
        this.foodPreparedTime = foodPreparedTime;
    }

    public double getFoodDeliveredTime() {
        return foodDeliveredTime;
    }

    public void setFoodDeliveredTime(double foodDeliveredTime) {
        this.foodDeliveredTime = foodDeliveredTime;
    }

    public double getFoodEatenTime() {
        return foodEatenTime;
    }

    public void setFoodEatenTime(double foodEatenTime) {
        this.foodEatenTime = foodEatenTime;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public double getStartOfOrderTime() {
        return startOfOrderTime;
    }

    public void setStartOfOrderTime(double startOfOrderTime) {
        this.startOfOrderTime = startOfOrderTime;
    }

    public double getEndOfOrderTime() {
        return endOfOrderTime;
    }

    public void setEndOfOrderTime(double endOfOrderTime) {
        this.endOfOrderTime = endOfOrderTime;
    }

    public int size() {
        return groupSize;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getExitTime() {
        return exitTime;
    }

    public void setExitTime(double exitTime) {
        this.exitTime = exitTime;
    }

    public CustomerGroup(int groupSize, int id) {
        this.groupSize = groupSize;
        this.id = id;
        this.arrivalTime = this.endOfOrderTime = this.exitTime = this.foodPreparedTime = this.foodEatenTime = this.foodDeliveredTime = this.startOfOrderTime = this.startOfPayingTime = -1;
    }

    public String getActivity() {
        if (arrivalTime != -1) {
            if (startOfOrderTime == -1) {
                return "Čaká na objednávku";
            }
            if (endOfOrderTime == -1) {
                return "Prebieha objednávka";
            }
            if (foodDeliveredTime == -1) {
                return "Čaká na jedlo";
            }
            if (foodEatenTime == -1) {
                return "Jedenie";
            }
            if (startOfPayingTime == -1) {
                return "Čaká na zapltenie";
            }
            if (exitTime == -1) {
                return "Platí";
            }

        }
        return "";

    }

    public int getId() {
        return this.id;
    }

}
