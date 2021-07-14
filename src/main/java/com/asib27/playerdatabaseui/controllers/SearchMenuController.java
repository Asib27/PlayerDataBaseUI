/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import com.asib27.playerdatabasesystem.PlayerAttribute;
import java.net.URL;
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
public class SearchMenuController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private BorderPane borderPane;
    
    @FXML
    private ComboBox positionComboBox;
    
    @FXML
    private ComboBox choiceComboBox;
    
    @FXML
    private ComboBox fieldComboBox;
    
    @FXML
    private Button searchButton;
    
    @FXML
    private TitledPane titledPane;
    
    private SimpleStringProperty searchString = new SimpleStringProperty(this, "search");
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        positionComboBox.getItems().addAll("Goalkeeper", "Defender", "Midfielder", "Forward");
        choiceComboBox.getItems().addAll("Search", "Minimum search", "Maximum search", "Player Count");
        choiceComboBox.setValue("Search");
        fieldComboBox.getItems().addAll(Arrays.asList(PlayerAttribute.values()));
        
        fieldComboBox.visibleProperty().bind(choiceComboBox.valueProperty().isNotEqualTo("Search"));
        
        borderPane.prefHeightProperty().bind(anchorPane.heightProperty());
        borderPane.prefWidthProperty().bind(anchorPane.widthProperty());
        
        searchButton.setOnAction(eh->searchString.set(positionComboBox.getValue().toString()));
        
        //titledPane.
    }    

    public String getSearchString() {
        return searchString.getValue();
    }

    public StringProperty SearchStringProperty(){
        return searchString;
    }
    
}
