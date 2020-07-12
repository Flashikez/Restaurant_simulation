/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.gui.mainGui.replicationsGui;

import diss_2.restauracia.Restauracia_Core;
import diss_2.restauracia.gui.mainGui.graphGui.ChartSet;
import event_sim_core.Sim_GUI;
import event_sim_core.Simulation_Core;
import java.net.URL;
import java.util.ResourceBundle;
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
public class ReplViewController implements Initializable, Sim_GUI {
    
    @FXML
    private VBox tabRepl;
    @FXML
    private CheckBox cbCool;
    @FXML
    private ChoiceBox<String> cbSeed1;
    @FXML
    private TextField tfSeed1;
    @FXML
    private TextField tfWaiters;
    @FXML
    private TextField tfCooks;
    @FXML
    private TextField tfReplications;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnStop;
    @FXML
    private Label lbReplication, lbTime, lbPercent, lbFreeCooks, lbFreeWaiters, lbFree2, lbFree4, lbFree6, lbFreeTimeWaiters, lbFreeTimeCooks;
    @FXML
    private VBox vBox;
    private ChartSet chart;
    private Restauracia_Core simulation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbSeed1.setItems(FXCollections.observableArrayList("Náhodná", "Fixná"));
        
        cbSeed1.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue ov, String value, String new_value) {
                        
                        if (new_value.compareTo("Fixná") == 0) {
//                            System.out.println(new_value);
                            tfSeed1.setVisible(true);
                        } else {
                            tfSeed1.setVisible(false);
                        }
                        
                    }
                });
        
        cbSeed1.getSelectionModel().select("Náhodná");
        tfWaiters.setText("4");
        tfCooks.setText("15");
        btnStart.setOnAction(e -> {
            toogleControlls(true);
            if (chart != null) {
                tabRepl.getChildren().remove(chart.getChart());
            }
            this.chart = new ChartSet("Priemerná doba čakania zákazníka", "Replikácia", "Priemerný čas čakania zákazníka(sekundy)", false);
            tabRepl.getChildren().add(this.chart.getChart());
            int seed = -1;
            if (tfSeed1.isVisible()) {
                seed = Integer.parseInt(tfSeed1.getText());
            }
            simulation = new Restauracia_Core(9 * 60 * 60, Integer.parseInt(tfReplications.getText()), Integer.parseInt(tfWaiters.getText()), Integer.parseInt(tfCooks.getText()), seed, cbCool.isSelected());
            simulation.registerGUI(this);
            
            Thread t = new Thread(() -> {
                simulation.runSimulation();
                
            });
            t.setDaemon(true);
            t.start();
            
        });
        
        btnStop.setOnAction(e -> {
            simulation.stopSimulation();
            toogleControlls(false);
            
        });
        
    }
    
    @Override
    public void refresh(Simulation_Core core) {
        Restauracia_Core myCore = (Restauracia_Core) core;
        Platform.runLater(() -> {
            int replicationsDone = myCore.replicationsDone();
            if (replicationsDone > Integer.parseInt(tfReplications.getText()) * (5 / 100.0)) {
                if (replicationsDone % 100 == 0) {
                    this.chart.addData(replicationsDone, myCore.statistics.customersWaitingTimeoverReplications.average());
                }
            }
            
            lbReplication.setText("Replikácia:\t" + replicationsDone);
            lbTime.setText(String.format("%.10f", myCore.statistics.customersWaitingTimeoverReplications.average()) + " sekúnd");
            lbPercent.setText(String.format("%.10f", myCore.statistics.percentLeftOverReplications.average() * 100) + "%");
            lbFreeCooks.setText(String.format("%.10f", myCore.statistics.freeCooksOverReplications.average()));
            lbFreeWaiters.setText(String.format("%.10f", myCore.statistics.freeWaitersOverReplications.average()));
            lbFree2.setText(String.format("%.10f", myCore.statistics._2tablesFreeoverReplications.average()));
            lbFree4.setText(String.format("%.10f", myCore.statistics._4tablesFreeoverReplications.average()));
            lbFree6.setText(String.format("%.10f", myCore.statistics._6tablesFreeoverReplications.average()));
            lbFreeTimeCooks.setText(String.format("%.10f", myCore.statistics.cooksFreeTimeOverReplications.average()));
            lbFreeTimeWaiters.setText(String.format("%.10f", myCore.statistics.waiterFreeTimeOverReplications.average()));
            
        });
        
    }
    
    private void toogleControlls(boolean start) {
        if (start) {
            tfSeed1.setDisable(true);
            tfWaiters.setDisable(true);
            cbCool.setDisable(true);
            tfCooks.setDisable(true);
            cbSeed1.setDisable(true);
            btnStart.setDisable(true);
        } else {
            tfSeed1.setDisable(false);
            tfWaiters.setDisable(false);
            cbCool.setDisable(false);
            tfCooks.setDisable(false);
            cbSeed1.setDisable(false);
            btnStart.setDisable(false);
            
        }
        
    }
    
    @Override
    public void afterSimulation(Simulation_Core core) {
        Restauracia_Core myCore = (Restauracia_Core) core;
        Platform.runLater(() -> {
            lbReplication.setText("Replikácia:\t" + myCore.replicationsDone());
            lbTime.setText(myCore.statistics.customersWaitingTimeoverReplications.confidenceIntervalString(false));
            lbPercent.setText(myCore.statistics.percentLeftOverReplications.confidenceIntervalString(true));
            lbFreeCooks.setText(myCore.statistics.freeCooksOverReplications.confidenceIntervalString(false));
            lbFreeWaiters.setText(myCore.statistics.freeWaitersOverReplications.confidenceIntervalString(false));
            lbFree2.setText(myCore.statistics._2tablesFreeoverReplications.confidenceIntervalString(false));
            lbFree4.setText(myCore.statistics._4tablesFreeoverReplications.confidenceIntervalString(false));
            lbFree6.setText(myCore.statistics._6tablesFreeoverReplications.confidenceIntervalString(false));
            lbFreeTimeCooks.setText(myCore.statistics.cooksFreeTimeOverReplications.confidenceIntervalString(true));
            lbFreeTimeWaiters.setText(myCore.statistics.waiterFreeTimeOverReplications.confidenceIntervalString(true));
            
        });
        
        toogleControlls(false);
        
    }
    
    @Override
    public void refreshOnlyTime(Simulation_Core core) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
