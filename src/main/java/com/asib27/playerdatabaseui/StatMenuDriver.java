/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui;

import com.asib27.playerdatabasesystem.*;
import com.asib27.playerdatabaseui.controllers.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author USER
 */
public class StatMenuDriver implements Driver{
    private Service service;
    private DatabaseManager database;
    
    private StatMenuController statMenuController;
    private SearchScreenController searchScreenController;
    private ChartController chartController;
    private PlayerTableController playerTableController;
    
    private BorderPane searchScreenPane;
    private BorderPane statMenuPane;
    private AnchorPane playerTablePane;
    private AnchorPane chartPane;

    public StatMenuDriver(Service service) {
        this.service = service;
        database = service.getDatabase();
       
        try {
            FXMLLoader loader = App.getFXMLLoader("SearchScreen.fxml");
            searchScreenPane = loader.load();
            searchScreenController = loader.getController();
            
            loader = App.getFXMLLoader("statMenu.fxml");
            statMenuPane = loader.load();
            statMenuController = loader.getController();
            
            loader = App.getFXMLLoader("PlayerTable.fxml");
            playerTablePane = loader.load();
            playerTableController = loader.getController();
            
            loader = App.getFXMLLoader("chart.fxml");
            chartPane = loader.load();
            chartController = loader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        searchScreenController.setPaneRight(chartPane);
        searchScreenController.setPaneLeftUp(playerTablePane);
        searchScreenController.setPaneLeftDown(statMenuPane);
        
        playerTableController.getPlayerTable().itemsProperty().addListener(this::tableDatachanged);
        
        statMenuController.getSearchButton().setOnAction((t) -> {
            StatData[] sd = statMenuController.getDataProcessor().createData(database.getDataBase());
            //System.out.println(Arrays.toString(sd));
            initTableView(playerTableController.getPlayerTable(), statMenuController.getDataProcessor().getColumns());
            playerTableController.setData(sd);
        });
        
    }
    
    private void initTableView(TableView table, ArrayList<String> colName){
        table.getColumns().removeAll(table.getColumns());
        
        TableColumn<StatData, String> col1 = new TableColumn<>();
        col1.setCellValueFactory((p) -> {
            return p.getValue().NameProperty();
        });
        
        table.getColumns().add(col1);
        
        colName.forEach((t) -> {
            TableColumn<StatData, String> col = new TableColumn<>(t);
            col.setCellValueFactory((p) -> {
                double val = p.getValue().getValue(t);
                return new ReadOnlyStringWrapper(String.valueOf(val));
            });
            table.getColumns().add(col);
        });
         
    }
    
    public void tableDatachanged(ObservableValue obv, Object ov, Object nv) {
        ObservableList<StatData> olist = (ObservableList<StatData>) nv;
        ArrayList<String> dataMapKey = statMenuController.getDataProcessor().getColumns();
        
        chartController.getChartHelper().setData(olist.toArray(new StatData[0]), dataMapKey.toArray(new String[0]));
    }

    @Override
    public Pane getGuiPane() {
        return searchScreenPane;
    }

    @Override
    public void clearListener() {
        playerTableController.getPlayerTable().itemsProperty().removeListener(this::tableDatachanged);
        playerTableController.clearListener();
        chartController.clearListener();
        searchScreenController.clearListener();
    }
    
}
