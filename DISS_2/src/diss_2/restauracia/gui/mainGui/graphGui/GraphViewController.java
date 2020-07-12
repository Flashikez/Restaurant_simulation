/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.gui.mainGui.graphGui;

import diss_2.restauracia.Restauracia_Core;
import event_sim_core.Sim_GUI;
import event_sim_core.Simulation_Core;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author MarekPC
 */
public class GraphViewController implements Initializable, Sim_GUI {

    @FXML
    private VBox tabGraphs;
    @FXML
    private CheckBox cbCool;
    @FXML
    private ChoiceBox<String> cbChoose;
    @FXML
    private TextField tfReplications;
    @FXML
    private Button btnStart1;
    @FXML
    private Button btnStop1;
    @FXML
    private Label lbText, lbPpl;

    private int numberOfCooks = 15;
    private int numberOfWaiters = 4;
    private boolean cooksVariant = true;
    private ChartSet currentChart;
    private ThreadPoolExecutor threadPool;
    private List<Future<?>> futures;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        cbChoose.setItems(FXCollections.observableArrayList("Závislosť čakania od počtu kuchárov", "Závislosť čakania od počtu čašníkov"));
        lbPpl.setText("Počet kuchárov: " + numberOfCooks + "\n" + "Počet čašníkov: " + numberOfWaiters);

        cbChoose.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue ov, String value, String new_value) {

                        if (new_value.compareTo("Závislosť čakania od počtu kuchárov") == 0) {
                            cooksVariant = true;
                        } else {
                            cooksVariant = false;
                        }
                    }
                });
        cbChoose.getSelectionModel().select("Závislosť čakania od počtu kuchárov");
        btnStart1.setOnAction(e -> {
            this.futures = new ArrayList<>();
            if (currentChart != null) {
                tabGraphs.getChildren().remove(currentChart.getChart());
            }
            if (cooksVariant) {
                currentChart = new ChartSet("Závislosť času čakania od počtu kuchárov", "Počet kuchárov", "Priemerný čas čakania zákazníka", true);

                tabGraphs.getChildren().add(currentChart.getChart());
                lbText.setText("Prebieha výpočet (5 vlákna)");

                Thread th = new Thread(() -> {
                    threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5,
                            new ThreadFactory() {
                        public Thread newThread(Runnable r) {
                            Thread t = Executors.defaultThreadFactory().newThread(r);
                            t.setDaemon(true);
                            return t;
                        }
                    });

                    for (int i = 10; i <= 20; i++) {
                        Restauracia_Core core = new Restauracia_Core(9 * 60 * 60, Integer.parseInt(tfReplications.getText()), numberOfWaiters, i, -1, cbCool.isSelected());
                        core.registerGUI(this);
                        Future t = threadPool.submit(()
                                -> {
                            core.runSimulation();
                        });
                        futures.add(t);
                    }
                });
                th.setDaemon(true);
                btnStart1.setDisable(true);
                btnStop1.setDisable(false);
                th.start();

            } else {
                currentChart = new ChartSet("Závislosť času čakania od počtu čašníkov", "Počet čašníkov", "Priemerný čas čakania zákazníka", true);

                tabGraphs.getChildren().add(currentChart.getChart());

//                tabGraphs.getChildren().add(currentChart.getChart());
                lbText.setText("Prebieha výpočet (5 vlákien)");
                Thread th = new Thread(() -> {
                    threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4,
                            new ThreadFactory() {
                        public Thread newThread(Runnable r) {
                            Thread t = Executors.defaultThreadFactory().newThread(r);
                            t.setDaemon(true);
                            return t;
                        }
                    });

                    for (int i = 1; i <= 5; i++) {
                        Restauracia_Core core = new Restauracia_Core(9 * 60 * 60, Integer.parseInt(tfReplications.getText()), i, numberOfCooks, -1, cbCool.isSelected());
                        core.registerGUI(this);
                        Future t = threadPool.submit(()
                                -> {
                            core.runSimulation();
                        });
                        futures.add(t);
                    }

                });

                btnStart1.setDisable(true);
                btnStop1.setDisable(false);
                th.setDaemon(true);
                th.start();
            }
        });
        btnStop1.setOnAction(e -> {
            if (threadPool != null) {
                boolean allDone = true;
                for (Future<?> future : futures) {
                    allDone &= future.isDone();

                }
                if (!allDone) {
                    lbText.setText("Dokončím bežiace vlákna a zastavím");
                    btnStop1.setDisable(true);
                    threadPool.shutdownNow();
                } else {
                    threadPool = null;
                    lbText.setText("Zastavené");
                    btnStart1.setDisable(false);
                    btnStop1.setDisable(true);
                }

            }

        });
//        threadPool.
    }

    @Override
    public void refresh(Simulation_Core core) {

    }

    @Override
    public void afterSimulation(Simulation_Core core) {
        Restauracia_Core myCore = (Restauracia_Core) core;

        if (cooksVariant) {
            Platform.runLater(() -> {
//                System.out.println(myCore.getNumberOfCooks() + "\t" + myCore.statistics.customersWaitingTimeoverReplications.average());
                this.currentChart.addData(myCore.getNumberOfCooks(), myCore.statistics.customersWaitingTimeoverReplications.average());
                if (threadPool.isShutdown()) {
                    lbText.setText("Zastavené");
                    btnStart1.setDisable(false);
                    btnStop1.setDisable(true);
                }
                if (threadPool.getCompletedTaskCount() == 10) {
                    lbText.setText("Hotovo");
                    btnStart1.setDisable(false);
                    btnStop1.setDisable(true);
                }

            });
        } else {
            Platform.runLater(() -> {
//                System.out.println(myCore.getNumberOfCooks() + "\t" + myCore.statistics.customersWaitingTimeoverReplications.average());
                this.currentChart.addData(myCore.getNumberOfWaiters(), myCore.statistics.customersWaitingTimeoverReplications.average());
                if (threadPool.isShutdown()) {
                    lbText.setText("Zastavené");
                    btnStart1.setDisable(false);
                    btnStop1.setDisable(true);
                }
                if (threadPool.getCompletedTaskCount() == 5) {
                    lbText.setText("Hotovo");
                    btnStart1.setDisable(false);
                    btnStop1.setDisable(true);
                }

            });

        }

    }

    @Override
    public void refreshOnlyTime(Simulation_Core core) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
