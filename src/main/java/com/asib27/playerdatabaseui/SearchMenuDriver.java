/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui;

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
    private SearchScreenController searchScreenController;
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
//       
//        try {
//            FXMLLoader loader = App.getFXMLLoader("SearchScreen.fxml");
//            searchScreenPane = loader.load();
//            searchScreenController = loader.getController();
//            
//            loader = App.getFXMLLoader("SearchMenu.fxml");
//            searchMenuPane = loader.load();
//            searchMenuController = loader.getController();
//            
//            loader = App.getFXMLLoader("PlayerTable.fxml");
//            playerTablePane = loader.load();
//            playerTableController = loader.getController();
//            
//            loader = App.getFXMLLoader("PlayerInfos.fxml");
//            playerInfoPane = loader.load();
//            playerInfoController = loader.getController();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        
//        
//        searchScreenController.setPaneRight(playerInfoPane);
//        searchScreenController.setPaneLeftUp(playerTablePane);
//        searchScreenController.setPaneLeftDown(searchMenuPane);
        
        try {
            FXMLLoader loader = App.getFXMLLoader("SearchScreen.fxml");
            searchScreenPane = loader.load();
            searchScreenController = loader.getController();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {                
            FXMLLoader loader = App.getFXMLLoader("SearchMenu.fxml");
            searchMenuPane = loader.load();
            searchMenuController = loader.getController();
            searchScreenController.setPaneLeftDown(searchMenuPane);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            FXMLLoader loader = App.getFXMLLoader("PlayerTable.fxml");
            playerTablePane = loader.load();
            playerTableController = loader.getController();
            searchScreenController.setPaneLeftUp(playerTablePane);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {                
            FXMLLoader loader = App.getFXMLLoader("PlayerInfos.fxml");
            playerInfoPane = loader.load();
            playerInfoController = loader.getController();
            searchScreenController.setPaneRight(playerInfoPane);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
            
        addListener();
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
                FXMLLoader loader = App.getFXMLLoader("SearchScreen.fxml");
                result.searchScreenPane = loader.load();
                result.searchScreenController = loader.getController();
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            updateMessage("Setting up 1/4");
            updateProgress(1, 4);
            
            try {                
                FXMLLoader loader = App.getFXMLLoader("SearchMenu.fxml");
                result.searchMenuPane = loader.load();
                result.searchMenuController = loader.getController();
                result.searchScreenController.setPaneLeftDown(result.searchMenuPane);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            updateMessage("Setting up 2/4");
            updateProgress(2, 4);
            
            try {
                FXMLLoader loader = App.getFXMLLoader("PlayerTable.fxml");
                result.playerTablePane = loader.load();
                result.playerTableController = loader.getController();
                result.searchScreenController.setPaneLeftUp(result.playerTablePane);
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            updateMessage("Setting up 3/4");
            updateProgress(3, 4);
            
            try {                
                FXMLLoader loader = App.getFXMLLoader("PlayerInfos.fxml");
                result.playerInfoPane = loader.load();
                result.playerInfoController = loader.getController();
                result.searchScreenController.setPaneRight(result.playerInfoPane);
  
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
