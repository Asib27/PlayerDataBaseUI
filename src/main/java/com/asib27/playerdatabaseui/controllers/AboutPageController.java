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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class AboutPageController implements Initializable, Driver {
    @FXML
    private AnchorPane anchorPane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private final String driverName = "About Page";

    @Override
    public Pane getGuiPane() {
        return anchorPane;
    }

    @Override
    public void clearListener() {
        
    }

    @Override
    public void update(DatabaseManager databaseManager) {
        
    }

    @Override
    public String getDriverName() {
        return driverName;
    }
    
}
