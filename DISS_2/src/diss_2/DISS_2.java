/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2;

import diss_2.restauracia.Restauracia_Core;
import diss_2.restauracia.events.customerEvents.ArrivalEvent;
import diss_2.restauracia.gui.mainGui.MainViewController;
import diss_2.restauracia.objects.CustomerGroup;
import event_sim_core.generators.Deterministic_RNG;
import event_sim_core.generators.Empirical_RNG;
import event_sim_core.generators.Generator;
import event_sim_core.generators.Trianglular_RNG;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 *
 * @author MarekPC
 */
public class DISS_2 extends Application {
//public class DISS_2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        launch(args);
//        PrintWriter writer = new PrintWriter("vysledkyChladenie.txt", "UTF-8");
//
//        for (int cooks = 1; cooks <= 20; cooks++) {
//            for (int waiters = 1; waiters <= 10; waiters++) {
//                System.out.println("cooks: " + cooks + "   waiter: " + waiters);
//                Restauracia_Core core = new Restauracia_Core(9 * 60 * 60, 10000, waiters, cooks, -1, true);
//                core.runSimulation();
//                String outString = "" + waiters + ";" + cooks + ";" + core.statistics.customersWaitingTimeoverReplications.averageAndInterval() + ";" + core.statistics.percentLeftOverReplications.averageAndIntervalPercent() + ";"
//                        + "" + core.statistics.freeWaitersOverReplications.average() + ";" + core.statistics.freeCooksOverReplications.average() + ";"
//                        + "" + core.statistics.waiterFreeTimeOverReplications.average() * 100 + ";" + core.statistics.cooksFreeTimeOverReplications.average() * 100 + ";" + core.statistics._2tablesFreeoverReplications.average() + ";"
//                        + "" + core.statistics._4tablesFreeoverReplications.average() + ";" + core.statistics._4tablesFreeoverReplications.average() + ";";
//                writer.println(outString);
//
//            }
//        }
//        writer.close();

//        double sum = 0;
//        double in = 0;
//        double out = 0;
//        double left = 0;
//        double totalIn = 0;
//        double precento = 0;
//        double s = 0;
//        int repl = 10000;
////        for (int i = 0; i < repl; i++) {
//
////            long seed = i;
////            System.out.println(i);
//        Restauracia_Core core = new Restauracia_Core(9 * 60 * 60, 10000, 4, 15, -1, false);
//        core.runSimulation();
//            sum += core.statistics.totalCustomerWaitingTime.average();
//            in += core.statistics.totalCustomersIn.sum();
//            out += core.statistics.totalCustomersOut.sum();
////            left += core.statistics.totalCustomersLeft.sum();
////            
////
////            
////            totalIn += core.statistics.totalCustomersTotalIn.sum();
////            precento += core.statistics.totalCustomersLeft.sum()/core.statistics.totalCustomersTotalIn.sum();
//
////        }
////        
//        System.out.println(("Priemerná doba čakania: " + (sum / repl)));
////        System.out.println("Priemerný počet zákazníkov čo prišli   " + totalIn / repl);
//        System.out.println("Priemerné % čo si nesadlo  " + core.statistics.percentLeftOverReplications.averageAndIntervalPercent());
//////        System.out.println("Priemerný počet zákazníkov čo si sadli   " + in / repl);
//////        System.out.println("Priemerný počet zákazníkov čo ostali v systéme na konci otváracej doby   " + (in - out) / repl);
//////        System.out.println("Priemerný počet zákazníkov čo si nesadli(odišli hneď)   " + left / repl);
//        System.out.println(("Priemerná doba čakania: " + core.statistics.customersWaitingTimeoverReplications.averageAndInterval()));
//        System.out.println("Priemerný počet voľných stolov veľkosti 2: " + core.statistics._2tablesFreeoverReplications.averageAndInterval());
//        System.out.println("Priemerný počet voľných stolov veľkosti 4: " + core.statistics._4tablesFreeoverReplications.averageAndInterval());
//        System.out.println("Priemerný počet voľných stolov veľkosti 6: " + core.statistics._6tablesFreeoverReplications.averageAndInterval());
//        System.out.println("Priemerný počet voľných kuchárov: " + core.statistics.freeCooksOverReplications.averageAndInterval());
//        System.out.println("Priemerný počet voľných časňíkov: " + core.statistics.freeWaitersOverReplications.averageAndInterval());
////        System.out.println("Priemerný počet zákazníkov čo prišli   " + totalIn / repl);
////        System.out.println("Priemerné % čo si nesadlo  " + (precento/repl)*100);
////        System.out.println("Priemerný počet zákazníkov čo si sadli   " + in / repl);
////        System.out.println("Priemerný počet zákazníkov čo ostali v systéme na konci otváracej doby   " + (in - out) / repl);
////        System.out.println("Priemerný počet zákazníkov čo si nesadli(odišli hneď)   " + left / repl);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(MainViewController.class.getResource("mainView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
