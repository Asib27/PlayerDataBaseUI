/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Drivers;

import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabasesystem.PlayerDataBase;
import com.asib27.playerdatabasesystem.PlayerDataBaseInt;
import com.asib27.playerdatabaseui.ControllerHelper.DataProcessHelper;
import com.asib27.playerdatabaseui.ControllerHelper.SearchObserver;
import com.asib27.playerdatabaseui.CustomControls.MessageBoxUtil;
import com.asib27.playerdatabaseui.controllers.SearchMenuController;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.util.PlayerTransaction;
import java.util.ArrayList;
import java.util.Set;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author USER
 */
public class BuyMenuDriver extends TableSearchInfoDriverUtil implements SearchObserver<Player>, Driver{
    private Service service;
    final String driverName = "Buy Menu";

    private BuyMenuDriver() {
    }

    public BuyMenuDriver(Service service) {
        this.service = service;
        
        loadSearchScreen("SearchScreen");
        loadFloatingScreen("SearchMenu");
        loadLeftScreen("PlayerTable");
        loadRightScreen("PlayerInfos");
        
        addListener();
    }


    private void  addListener(){
        initTableView(playerTableController.getPlayerTable());
        searchMenuController.addSearchListener(this);
        
        playerTableController.selectionModeProperty().addListener(this::selectionChangedListener);
        
        searchScreenController.setFloatingPointCollapsed(false);
        playerTableController.setData(getData());
        
        service.playersOnSellProperty().addListener((var ov, var t, var t1) -> {
            playerTableController.setData(getData());
        });
    }
    
    private void selectionChangedListener(ListChangeListener.Change change){
        ObservableList list = change.getList();
    
        if(!list.isEmpty()){
            Player player = (Player) list.get(0);
            playerInfoController.setPlayer(player);

            if(player.getClub().equals(service.getClubName())){
                Button rightButton = playerInfoController.getRightButton();
                rightButton.setText("Cancel Sell Player");
                rightButton.setDisable(true);
                rightButton.setOnAction((t) -> {
                    MessageBoxUtil.playerBuyRequestMessageBox(player, service);
                });
            }
            else{
                Button rightButton = playerInfoController.getRightButton();
                rightButton.setText("Buy Player");
                rightButton.setDisable(false);
                rightButton.setOnAction((t) -> {
                    MessageBoxUtil.playerBuyRequestMessageBox(player, service);
                });
            }
            animateSplitPane(searchScreenController.getSplitPane(), .5);
        }
    }

    @Override
    public void update(DataProcessHelper<Player> dataProcessor) {
        Player[] players = dataProcessor.getData(new PlayerDataBase(getData()));
        playerTableController.setData(players);
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
    
    
    
    public Service getService() {
        return service;
    }
    
    
    
    private Player[] getData(){
        Set<PlayerTransaction> playersOnSell = service.getPlayersOnSell();
        ArrayList<Player> players =  new ArrayList<>();
            
        playersOnSell.forEach((t) -> {
            players.add(t.getPlayer());
        });
        
        return players.toArray(new Player[0]);
    }
    
    public static Task getLoader(Service service){
        return new BuyMenuDriver.MainLoader(service);
    }

    @Override
    public void update(DatabaseManager databaseManager) {
        SearchMenuController.SearchHelper searchHelper = searchMenuController.getSearchHelper();
        
        if(searchHelper != null){
            Platform.runLater(() -> {
                update(searchHelper);
            });
        }
    }
    
    public static class MainLoader extends Task<BuyMenuDriver>{
        private Service serviceName;

        public MainLoader(Service serviceName) {
            updateTitle("Loading Search Screnn");
            this.serviceName = serviceName;
        }

        @Override
        protected BuyMenuDriver call(){
            BuyMenuDriver result = new BuyMenuDriver();
            
            result.service = serviceName;
            
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
