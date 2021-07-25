/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.asib27.playerdatabasesystem.*;
import com.asib27.playerdatabaseui.StatData;
import java.io.File;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * FXML Controller class
 *
 * @author USER
 */
public class PlayerTableController implements Initializable {
    
    @FXML
    private TableView playerTable;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    
    public void setData(Player[] allData){
        ObservableList<Player> data = FXCollections.observableArrayList(allData);
        playerTable.setItems(data);
    }
    
    public void setData(StatData[] allData){
        ObservableList<StatData> data = FXCollections.observableArrayList(allData);
        playerTable.setItems(data);
    }
    
    public ObservableList selectionModeProperty(){
        return playerTable.getSelectionModel().getSelectedItems();
    }

    public TableView getPlayerTable() {
        return playerTable;
    }

    public void setPlayerTable(TableView playerTable) {
        this.playerTable = playerTable;
    }
    
    public void clearListener() {
        
    }
}

