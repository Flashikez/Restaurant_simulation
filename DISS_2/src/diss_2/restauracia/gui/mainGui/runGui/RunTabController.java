/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.gui.mainGui.runGui;

import diss_2.restauracia.Restauracia_Core;
import diss_2.restauracia.objects.Cook;
import diss_2.restauracia.objects.CustomerGroup;
import diss_2.restauracia.objects.Food;
import diss_2.restauracia.objects.Table;
import diss_2.restauracia.objects.Waiter;
import event_sim_core.Sim_GUI;
import event_sim_core.Simulation_Core;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.adapter.JavaBeanDoublePropertyBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author MarekPC
 */
public class RunTabController implements Initializable, Sim_GUI {

    @FXML
    private VBox runTab;
    @FXML
    private ChoiceBox<String> cbSeed1;
    @FXML
    private TextField tfSeed1;
    @FXML
    private TextField tfWaiters;
    @FXML
    private TextField tfCooks;
    @FXML
    private Button btnStart, btnStop;
    @FXML
    private Label lbTime, lbInSit, lbInLeft, lbOut;
    @FXML
    private ChoiceBox<SpeedClass> cbSpeed;
    @FXML
    private Button btnPause;
    @FXML
    private Button btnContinue;
    @FXML
    private CheckBox cbCool;
    private Restauracia_Core simulation;
    private SpeedClass oneToOne = new SpeedClass("1:1", 1, 1 * 1000);
    @FXML
    private TableColumn<Cook, Integer> tcCooksID;
    @FXML
    private TableColumn<Waiter, Integer> tcWaitersID;
    @FXML
    private TableColumn<CustomerGroup, Integer> tcOrderId;
    @FXML
    private TableColumn<CustomerGroup, Integer> tcPayId;
    @FXML
    private TableColumn<CustomerGroup, String> tcWaitersActivity;
    @FXML
    private TableColumn<Table, String> tcTable2Act, tcTable4Act, tcTable6Act;
    @FXML
    private TableColumn<CustomerGroup, Integer> tcWaitersGID;
    @FXML
    private TableColumn<Food, Integer> tcFoodId;
    @FXML
    private TableColumn<CustomerGroup, Integer> tcDeliveryId;
    @FXML
    private TableColumn<Cook, Double> tcCooksTime;
    @FXML
    private TableColumn<Waiter, Double> tcWaitersTime;
    @FXML
    private TableColumn<Cook, Boolean> tcCooksWorking;
    @FXML
    private TableColumn<Waiter, Boolean> tcWaiteresWorking;
    @FXML
    private TableColumn<Table, Integer> tcTable2ID, tcTable4ID, tcTable6ID;
    @FXML
    private TableColumn<Table, Integer> tcTable2GID, tcTable4GID, tcTable6GID;
    @FXML
    private TableColumn<Table, Integer> tcTable2Gsize, tcTable4Gsize, tcTable6Gsize;
    @FXML
    private TableColumn<Waiter, Boolean> tcTable2Oc, tcTable4Oc, tcTable6Oc;

    @FXML
    private TableView tvCooks, tvWaiters, tvPay, tvOrder, tvDelivery, tvFood, tvTable2, tvTable4, tvTable6;

    private NumberFormat formatter = new DecimalFormat("#0.000");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbSeed1.setItems(FXCollections.observableArrayList("Náhodná", "Fixná"));
        SpeedClass oneTo10 = new SpeedClass("1:10", 1, (1.0 / 10.0) * 1000.0);
        SpeedClass oneTo50 = new SpeedClass("1:50", 1, (1.0 / 50.0) * 1000.0);
        SpeedClass oneTo100 = new SpeedClass("1:100", 1, (1.0 / 100.0) * 1000.0);
        SpeedClass oneTo200 = new SpeedClass("1:200", 1, (1.0 / 200.0) * 1000.0);
        SpeedClass oneTo500 = new SpeedClass("1:500", 1, (1.0 / 500.0) * 1000.0);
        SpeedClass oneTo1000 = new SpeedClass("1:1 000", 1, (1.0 / 1000.0) * 1000.0);
        SpeedClass oneTo2000 = new SpeedClass("1:2 000", 2, (1.0 / 1000.0) * 1000.0);
        SpeedClass oneTo5000 = new SpeedClass("1:5 000", 5, (1.0 / 1000.0) * 1000.0);
        SpeedClass oneTo100000 = new SpeedClass("1:100 000", 100, 1);

