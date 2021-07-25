/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import com.asib27.playerdatabaseui.Drivers.Driver;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * FXML Controller class
 *
 * @author USER
 */

public class IntroPageController implements Initializable, Driver{
    @FXML
    private VBox vboxContainer;

    @FXML
    private Label databaseLabel;

    @FXML
    private TextField searchTextField;
    
    final String driverName = "New Tab";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        searchTextField.prefWidthProperty().bind(vboxContainer.widthProperty().divide(2));
    }    

    @Override
    public Pane getGuiPane() {
        return vboxContainer;
    }

    @Override
    public void clearListener() {
        ;
    }

    @Override
    public String getDriverName() {
        return driverName;
    }

    @Override
    public void update(DatabaseManager databaseManager) {
        System.out.println("Not implemented yet");
    }
    
}
