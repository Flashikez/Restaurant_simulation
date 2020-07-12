/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia;

import diss_2.restauracia.managment.TablesManagment;
import diss_2.restauracia.managment.WaitersManagment;
import diss_2.restauracia.managment.CooksManagment;
import diss_2.restauracia.events.customerEvents.ArrivalEvent;
import diss_2.restauracia.objects.Cook;
import diss_2.restauracia.objects.CustomerGroup;
import diss_2.restauracia.objects.Food;
import diss_2.restauracia.objects.Waiter;
import event_sim_core.Simulation_Core;
import event_sim_core.event.Event;
import event_sim_core.generators.Cont_Uniform_RNG;
import event_sim_core.generators.Deterministic_RNG;
import event_sim_core.generators.Discrete_Uniform_RNG;
import event_sim_core.generators.Empirical_RNG;
import event_sim_core.generators.Exponential_RNG;
import event_sim_core.generators.Generator;
import event_sim_core.generators.Trianglular_RNG;
import event_sim_core.statictics.Basic_Statistics;
import event_sim_core.statictics.StatQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import javafx.util.Pair;

/**
 * Hlavná trieda simulácie reštaurácie, obsahuje všetky rady zákazníkov a jedál, štatistiky, a inštancie
 * manažérov zdrojov
 * @author MarekPC
 */
public class Restauracia_Core extends Simulation_Core {

    public Generators generators;
    public TablesManagment tablesManagment;
    public WaitersManagment waitersManagment;
    public CooksManagment cooksManagment;
    private StatQueue<CustomerGroup> waitingForOrderQueue;
    private StatQueue<Food> waitingForCookQueue;
    private StatQueue<CustomerGroup> waitingForPayQueue;
    private StatQueue<CustomerGroup> waitingForFoodDeliverQueue;
    public Statistics statistics;
    private int numberOfCooks, numberOfWaiters;
    public int customerIdCounter;

    public Restauracia_Core(double maxTime, int iterations, int numberOfWaiters, int numberOfCooks, long seed, boolean cooling) {
        super(maxTime, iterations, seed, cooling);
        statistics = new Statistics();
        generators = new Generators();
        this.numberOfCooks = numberOfCooks;
        this.numberOfWaiters = numberOfWaiters;


    }

    public void addTo_WaitingForOrderQueue(CustomerGroup group, double time) {
        waitingForOrderQueue.addToQueue(group, time);
    }

    public int getNumberOfCooks() {
        return numberOfCooks;
    }

    public int getNumberOfWaiters() {
        return numberOfWaiters;
    }

    public CustomerGroup removeFrom_WaitingForOrderQueue(double time) {
        return waitingForOrderQueue.removeFromQueue(time);
    }

    public Queue<CustomerGroup> getOrderQueue() {
        return waitingForOrderQueue;
    }

    public Queue<CustomerGroup> getPayQueue() {
        return waitingForPayQueue;
    }

    public Queue<CustomerGroup> getDeliveryQueue() {
        return waitingForFoodDeliverQueue;
    }

    public Queue<Food> getFoodQueue() {
        return waitingForCookQueue;
    }

    public boolean isEmpty_WaitingForOrderQueue() {
        return waitingForOrderQueue.isEmpty();
    }

    public int size_WaitingForOrderQueue() {
        return waitingForOrderQueue.size();
    }

    public void addTo_WaitingForCookQueue(Food food, double time) {
        waitingForCookQueue.addToQueue(food, time);

    }

    public Food peek_WaitingForCookQueue() {
        return waitingForCookQueue.peek();
    }

    public Food removeFrom_WaitingForCookQueue(double time) {
        return waitingForCookQueue.removeFromQueue(time);
    }

    public int size_WaitingForCookQueue() {
        return waitingForCookQueue.size();
    }

    public boolean isEmpty_WaitingForCookQueue() {
        return waitingForCookQueue.isEmpty();
    }

    public void addTo_WaitingForPayQueue(CustomerGroup group, double time) {
        waitingForPayQueue.addToQueue(group, time);

    }

    public CustomerGroup removeFrom_WaitingForPayQueue(double time) {
        return waitingForPayQueue.removeFromQueue(time);
    }

    public boolean isEmpty_WaitingForPayQueue() {
        return waitingForPayQueue.isEmpty();
    }

    public int size_WaitingForPayQueue() {
        return waitingForPayQueue.size();
    }

