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
import com.asib27.playerdatabaseui.ControllerHelper.StatData;
import java.io.File;
import java.util.Arrays;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
    
    @FXML
    private TextField searchField;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    public void setData(Predicate<String>[] data){
        FilteredList<Predicate<String>> filteredList = new FilteredList<>(FXCollections.observableArrayList(data), p->true);
        
        searchField.textProperty().addListener((ov, t, t1) -> {
            filteredList.setPredicate((pre) -> {
                if(t1 == null || t1.length() == 0)
                    return true;
                
                return pre.test(t1.toLowerCase());
            });
        });
        
        playerTable.setItems(filteredList);
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

