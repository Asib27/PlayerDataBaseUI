/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import com.asib27.playerdatabaseui.StatData;
import com.asib27.playerdatabasesystem.*;
import com.asib27.playerdatabaseui.util.ImageUtil;
import java.io.File;
import java.net.URL;
import java.util.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;



/**
 * FXML Controller class
 *
 * @author USER
 */
public class ChartController implements Initializable {
    @FXML
    private ComboBox selectChartBox;
    
    @FXML
    private BorderPane borderPane;
    
    @FXML
    private Button downloadButton;
    
    private Chart chart;
    private ChartHelper chartHelper;
    private SimpleStringProperty chartType = new SimpleStringProperty(this, "chart type", "Bar chart");
    private final String[] allChartType = {"Pie Chart", "Bar Chart", "Area Chart", "Line Chart",
        "Scatter Chart", "Stacked Area Chart", "Stacked Bar Chart"};
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        selectChartBox.getItems().addAll(allChartType);
        selectChartBox.valueProperty().bindBidirectional(chartType);
        chartHelper = new ChartHelper();
        borderPane.setCenter(chart);
        
        downloadButton.setOnAction((t) -> {
            WritableImage snapshot = chart.snapshot(null, null);
            ImageUtil.saveToFile(snapshot);
        });
    }    
    
    @FXML
    private void selectionChanged(ActionEvent event) {
        chartHelper.setChart();
    }
    

    public String getChartType() {
        return chartType.getValue();
    }

    public void setChartType(String chartType) {
        this.chartType.setValue(chartType);
    }
    
    public SimpleStringProperty chartTypeProperty(){
        return chartType;
    }

    public ChartHelper getChartHelper() {
        return chartHelper;
    }

    public void setChartHelper(ChartHelper chartHelper) {
        this.chartHelper = chartHelper;
    }
    
    public class ChartHelper{
        private CategoryAxis xAxis;
        private NumberAxis yAxis;
        private ObservableList<XYChart.Series> seriesData;
        private String[] dataMapKey;
        private StatData[] data;
        private boolean isDataValid; 

        public ChartHelper() {
            xAxis = new CategoryAxis();
            yAxis = new NumberAxis();
            isDataValid = false;
        }
        
        public void setChart(){
            if(chartType.getValue().equals("Pie Chart")){
                chart = makePieChart();
            }else{
                if(isDataValid)
                    chart = changeChartType();
                else
                    chart = makeChart();
            }
            
            borderPane.setCenter(chart);
        }
        
        private XYChart chartFactory(Axis xAxis, Axis yAxis){
            return switch(chartType.getValue()){
                case "Bar Chart" -> new BarChart<>(xAxis, yAxis);
                case "Area Chart" -> new AreaChart<>(xAxis, yAxis);
                case "Bubble Chart" -> new BubbleChart<>(xAxis, yAxis);
                case "Line Chart" -> new LineChart<>(xAxis, yAxis);
                case "Scatter Chart" -> new ScatterChart<>(xAxis, yAxis);
                case "Stacked Area Chart"-> new StackedAreaChart<>(xAxis, yAxis);
                case "Stacked Bar Chart"-> new StackedBarChart<>(xAxis, yAxis);
                default -> new BarChart<>(xAxis, yAxis);
            };
        }
        
        private XYChart chartFactory(Axis xAxis, Axis yAxis,  ObservableList<XYChart.Series> ol){
            XYChart c = chartFactory(xAxis, yAxis);
            c.setData(ol);
            return c;
        }
        
        private XYChart makeChart(){
            seriesData = FXCollections.observableArrayList();

            for (String t: dataMapKey) {
                XYChart.Series ser = new XYChart.Series<>();
                ser.setName(t);

                for (StatData statData: data) {
                    ser.getData().add(new XYChart.Data<>(statData.getName(), statData.getValue(t)));
                }

                seriesData.add(ser);
            }

            XYChart chart = chartFactory(xAxis, yAxis);
            chart.setData(seriesData);
            isDataValid = true;
            return chart;
        }
        
        
        private XYChart changeChartType(){
            return chartFactory(xAxis, yAxis, seriesData);
        }
        
        private PieChart makePieChart(){
            PieChart pieChart = new PieChart();
            
            for (var statData : data) {
                double total = 0;
                
                total = statData.getSeriesValues().keySet().stream().map(t -> statData.getValue(t)).reduce(total, Double::sum);
                
                pieChart.getData().add(new PieChart.Data(statData.getName(), total));
            }
            
            return pieChart;
        }

        public Axis getxAxis() {
            return xAxis;
        }

        public Axis getyAxis() {
            return yAxis;
        }

        public StatData[] getData() {
            return data;
        }

        public void setData(StatData[] data, String[] dataMapKey) {
            this.data = data;
            isDataValid = false;
            this.dataMapKey = dataMapKey;
            setChart();
        }

    
        
    }
    
    public void clearListener() {
        selectChartBox.valueProperty().unbindBidirectional(chartType);
    }
}