    public void addTo_WaitingForFoodDeliveryQueue(CustomerGroup group, double time) {
        waitingForFoodDeliverQueue.addToQueue(group, time);

    }

    public CustomerGroup removeFrom_WaitingForFoodDeliverQueue(double time) {
        return waitingForFoodDeliverQueue.removeFromQueue(time);
    }

    public boolean isEmpty_WaitingForFoodDeliverQueue() {
        return waitingForFoodDeliverQueue.isEmpty();
    }

    public int size_WaitingForFoodDeliveryQueue() {
        return waitingForFoodDeliverQueue.size();
    }

    public void dispatchCustomer(CustomerGroup group, boolean successFinish) {
        if (successFinish) {
            double waitingTime = group.getStartOfOrderTime() - group.getArrivalTime();
            waitingTime += group.getFoodDeliveredTime() - group.getEndOfOrderTime();
            waitingTime += group.getStartOfPayingTime() - group.getFoodEatenTime();

            for (int i = 0; i < group.getGroupSize(); i++) {
                this.statistics.totalCustomerWaitingTime.add(waitingTime);
            }

        }
    }

    public String timeString() {
        int totalSecs = (int) super.getCurrentTime();
        int hours = totalSecs / 3600 + 13;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Metoda na uvolnenie casnika, ktora skontroluje cakajucich zakaznikov
     *
     * @param waiter
     */
    @Override
    public void afterEvent() {
        if (!replicationsMode()) {
            if (super.getSystemEventPlanTime() <= 5) {
                refreshGUI();
            } else {
                refreshGUItime();
            }
        }
//            if (replicationsDone() % 1000 == 0) {
//                refreshGUI();
//            }

    }

    @Override
    public void beforeReplication() {
        statistics.reset();
        tablesManagment = new TablesManagment();
        waitingForOrderQueue = new StatQueue<>();
        waitingForCookQueue = new StatQueue<>();
        waitingForPayQueue = new StatQueue<>();
        waitingForFoodDeliverQueue = new StatQueue<>();
        waitersManagment = new WaitersManagment(numberOfWaiters);
        cooksManagment = new CooksManagment(numberOfCooks);
        for (int j = 1; j <= 6; j++) {

            ArrivalEvent ariv1 = new ArrivalEvent(this, this.generators.generateArrivalTime(j), new CustomerGroup(j, j));
            this.addEvent(ariv1);
        }
        this.customerIdCounter = 6;
    }

    @Override
    public void afterReplication() {
        statistics.replicationDone();
        double endTime = getCurrentTime();
        double _2tables = tablesManagment._2tablesFree.average(endTime);
        double _4tables = tablesManagment._4tablesFree.average(endTime);
        double _6tables = tablesManagment._6tablesFree.average(endTime);
        double freeCooks = cooksManagment.freeCooksStat.average(endTime);
        double freeWaiters = waitersManagment.freeWaitersStat.average(endTime);
        // TODO cooks, watiers free time 
        statistics.averageLengthReplicationDone(_2tables, _4tables, _6tables, freeCooks, freeWaiters);
        double waitersFreeTime = 0;
        double sumW = 0;
        for (Waiter waiter : waitersManagment.getList()) {
            sumW += 1 - (waiter.getTotalWorkedTime() / endTime);

        }
        sumW = sumW / waitersManagment.getList().size();
        waitersFreeTime =  sumW;
        double cooksFreeTime = 0;
        double sumC = 0;
        for (Cook cook : cooksManagment.getList()) {
            sumC += 1 -(cook.getTotalWorkedTime() / endTime);
        }
        sumC = sumC / cooksManagment.getList().size();
        
        cooksFreeTime =  sumC;

        statistics.averageFreeTimeReplicationDone(cooksFreeTime, waitersFreeTime);

        if (replicationsMode()) {
//            if (replicationsDone() % 100 == 0) {
            refreshGUI();
//            }
        }

    }

    @Override
    public void beforeSimulation() {

    }

    @Override
    public void afterSimulation() {
        refreshGUI();
        afterSimulationGUInotify();

    }
/**
 * Trieda obsahujúca generátory pre potreby simulácie reštaurácie zo zadania
 */
    public class Generators {

        private Exponential_RNG arrivalGenerator_one;
        private Exponential_RNG arrivalGenerator_two;
        private Exponential_RNG arrivalGenerator_three;
        private Exponential_RNG arrivalGenerator_four;
        private Exponential_RNG arrivalGenerator_five;
        private Exponential_RNG arrivalGenerator_six;

        private Cont_Uniform_RNG timeToOrderGenerator;
        private Trianglular_RNG eatingTimeGenerator;
        private Cont_Uniform_RNG payingTimeGenerator;
        private Cont_Uniform_RNG getFoodFromKitchenGenerator;
        private Empirical_RNG foodPrepareTimeGenerator;

        public Generators() {

            arrivalGenerator_six = new Exponential_RNG(1 / 900.0, generateSeed());
            arrivalGenerator_five = new Exponential_RNG(1 / 1200.0, generateSeed());
            arrivalGenerator_four = new Exponential_RNG(1 / 720.0, generateSeed());
            arrivalGenerator_three = new Exponential_RNG(1 / 600.0, generateSeed());
            arrivalGenerator_two = new Exponential_RNG(1 / 450.0, generateSeed());
            arrivalGenerator_one = new Exponential_RNG(1 / 360.0, generateSeed());
            timeToOrderGenerator = new Cont_Uniform_RNG(45.0, 120.0, generateSeed());
            eatingTimeGenerator = new Trianglular_RNG(180.0, 1800.0, 900.0, generateSeed());
            payingTimeGenerator = new Cont_Uniform_RNG(43.0, 97.0, generateSeed());
            getFoodFromKitchenGenerator = new Cont_Uniform_RNG(23.0, 80.0, generateSeed());
            Discrete_Uniform_RNG ceaser = new Discrete_Uniform_RNG(380, 440, generateSeed());
            Discrete_Uniform_RNG penneFirst = new Discrete_Uniform_RNG(185, 330, generateSeed());
            Discrete_Uniform_RNG penneSecond = new Discrete_Uniform_RNG(331, 630, generateSeed());
            Discrete_Uniform_RNG penneThird = new Discrete_Uniform_RNG(631, 930, generateSeed());
            Empirical_RNG penneEmpiric = new Empirical_RNG(generateSeed(), constPair(penneFirst, 0.15), constPair(penneSecond, 0.5), constPair(penneThird, 0.35));
            Discrete_Uniform_RNG spaghettyFirst = new Discrete_Uniform_RNG(290, 356, generateSeed());
            Discrete_Uniform_RNG spaghettySecond = new Discrete_Uniform_RNG(357, 540, generateSeed());
            Discrete_Uniform_RNG spaghettyThird = new Discrete_Uniform_RNG(541, 600, generateSeed());
            Empirical_RNG spaghettyEmpiric = new Empirical_RNG(generateSeed(), constPair(spaghettyFirst, 0.2), constPair(spaghettySecond, 0.43), constPair(spaghettyThird, 0.37));
            Deterministic_RNG salad = new Deterministic_RNG(180.0);
            foodPrepareTimeGenerator = new Empirical_RNG(generateSeed(), constPair(ceaser, 0.3), constPair(penneEmpiric, 0.35), constPair(spaghettyEmpiric, 0.2), constPair(salad, 0.15));
        }

        private Pair<Generator, Double> constPair(Generator gen, double prob) {
            return new Pair<>(gen, prob);
        }

        public double generateArrivalTime(int groupSize) {
            switch (groupSize) {
                case 1:
                    return arrivalGenerator_one.getSample();
                case 2:
                    return arrivalGenerator_two.getSample();
                case 3:
                    return arrivalGenerator_three.getSample();
                case 4:
                    return arrivalGenerator_four.getSample();
                case 5:
                    return arrivalGenerator_five.getSample();
                case 6:
                    return arrivalGenerator_six.getSample();
                default:
                    return 0;
            }
        }

        public double generateTimeToOrderTime() {
            return timeToOrderGenerator.getSample();
//            return 0.0;
        }

        public double generateEatingTime(int groupSize) {
            double max = 0.0;

            for (int i = 0; i < groupSize; i++) {
                double generated = (double) eatingTimeGenerator.getSample();
                if (generated > max) {
                    max = generated;
                }

            }
            return max;

        }

        public double generatePayingTime() {
            return payingTimeGenerator.getSample();

        }

        public double generateFoodFromKitchenTime() {
            return getFoodFromKitchenGenerator.getSample();
//            return 0.0;
        }

        public List<Double> generateFoodPreparationTime(int groupSize) {

            List<Double> list = new ArrayList<>(groupSize);
            for (int i = 0; i < groupSize; i++) {
                double generated = (double) foodPrepareTimeGenerator.getSample();
                list.add(generated);
//                list.add(0.0);

            }
            return list;
        }

    }

    public class Servers {

    }
}
