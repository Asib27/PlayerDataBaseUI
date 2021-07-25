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
import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabasesystem.PlayerAttribute;
import com.asib27.playerdatabasesystem.PlayerDataBaseInt;
import com.asib27.playerdatabaseui.App;
import com.asib27.playerdatabaseui.MessageBoxUtil;
import com.asib27.playerdatabaseui.controllers.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 *
 * @author USER
 */
public class SearchMenuDriver extends TableSearchInfoDriverUtil implements Driver, SearchObserver<Player>{
    private Service service;
    private DatabaseManager database;
    
    final String driverName = "Search Menu";
    
    private SearchMenuDriver() {
        
    }

    public SearchMenuDriver(Service service) {
        this.service = service;
        database = service.getDatabase();
        
        loadSearchScreen("SearchScreen");
        loadFloatingScreen("SearchMenu");
        loadLeftScreen("PlayerTable");
        loadRightScreen("PlayerInfos");
            
        addListener();
    }
    
    @Override
    public void update(DataProcessHelper<Player> dataProcessor) {
        Player[] players = dataProcessor.getData(service.getDatabase().getDataBase());
        playerTableController.setData(players);
    }
    
    
    private void  addListener(){
        initTableView(playerTableController.getPlayerTable());
        searchMenuController.addSearchListener(this);
        
        playerTableController.selectionModeProperty().addListener(this::selectionChangedListener);
        
        playerTableController.setData(getDatabase().getAllRecords());
        searchScreenController.setFloatingPointCollapsed(false);
    }
    
    private void selectionChangedListener(ListChangeListener.Change change){
        ObservableList list = change.getList();
    
        if(!list.isEmpty()){
            Player player = (Player) list.get(0);
            playerInfoController.setPlayer(player);

            if(player.getClub().equals(service.getClubName())){
                Button rightButton = playerInfoController.getRightButton();
                rightButton.setText("Sell Player");
                rightButton.setOnAction((t) -> {
                    MessageBoxUtil.playerSellMessageBox(player, service);
                });
            }
            else{
                Button rightButton = playerInfoController.getRightButton();
                rightButton.setText("Buy Player");
                rightButton.setOnAction((t) -> {
                    MessageBoxUtil.playerBuyRequestMessageBox(player, service);
                });
            }
            animateSplitPane(searchScreenController.getSplitPane(), .5);
        }
    }

    @Override
    public Pane getGuiPane() {
        return searchScreenPane;
    }

    @Override
    public void clearListener() {
        
    }

    @Override
    public String getDriverName() {
        return driverName;
    }
    
    
    
    public PlayerDataBaseInt getDatabase(){
        return service.getDatabase().getDataBase();
    }
    
    public static Task getLoader(Service service){
        return new MainLoader(service);
    }

    @Override
    public void update(DatabaseManager databaseManager) {
        SearchMenuController.SearchHelper searchHelper = searchMenuController.getSearchHelper();
        database = databaseManager;
        
        if(searchHelper != null){
            Platform.runLater(() -> {
                update(searchHelper);
            });
        }
    }
    
    public static class MainLoader extends Task<SearchMenuDriver>{
        private Service serviceName;

        public MainLoader(Service serviceName) {
            updateTitle("Loading Search Screen");
            this.serviceName = serviceName;
        }

        @Override
        protected SearchMenuDriver call(){
            SearchMenuDriver result = new SearchMenuDriver();
            
            result.service = serviceName;
            result.database = serviceName.getDatabase();
            
            result.loadSearchScreen("SearchScreen");
            updateMessage("Setting up 1/4");
            updateProgress(1, 4);
            
            result.loadFloatingScreen("SearchMenu");
            updateMessage("Setting up 2/4");
            updateProgress(2, 4);
            
            
            result.loadLeftScreen("PlayerTable");
            updateMessage("Setting up 3/4");
            updateProgress(3, 4);
            
            result.loadRightScreen("PlayerInfos");
            updateMessage("Complete");
            updateProgress(4, 4);
            
            result.addListener();
            
            return result;
        }
        
    }
}