        cbSpeed.setItems(FXCollections.observableArrayList(oneToOne, oneTo10, oneTo50, oneTo100, oneTo200, oneTo500, oneTo1000, oneTo2000, oneTo5000, oneTo100000));

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
        cbSpeed.setDisable(true);
        cbSpeed.getSelectionModel().select(oneToOne);
        cbSpeed.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SpeedClass>() {
            @Override
            public void changed(ObservableValue<? extends SpeedClass> observable, SpeedClass oldValue, SpeedClass newValue) {
                simulation.setSystemEventPlanTime(newValue.planTime);
                simulation.setSystemEventSleepTime(newValue.sleepTime);

            }

        });

        cbSeed1.getSelectionModel().select("Náhodná");
        tfWaiters.setText("4");
        tfCooks.setText("15");
        btnStart.setOnAction(e -> {
            int seed = -1;
            if (tfSeed1.isVisible()) {
                seed = Integer.parseInt(tfSeed1.getText());
            }
            simulation = new Restauracia_Core(9 * 60 * 60, 1, Integer.parseInt(tfWaiters.getText()), Integer.parseInt(tfCooks.getText()), seed, cbCool.isSelected());
            simulation.registerGUI(this);
            toogleControlls(true);
            setupTables(simulation);

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

        btnPause.setOnAction(e -> {
            simulation.pauseSimulation();
        });

        btnContinue.setOnAction(e -> {
            simulation.continueSimulation();

        });

    }

    @Override
    public void refresh(Simulation_Core core) {
        Restauracia_Core sim = (Restauracia_Core) core;
        Platform.runLater(() -> {
            lbTime.setText(sim.timeString());
            refreshTables(sim);
            lbInSit.setText("" + sim.statistics.totalCustomersIn.sum());
            lbOut.setText("" + sim.statistics.totalCustomersOut.sum());
            lbInLeft.setText("" + sim.statistics.totalCustomersLeft.sum());

        });

//      
//        Queue<CustomerGroup> orderQ = sim.getOrderQueue();
//        for (CustomerGroup customerGroup : orderQ) {
//           
//
//        }
//        tvDelivery.getItems().clear();
//        tvDelivery.getItems().addAll(sim.getDeliveryQueue());
//        tvPay.getItems().clear();
//        tvPay.getItems().addAll(sim.getPayQueue());
    }

    private void toogleControlls(boolean start) {
        if (start) {
            cbSpeed.setDisable(true);
            tfSeed1.setDisable(true);
            tfWaiters.setDisable(true);
            cbCool.setDisable(true);
            tfCooks.setDisable(true);
            cbSeed1.setDisable(true);
            cbSpeed.setDisable(false);
            btnStart.setDisable(true);
            btnPause.setDisable(false);
            btnContinue.setDisable(false);
            cbSpeed.getSelectionModel().select(oneToOne);
        } else {
            cbSpeed.setDisable(false);
            tfSeed1.setDisable(false);
            tfWaiters.setDisable(false);
            cbCool.setDisable(false);
            tfCooks.setDisable(false);
            cbSeed1.setDisable(false);
            cbSpeed.setDisable(true);
            btnStart.setDisable(false);
            btnPause.setDisable(true);
            btnContinue.setDisable(true);
        }

    }

    @Override
    public void afterSimulation(Simulation_Core core) {

        Restauracia_Core sim = (Restauracia_Core) core;
        Platform.runLater(() -> {
            toogleControlls(false);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Výsledky behu");
            alert.setHeaderText(null);
            alert.setContentText("Priemerný čas čakania zákazníka:  "+sim.statistics.totalCustomerWaitingTime.average()+"\n"+"");

            alert.showAndWait();

        });

    }

    private void setupTables(Restauracia_Core myCore) {
        tcCooksID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcPayId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcDeliveryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcTable2ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcWaitersActivity.setCellValueFactory(new PropertyValueFactory<>("activity"));
        tcTable2Act.setCellValueFactory(new PropertyValueFactory<>("activity"));
        tcTable4Act.setCellValueFactory(new PropertyValueFactory<>("activity"));
        tcTable6Act.setCellValueFactory(new PropertyValueFactory<>("activity"));
        tcWaitersGID.setCellValueFactory(new PropertyValueFactory<>("groupId"));
        tcTable4ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcTable6ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcTable2GID.setCellValueFactory(new PropertyValueFactory<>("groupId"));

//        tcTable2GID.setCellFactory(tc -> new TableCell<Table, Integer>() {
//            @Override
//            protected void updateItem(Integer i, boolean empty) {
//                super.updateItem(i, empty);
//                if (i == null) {
//                    setText(null);
//                    return;
//                }
//                if (i == -1) {
//                    setText(null);
//                }
//
//            }
//
//        });
        tcTable4GID.setCellValueFactory(new PropertyValueFactory<>("groupId"));
        tcTable6GID.setCellValueFactory(new PropertyValueFactory<>("groupId"));
        negativeOneAsEmpty(tcTable2GID, tcTable4GID, tcTable6GID);
        negativeOneAsEmpty(tcWaitersGID);

        tcTable2Gsize.setCellValueFactory(new PropertyValueFactory<>("groupSize"));
        tcTable4Gsize.setCellValueFactory(new PropertyValueFactory<>("groupSize"));
        tcTable6Gsize.setCellValueFactory(new PropertyValueFactory<>("groupSize"));
        negativeOneAsEmpty(tcTable2Gsize, tcTable4Gsize, tcTable6Gsize);
        tcFoodId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tcCooksTime.setCellValueFactory(new PropertyValueFactory<>("totalWorkedTime"));
        tcCooksWorking.setCellValueFactory(new PropertyValueFactory<>("working"));
        tcTable2Oc.setCellValueFactory(new PropertyValueFactory<>("free"));
        tcTable4Oc.setCellValueFactory(new PropertyValueFactory<>("free"));
        tcTable6Oc.setCellValueFactory(new PropertyValueFactory<>("free"));
        booleanAsColor(tcCooksWorking);

        tcWaitersID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcWaitersTime.setCellValueFactory(new PropertyValueFactory<>("totalWorkedTime"));
        tcWaitersTime.setCellFactory(tc -> new TableCell<Waiter, Double>() {

            @Override
            protected void updateItem(Double time, boolean empty) {
                super.updateItem(time, empty);

                if (empty) {
                    setText(null);
                } else {
                    setText(formatter.format(time));
                }
            }
        });
        tcWaiteresWorking.setCellValueFactory(new PropertyValueFactory<>("working"));
        booleanAsColor(tcWaiteresWorking);
        booleanAsColor(tcTable2Oc);
        booleanAsColor(tcTable4Oc);
        booleanAsColor(tcTable6Oc);

    }

    public <T> void booleanAsColor(TableColumn<T, Boolean> column) {
        column.setCellFactory(tc -> new TableCell<T, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    this.setStyle(null);
                    return;
                }
                if (empty) {
                    this.setStyle(null);
                } else if (item.booleanValue()) {
                    this.setStyle("-fx-background-color: green; -fx-border-width:10 0 0 0;-fx-border-color:#f4f4f4;");
                } else {
                    this.setStyle("-fx-background-color: red; -fx-border-width:10 0 0 0;-fx-border-color:#f4f4f4;");
                }

            }
        }
        );

    }

    public <T> void negativeOneAsEmpty(TableColumn<T, Integer>... columns) {
        for (TableColumn<T, Integer> column : columns) {

            column.setCellFactory(tc -> new TableCell<T, Integer>() {
                @Override
                protected void updateItem(Integer i, boolean empty) {
                    super.updateItem(i, empty);
                    if (i == null) {
                        setText(null);
                        return;
                    }
                    if (i == -1) {
                        setText(null);
                    } else {
                        setText(i + "");
                    }

                }

            });
        }

    }

    private void refreshTables(Restauracia_Core sim) {
        tvCooks.getItems().clear();
        tvCooks.getItems().addAll(sim.cooksManagment.getList());
        tvWaiters.getItems().clear();
        tvWaiters.getItems().addAll(sim.waitersManagment.getList());
        tvOrder.getItems().clear();
        tvOrder.getItems().addAll(sim.getOrderQueue());
        tvDelivery.getItems().clear();
        tvDelivery.getItems().addAll(sim.getDeliveryQueue());
        tvPay.getItems().clear();
        tvPay.getItems().addAll(sim.getPayQueue());
        tvFood.getItems().clear();
        tvFood.getItems().addAll(sim.getFoodQueue());
        tvTable2.getItems().clear();
//        tvTable2.getItems().addAll(sim.tablesManagment.getList());
        tvTable4.getItems().clear();
//        tvTable4.getItems().addAll(sim.tablesManagment.getList());
        tvTable6.getItems().clear();
        //        tvTable2.getItems().addAll(sim.tablesManagment.getList());
        List<Table> tables = sim.tablesManagment.getList();
        for (Table table : tables) {
            if (table.getTableSize() == 2) {
                tvTable2.getItems().add(table);
            } else if (table.getTableSize() == 4) {
                tvTable4.getItems().add(table);
            } else {
                tvTable6.getItems().add(table);
            }

        }

    }

    @Override
    public void refreshOnlyTime(Simulation_Core core) {
        Restauracia_Core sim = (Restauracia_Core) core;
        Platform.runLater(() -> {
            lbTime.setText(sim.timeString());
        });
    }

}
