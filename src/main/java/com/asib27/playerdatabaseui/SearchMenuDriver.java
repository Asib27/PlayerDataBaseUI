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
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

/**
 *
 * @author USER
 */
public class SearchMenuDriver implements Driver{
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

    public SearchMenuDriver(Service service) {
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
        
        initTableView(playerTableController.getPlayerTable());
        
        searchMenuController.getSearchButton().setOnAction((t) -> {
            Player[] players = searchMenuController.getSearchHelper().createData(database.getDataBase());
            playerTableController.setData(players);
        });
        
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
    
    
}
