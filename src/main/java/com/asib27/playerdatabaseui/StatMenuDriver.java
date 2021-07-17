/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui;

import com.asib27.playerdatabasesystem.*;
import com.asib27.playerdatabaseui.controllers.*;
import java.io.IOException;
import java.util.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 *
 * @author USER
 */

public class StatMenuDriver  implements Driver, SearchObserver<StatData>{
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

    private StatMenuDriver() {
    }
    
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
        
        addListener();
    }
    
    private void addListener(){
        playerTableController.getPlayerTable().itemsProperty().addListener(this::tableDatachanged);
        statMenuController.addSearchListener(this);
    }

    @Override
    public void update(DataProcessHelper<StatData> dataProcessor) {
        StatData[] sd = dataProcessor.getData(database.getDataBase());
        initTableView(playerTableController.getPlayerTable(), statMenuController.getDataProcessor().getColumns());
        playerTableController.setData(sd);
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
    
    public static Task getLoader(Service service){
        return new StatMenuLoader(service);
    }
    
    public static class StatMenuLoader extends Task<StatMenuDriver>{
        Service serviceName;

        public StatMenuLoader(Service serviceName) {
            this.serviceName = serviceName;
            updateTitle("Loading Stat Menu");
        }
        
        @Override
        protected StatMenuDriver call() throws Exception {
            StatMenuDriver result = new StatMenuDriver();
            
            result.service = serviceName;
            result.database = serviceName.getDatabase();
            
            try {
                FXMLLoader loader = App.getFXMLLoader("SearchScreen.fxml");
                result.searchScreenPane = loader.load();
                result.searchScreenController = loader.getController();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            updateMessage("loading");
            updateProgress(1, 4);
            
            try {
                FXMLLoader loader = App.getFXMLLoader("statMenu.fxml");
                result.statMenuPane = loader.load();
                result.statMenuController = loader.getController();
                result.searchScreenController.setPaneLeftDown(result.statMenuPane);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            
            updateMessage("loading..");
            updateProgress(2, 4);
            
            try {
                FXMLLoader loader = App.getFXMLLoader("PlayerTable.fxml");
                result.playerTablePane = loader.load();
                result.playerTableController = loader.getController();
                result.searchScreenController.setPaneLeftUp(result.playerTablePane);
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            
            updateMessage("Almost Done");
            updateProgress(3, 4);
            
            try {
                FXMLLoader loader = App.getFXMLLoader("chart.fxml");
                result.chartPane = loader.load();
                result.chartController = loader.getController();
                result.searchScreenController.setPaneRight(result.chartPane);
            
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            
            updateMessage("Complete");
            updateProgress(4, 4);
            
            result.addListener();
            
            return result;
        }
        
    }
}
