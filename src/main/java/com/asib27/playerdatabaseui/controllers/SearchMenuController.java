/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import com.asib27.playerdatabaseui.ControllerHelper.SearchObserver;
import com.asib27.playerdatabaseui.ControllerHelper.DataProcessHelper;
import com.asib27.playerdatabaseui.util.ObserverUtil;
import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabasesystem.PlayerAttribute;
import com.asib27.playerdatabasesystem.PlayerDataBase;
import com.asib27.playerdatabasesystem.PlayerDataBaseInt;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class SearchMenuController extends ObserverUtil<SearchObserver<Player>> implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField namefield;

    @FXML
    private TextField clubfield;

    @FXML
    private TextField countryfield;

    @FXML
    private ComboBox<String> positionComboBox;

    @FXML
    private TextField salaryLowField;

    @FXML
    private TextField salaryHighField;

    @FXML
    private TextField jurseyfield;

    @FXML
    private TextField agefield;

    @FXML
    private ComboBox<String> choiceComboBox;

    @FXML
    private ComboBox<String> fieldComboBox;

    @FXML
    private Button searchButton;

    private SearchHelper searchHelper = new SearchHelper();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        positionComboBox.getItems().addAll("Goalkeeper", "Defender", "Midfielder", "Forward");
        choiceComboBox.getItems().addAll("Search", "Minimum search", "Maximum search");
        choiceComboBox.setValue("Search");
        //fieldComboBox.getItems().addAll(Arrays.asList(PlayerAttribute.values()));
        
        fieldComboBox.visibleProperty().bind(choiceComboBox.valueProperty().isNotEqualTo("Search"));
        addAllListener();
    }    
    
    private void addAllListener(){
        searchButton.setOnAction((t) -> {
            updateAll();
        });
    }
    
    public void clearListener(){
        fieldComboBox.visibleProperty().unbind();
    }

    public SearchHelper getSearchHelper() {
        return searchHelper;
    }

    @Override
    protected void updator(SearchObserver t) {
        t.update(searchHelper);
    }
    
    
    
    public class SearchHelper implements DataProcessHelper<Player>{
        
        public Player[] getData(PlayerDataBaseInt db){
            Player[] players = db.getAllRecords();
            
            if(namefield.getText().length() != 0){
                players = dbQuery(players, PlayerAttribute.NAME, namefield.getText());
            }
            
            if(clubfield.getText().length() != 0){
                players = dbQuery(players, PlayerAttribute.CLUB, clubfield.getText());
            }
            
            if(countryfield.getText().length() != 0){
                players = dbQuery(players, PlayerAttribute.COUNTRY, countryfield.getText());
            }
            
            if(positionComboBox.getValue() != null){
                players = dbQuery(players, PlayerAttribute.POSITION, positionComboBox.getValue());
            }
            
            if(agefield.getText().length() != 0){
                System.out.println(agefield.getText());
                players = dbQuery(players, PlayerAttribute.AGE, Integer.parseInt(agefield.getText()));
            }
            
            if(jurseyfield.getText().length() != 0){
                players = dbQuery(players, PlayerAttribute.JURSEY, Integer.parseInt(jurseyfield.getText()));
            }
            
            players = rangeQueryHelper(players, PlayerAttribute.SALARY, salaryLowField, salaryHighField);
            
            return players;
        }
        
        Player[] dbQuery(Player[] players, PlayerAttribute field, Object ob){
            return new PlayerDataBase(players).query(field, ob);
        }
        
        Player[] dbRangeQuery(Player[] players, PlayerAttribute field, Comparable ob1, Comparable ob2){
            return new PlayerDataBase(players).queryRange(field, ob1, ob2);
        }
        
        Player[] rangeQueryHelper(Player[] players, PlayerAttribute field, TextField low, TextField high){
            if(low.getText().length() != 0){
                players = dbRangeQuery(players, field, Double.parseDouble(low.getText()), Double.MAX_VALUE);
            }
            
            if(high.getText().length() != 0){
                players = dbRangeQuery(players, field, 0.0,  Double.parseDouble(high.getText()));
            }
            
            return players;
        }
    }
}
