/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui;

import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabasesystem.PlayerAttribute;
import com.asib27.playerdatabaseui.controllers.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

/**
 *
 * @author USER
 */
public class SearchMenuDriver implements Driver, SearchObserver<Player>{
    private Service service;
    private DatabaseManager database;
    
    private SearchMenuController searchMenuController;
    private SplitedScreenInt searchScreenController;
    private PlayerInfosController playerInfoController;
    private PlayerTableController playerTableController;
    
    private BorderPane searchScreenPane;
    private BorderPane searchMenuPane;
    private AnchorPane playerTablePane;
    private AnchorPane playerInfoPane;

    private SearchMenuDriver() {
        
    }
    
    

    public SearchMenuDriver(Service service) {
        this.service = service;
        database = service.getDatabase();
        
        try {
            FXMLLoader loader = paneFactory("SearchScreen");
            searchScreenPane = loader.load();
            searchScreenController = loader.getController();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {                
            FXMLLoader loader = paneFactory("SearchMenu");
            searchMenuPane = loader.load();
            searchMenuController = loader.getController();
            searchScreenController.setFloatingPane(searchMenuPane);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            FXMLLoader loader = paneFactory("PlayerTable");
            playerTablePane = loader.load();
            playerTableController = loader.getController();
            searchScreenController.setLeftPane(playerTablePane);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {                
            FXMLLoader loader = paneFactory("PlayerInfos");
            playerInfoPane = loader.load();
            playerInfoController = loader.getController();
            searchScreenController.setRightPane(playerInfoPane);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
            
        addListener();
    }
    
    static FXMLLoader paneFactory(String name){
        return switch(name){
            case "SearchScreen" ->    App.getFXMLLoader("SlidingScreen.fxml");
            case "SearchMenu"   -> App.getFXMLLoader("SearchMenu.fxml");
            case "PlayerTable"  -> App.getFXMLLoader("PlayerTable.fxml");
            case "PlayerInfos"  ->App.getFXMLLoader("PlayerInfos.fxml");
            default -> null;
        };
    }

    @Override
    public void update(DataProcessHelper<Player> dataProcessor) {
        Player[] players = dataProcessor.getData(database.getDataBase());
        playerTableController.setData(players);
    }
    
    
    private void  addListener(){
        initTableView(playerTableController.getPlayerTable());
        searchMenuController.addSearchListener(this);
        
        playerTableController.selectionModeProperty().addListener(new ListChangeListener(){
            @Override
            public void onChanged(ListChangeListener.Change change) {
                ObservableList list = change.getList();
                
                if(!list.isEmpty())
                    playerInfoController.setPlayer((Player) list.get(0));
            }            
        });
    }
    
    private void initTableView(TableView table){
        for (var field : PlayerAttribute.values()) {
            TableColumn col = new TableColumn(field.toString());
            col.setCellValueFactory(new PropertyValueFactory<>(field.toString()));
            table.getColumns().add(col);
        }
    }

    @Override
    public Pane getGuiPane() {
        return searchScreenPane;
    }

    @Override
    public void clearListener() {
        
    }
    
    public static Task getLoader(Service service){
        return new MainLoader(service);
    }
    
    public static class MainLoader extends Task<SearchMenuDriver>{
        private Service serviceName;

        public MainLoader(Service serviceName) {
            updateTitle("Loading Search Screnn");
            this.serviceName = serviceName;
        }

        @Override
        protected SearchMenuDriver call() throws Exception {
            SearchMenuDriver result = new SearchMenuDriver();
            
            result.service = serviceName;
            result.database = serviceName.getDatabase();
            
            try {
                FXMLLoader loader = paneFactory("SearchScreen");
                result.searchScreenPane = loader.load();
                result.searchScreenController = loader.getController();
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            updateMessage("Setting up 1/4");
            updateProgress(1, 4);
            
            try {                
                FXMLLoader loader = paneFactory("SearchMenu");
                result.searchMenuPane = loader.load();
                result.searchMenuController = loader.getController();
                result.searchScreenController.setFloatingPane(result.searchMenuPane);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            updateMessage("Setting up 2/4");
            updateProgress(2, 4);
            
            try {
                FXMLLoader loader = paneFactory("PlayerTable");
                result.playerTablePane = loader.load();
                result.playerTableController = loader.getController();
                result.searchScreenController.setLeftPane(result.playerTablePane);
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            updateMessage("Setting up 3/4");
            updateProgress(3, 4);
            
            try {                
                FXMLLoader loader = paneFactory("PlayerInfos");
                result.playerInfoPane = loader.load();
                result.playerInfoController = loader.getController();
                result.searchScreenController.setRightPane(result.playerInfoPane);
  
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
