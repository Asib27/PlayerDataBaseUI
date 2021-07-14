/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import com.asib27.playerdatabasesystem.*;
import java.io.File;
import java.net.URL;
import java.util.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private PieChart pieChart;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        selectChartBox.getItems().addAll("Pie chart");
        selectChartBox.setValue("Pie chart");
        
        //borderPane.
        
        PlayerDataBaseInt db = new PlayerDataBase(new File("G:\\Java\\PlayerDataBaseSystem\\src\\main\\java\\com\\asib27\\playerdatabasesystem\\playersData.txt"));
        makePieChart(pieChart, db);
        TableView<StatData> makeStatTable = makeStatTable();
        
        ArrayList<String> columns = new ArrayList<>(Arrays.asList(new String[]{"Liverpool", "Chelsea", "Man U"}));
        
        
        XYChart xy = makeChart(makeStatTable, columns);
        System.out.println(xy);
        borderPane.setCenter(xy);
        borderPane.setTop(makeStatTable);
    }    
    
    private void makePieChart(PieChart pieChart, PlayerDataBaseInt db){
        Map<String, Integer> counAlltFields = db.counAlltFields(PlayerAttribute.COUNTRY);
        counAlltFields.keySet().forEach(country -> {
            pieChart.getData().add(new PieChart.Data(country, counAlltFields.get(country)));
        });
        
        pieChart.setLegendSide(Side.RIGHT);
    }
    
    private XYChart makeChart(TableView<StatData> tableView, ArrayList<String> columns){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Country");
        
        NumberAxis yAxis = new NumberAxis();
        
        ObservableList<XYChart.Series> data = FXCollections.observableArrayList();
        
        columns.forEach((t) -> {
            XYChart.Series ser = new XYChart.Series<>();
            ser.setName(t);
            
            tableView.getItems().forEach((statData) -> {
                ser.getData().add(new XYChart.Data<>(statData.getName(), statData.getValue(t)));
            });
            
            data.add(ser);
        });
        
        XYChart chart = new BarChart(xAxis, yAxis);
        chart.setData(data);
        
        return chart;
    }
    
    private TableView<StatData> makeStatTable(){
        TableView<StatData> tableView = new TableView<>();
        
        StatData data1 = new StatData();
        data1.setName("Brazil");
        data1.addValue("Liverpool", 1);
        data1.addValue("Chelsea", 2);
        data1.addValue("Man U", 3);
        
        StatData data2 = new StatData();
        data2.setName("England");
        data2.addValue("Liverpool", 2);
        data2.addValue("Chelsea", 4);
        data2.addValue("Man U", 1);
        
        StatData data3 = new StatData();
        data3.setName("Italy");
        data3.addValue("Liverpool", 1);
        data3.addValue("Chelsea", 4);
        data3.addValue("Man U", 1);
        
        TableColumn<StatData, String> tc1 = new TableColumn("Country");
        tc1.setCellValueFactory((p) -> {
            return p.getValue().NameProperty(); //To change body of generated lambdas, choose Tools | Templates.
        });
        tableView.setItems(FXCollections.observableArrayList(data1, data2, data3));
        //tableView.getColumns().addAll(tc1);
        
        
        TableColumn<StatData, String> tc2= new TableColumn<>("Liverpool");
        tc2.setCellValueFactory((TableColumn.CellDataFeatures<StatData, String> p) -> {
            StatData sd = p.getValue();
            
            System.out.println("Liverpool "+sd.getValue("Liverpool"));
            ReadOnlyStringWrapper r1 = new ReadOnlyStringWrapper(String.valueOf(sd.getValue("Liverpool")));
            return r1.getReadOnlyProperty();
        });
        
        
        TableColumn<StatData, String> tc3= new TableColumn<>("Chelsea");
        tc3.setCellValueFactory((TableColumn.CellDataFeatures<StatData, String> p) -> {
            StatData sd = p.getValue();
            
            System.out.println("Chelsea "+sd.getValue("Chelsea"));
            return new ReadOnlyStringWrapper(String.valueOf(sd.getValue("Chelsea")));
        });
        
        TableColumn<StatData, String> tc4= new TableColumn<>("Man U");
        tc4.setCellValueFactory((TableColumn.CellDataFeatures<StatData, String> p) -> {
            StatData sd = p.getValue();
            
            //return new SimpleStringProperty(String.valueOf(sd.getValue("Liverpool")));
            System.out.println("Man U " + sd.getValue("Man U"));
            return new ReadOnlyStringWrapper(String.valueOf(sd.getValue("Man U")));
        });
        
        tableView.getColumns().addAll(tc1,tc2, tc3, tc4);
        
        return tableView;
        
    }
}
