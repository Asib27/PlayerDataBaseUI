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
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 *
 * @author USER
 */
public class SearchMenu implements Driver{
    private Service service;
    private DatabaseManager database;
    
    private SearchMenuController searchMenuController;
    private SearchScreenController searchScreenController;
    private PlayerInfosController playerInfoController;
    private PlayerTableController playerTableController;
    
    private BorderPane searchScreenPane;
    private AnchorPane searchMenuPane;
    private AnchorPane playerTablePane;
    private AnchorPane playerInfoPane;

    public SearchMenu(Service service) {
        this.service = service;
        database = service.getDatabase();
       
        try {
            FXMLLoader loader = App.getFXMLLoader("SearchScreen.fxml");
            searchScreenPane = loader.load();
            searchScreenController = loader.getController();
            
            loader = App.getFXMLLoader("SearchMenu.fxml");
            searchMenuPane = loader.load();
            searchMenuController = loader.getController();
            
            loader = App.getFXMLLoader("PlayerTable.fxml");
            playerTablePane = loader.load();
            playerTableController = loader.getController();
            
            loader = App.getFXMLLoader("PlayerInfos.fxml");
            playerInfoPane = loader.load();
            playerInfoController = loader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        searchScreenController.setPaneRight(playerInfoPane);
        searchScreenController.setPaneLeftUp(playerTablePane);
        searchScreenController.setPaneLeftDown(searchMenuPane);
        //searchScreenController.setPaneLeftDown(playerInfoPane);
        
        searchMenuController.SearchStringProperty().addListener((c,ov, nv)->{
            playerTableController.setData(database.getDataBase().query(PlayerAttribute.POSITION, nv.toString()));
        });
        
        playerTableController.selectionModeProperty().addListener(new ListChangeListener(){
            @Override
            public void onChanged(ListChangeListener.Change change) {
                Player player = (Player) change.getList().get(0);
                playerInfoController.setPlayer(player);
            }
            
            
        });
    }

    @Override
    public Pane getGuiPane() {
        return searchScreenPane;
    }
    
    
}
