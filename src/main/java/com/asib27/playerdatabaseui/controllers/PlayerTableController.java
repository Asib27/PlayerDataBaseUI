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
    private TableView<Player> playerTable;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initTableView();
        
    }    
    
    public void initTableView(){
        ObservableList<TableColumn<Player,?>> columns = playerTable.getColumns();
        
        columns.get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        columns.get(1).setCellValueFactory(new PropertyValueFactory<>("club"));
        columns.get(2).setCellValueFactory(new PropertyValueFactory<>("country"));
        columns.get(3).setCellValueFactory(new PropertyValueFactory<>("age"));
        columns.get(4).setCellValueFactory(new PropertyValueFactory<>("position"));
        columns.get(5).setCellValueFactory(new PropertyValueFactory<>("salary"));
        columns.get(6).setCellValueFactory(new PropertyValueFactory<>("jurseyNumber"));
        
        
    }
    
    public void setData(Player[] allData){
        ObservableList<Player> data = FXCollections.observableArrayList(allData);
        playerTable.setItems(data);
        
        playerTable.getSelectionModel().select(0);
    }
    
    public ObservableList selectionModeProperty(){
        return playerTable.getSelectionModel().getSelectedItems();
    }
}

