/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_2.restauracia.gui.mainGui.graphGui;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author MarekPC
 */
public class ChartSet {

    private LineChart<Number, Number> chart;
    private XYChart.Series<Number, Number> series;
    private NumberAxis xAxis, yAxis;

    public LineChart<Number, Number> getChart() {
        return chart;
    }

    public void addData(Number x, Number y) {
        this.series.getData().add(new XYChart.Data<>(x, y));

    }

    public ChartSet(String seriesName, String xAxisName, String yAxisName,boolean showPoints) {

        xAxis = new NumberAxis();
        yAxis = new NumberAxis();

        xAxis.setAnimated(false);

        xAxis.setLabel(xAxisName);

//        yAxis.setTickUnit(0);
        yAxis.setAnimated(false);
        yAxis.setLabel(yAxisName);
        yAxis.setForceZeroInRange(false);
        xAxis.setForceZeroInRange(false);
        yAxis.setAutoRanging(true);

        this.series = new XYChart.Series<>();
        this.series.setName(seriesName);
        this.chart = new LineChart<>(xAxis, yAxis);
        chart.getData().add(this.series);
        chart.setCreateSymbols(showPoints);
    }

}
