/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Drivers;

import com.asib27.playerdatabaseui.Drivers.Driver;
import com.asib27.playerdatabaseui.ControllerHelper.SplitedScreenInt;
import com.asib27.playerdatabaseui.ControllerHelper.SearchObserver;
import com.asib27.playerdatabaseui.ControllerHelper.DataProcessHelper;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabasesystem.*;
import com.asib27.playerdatabaseui.App;
import com.asib27.playerdatabaseui.ControllerHelper.StatData;
import com.asib27.playerdatabaseui.controllers.*;
import java.io.IOException;
import java.util.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.Chart;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 *
 * @author USER
 */

public class StatMenuDriver  implements Driver, SearchObserver<StatData>, chartModifyListener{
    private Service service;
    private DatabaseManager database;
    
    final String driverName = "Stat Menu";
    
    private StatMenuController statMenuController;
    private SplitedScreenInt searchScreenController;
    private ChartController chartController;
    private PlayerTableController playerTableController;
    
    private BorderPane searchScreenPane;
    private BorderPane statMenuPane;
    private BorderPane playerTablePane;
    private AnchorPane chartPane;

    private StatMenuDriver() {
    }
    
    public StatMenuDriver(Service service) {
        this.service = service;
        database = service.getDatabase();
       
        try {
            FXMLLoader loader = paneFactory("SearchScreen");
            searchScreenPane = loader.load();
            searchScreenController = loader.getController();
            
            loader = paneFactory("statMenu");
            statMenuPane = loader.load();
            statMenuController = loader.getController();
            
            loader = paneFactory("PlayerTable");
            playerTablePane = loader.load();
            playerTableController = loader.getController();
            
            loader = paneFactory("chart");
            chartPane = loader.load();
            chartController = loader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        searchScreenController.setRightPane(chartPane);
        searchScreenController.setLeftPane(playerTablePane);
        searchScreenController.setFloatingPane(statMenuPane);
        
        addListener();
    }
    
    static FXMLLoader paneFactory(String name){
        return switch(name){
            case "SearchScreen" ->    App.getFXMLLoader("SlidingScreen.fxml");
            case "SearchMenu"   -> App.getFXMLLoader("SearchMenu.fxml");
            case "PlayerTable"  -> App.getFXMLLoader("PlayerTable.fxml");
            case "PlayerInfos"  ->App.getFXMLLoader("PlayerInfos.fxml");
            case "statMenu" -> App.getFXMLLoader("statMenu.fxml");
            case "chart" ->    App.getFXMLLoader("chart.fxml");
            default -> null;
        };
    }
    
    private void addListener(){
        playerTableController.getPlayerTable().itemsProperty().addListener(this::tableDatachanged);
        statMenuController.addSearchListener(this);
        chartController.addSearchListener(this);
        
        searchScreenController.getSplitPane().setDividerPosition(0, .5);
    }

    @Override
    public void update(DataProcessHelper<StatData> dataProcessor) {
        StatData[] sd = dataProcessor.getData(service.getDatabase().getDataBase());
        initTableView(playerTableController.getPlayerTable(), statMenuController.getDataProcessor().getColumns());
        playerTableController.setData(sd);
    }

    @Override
    public String getDriverName() {
        return driverName;
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
        chartController.removeSearchListener(this);
        
        playerTableController.getPlayerTable().itemsProperty().removeListener(this::tableDatachanged);
        playerTableController.clearListener();
        chartController.clearListener();
        //searchScreenController.clearListener();
    }
    
    public static Task getLoader(Service service){
        return new StatMenuLoader(service);
    }

    @Override
    public void modifyChart(Chart chart) {
        FXMLLoader fxmlLoader = App.getFXMLLoader("chartModifier.fxml");
        try {
            searchScreenController.setLeftPane(fxmlLoader.load());
            ChartModifierController chartModifierController = fxmlLoader.getController();
            chartModifierController.setChart(chart);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(DatabaseManager databaseManager) {
        StatMenuController.DataProcessor dataProcessor = statMenuController.getDataProcessor();
        this.database = databaseManager;
        
        if(dataProcessor != null){
            Platform.runLater(() -> {
                update(dataProcessor);
            });
        }
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
                FXMLLoader loader = paneFactory("SearchScreen");
                result.searchScreenPane = loader.load();
                result.searchScreenController = loader.getController();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            updateMessage("loading");
            updateProgress(1, 4);
            
            try {
                FXMLLoader loader = paneFactory("statMenu");
                result.statMenuPane = loader.load();
                result.statMenuController = loader.getController();
                result.searchScreenController.setFloatingPane(result.statMenuPane);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            
            updateMessage("loading..");
            updateProgress(2, 4);
            
            try {
                FXMLLoader loader = loader = paneFactory("PlayerTable");;
                result.playerTablePane = loader.load();
                result.playerTableController = loader.getController();
                result.searchScreenController.setLeftPane(result.playerTablePane);
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            
            updateMessage("Almost Done");
            updateProgress(3, 4);
            
            try {
                FXMLLoader loader = paneFactory("chart");
                result.chartPane = loader.load();
                result.chartController = loader.getController();
                result.searchScreenController.setRightPane(result.chartPane);
            
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
